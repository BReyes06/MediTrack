package meditrack.data;

import meditrack.data.mappers.AppUserMapper;
import meditrack.data.mappers.PrescriptionMapper;
import meditrack.models.AppUser;
import meditrack.models.Prescription;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository{

    private final JdbcTemplate jdbcTemplate;

    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public AppUser findByUsername(String username) {
        List<String> roles = getRolesByUsername(username);

        final String sql = "select app_user_id, first_name, middle_name, last_name, email, phone, username, password_hash, enabled "
                + "from app_user "
                + "where username = ?;";

        return jdbcTemplate.query(sql, new AppUserMapper(roles), username)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public AppUser findById(int appUserId) {
        return jdbcTemplate.query("select app_user_id, first_name, middle_name, last_name, email, phone, username, password_hash, enabled "
                + "from app_user "
                + "where app_user_id = ?;", new AppUserMapper(new ArrayList<>()), appUserId).stream().findFirst().orElse(null);
    }

    @Override
    public List<AppUser> findAll() {
        List<AppUser> appUsers = jdbcTemplate.query("select app_user_id, first_name, middle_name, last_name, email, phone, username, password_hash, enabled "
                        + "from app_user ", new AppUserMapper(new ArrayList<>()));

        for (AppUser appUser : appUsers) {
            appUser.setAuthorities(getRolesByUsername(appUser.getUsername()));
        }

        return appUsers;
    }

    @Override
    public AppUser create(AppUser user) {
        final String sql = "insert into app_user (first_name, middle_name, last_name, email, phone, username, password_hash) "
                + "values (?, ?, ?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getMiddleName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getUsername());
            ps.setString(7, user.getPassword());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        user.setAppUserId(keyHolder.getKey().intValue());

        updateRoles(user);

        return user;
    }

    @Override
    public boolean update(AppUser user) {
        final String sql = "update app_user set "
                + "first_name = ?, "
                + "middle_name = ?, "
                + "last_name = ?, "
                + "email = ?, "
                + "phone = ?, "
                + "password_hash = ?, "
                + "enabled = ? "
                + "where app_user_id = ?;";

        if (jdbcTemplate.update(sql,
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getPassword(),
                user.isEnabled(),
                user.getAppUserId()) > 0) {
            updateRoles(user);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteById(int appUserId) {
        final String sql = "select prescription_id, pill_count, hourly_interval, start_time, product_ndc, app_user_id, doctor_id, pharmacy_id "
                + "from prescription "
                + "where app_user_id = ?;";
        List<Prescription> prescriptionList = jdbcTemplate.query(sql, new PrescriptionMapper(), appUserId);

        prescriptionList.forEach(prescription -> {
            int prescriptionId = prescription.getPrescriptionId();

            jdbcTemplate.update("delete from tracker where prescription_id = ?;", prescriptionId);
            jdbcTemplate.update("delete from prescription where prescription_id = ?;", prescriptionId);
        });

        jdbcTemplate.update("delete from app_user_role where app_user_id = ?;", appUserId);
        return jdbcTemplate.update("delete from app_user where app_user_id = ?;", appUserId) > 0;
    }

    private List<String> getRolesByUsername(String username) {
        final String sql = "select r.name "
                + "from app_user_role ur "
                + "inner join app_role r on ur.app_role_id = r.app_role_id "
                + "inner join app_user au on ur.app_user_id = au.app_user_id "
                + "where au.username = ?;";
        return jdbcTemplate.query(sql, (rs, rowId) -> rs.getString("name"), username);
    }

    private void updateRoles(AppUser user) {
        jdbcTemplate.update("delete from app_user_role where app_user_id = ?;", user.getAppUserId());

        Collection<GrantedAuthority> authorities = user.getAuthorities();

        if (authorities == null) {
            return;
        }

        for (GrantedAuthority role : authorities) {
            final String sql = "insert into app_user_role (app_user_id, app_role_id) "
                    + "select ?, app_role_id from app_role where `name` = ?;";
            jdbcTemplate.update(sql, user.getAppUserId(), role.getAuthority());
        }
    }
}
