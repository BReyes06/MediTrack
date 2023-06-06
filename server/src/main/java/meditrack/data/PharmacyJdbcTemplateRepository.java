package meditrack.data;

import meditrack.data.mappers.PharmacyMapper;
import meditrack.models.Pharmacy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;

@Repository
public class PharmacyJdbcTemplateRepository implements PharmacyRepository {

    private final JdbcTemplate jdbcTemplate;

    public PharmacyJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Pharmacy findById(int pharmacyId) {
        final String sql = "select pharmacy_id, `name`, email, phone, address "
                + "from pharmacy "
                + "where pharmacy_id = ?";
        return jdbcTemplate.query(sql, new PharmacyMapper(), pharmacyId).stream()
                .findFirst().orElse(null);
    }

    @Override
    @Transactional
    public Pharmacy add(Pharmacy pharmacy, int prescriptionId) {
        final String sql = "insert into pharmacy (`name`, email, phone, address) "
                + "values (?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffect = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pharmacy.getName());
            ps.setString(3, pharmacy.getPhone());
            ps.setString(4, pharmacy.getAddress());

            if (pharmacy.getEmail() != null) {
                ps.setString(2, pharmacy.getEmail());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            return ps;
        }, keyHolder);

        if (rowsAffect <= 0) {
            return null;
        }

        pharmacy.setPharmacyId(keyHolder.getKey().intValue());
        updatePrescription(pharmacy.getPharmacyId(), prescriptionId);

        return pharmacy;
    }

    @Override
    public boolean update(Pharmacy pharmacy) {
        final String sql = "update pharmacy set "
                + "`name` = ?, "
                + "email = ?, "
                + "phone = ?, "
                + "address = ? "
                + "where pharmacy_id = ?;";

        return jdbcTemplate.update(sql,
                pharmacy.getName(),
                pharmacy.getEmail() != null ? pharmacy.getEmail() : null,
                pharmacy.getPhone(),
                pharmacy.getAddress(),
                pharmacy.getPharmacyId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int pharmacyId) {
        jdbcTemplate.update("update prescription set pharmacy_id = ? where pharmacy_id = ?;", null, pharmacyId);
        return jdbcTemplate.update("delete from pharmacy where pharmacy_id = ?;", pharmacyId) > 0;
    }

    private void updatePrescription(int pharmacyId, int prescriptionId) {
        final String sql = "update prescription set "
                + "pharmacy_id = ? "
                + "where prescription_id = ?;";
        jdbcTemplate.update(sql, pharmacyId, prescriptionId
        );
    }
}
