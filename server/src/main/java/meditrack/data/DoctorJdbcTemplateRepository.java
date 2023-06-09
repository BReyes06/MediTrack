package meditrack.data;

import meditrack.data.mappers.DoctorMapper;
import meditrack.models.Doctor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

@Repository
public class DoctorJdbcTemplateRepository implements DoctorRepository{

    private final JdbcTemplate jdbcTemplate;

    public DoctorJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Doctor findById(int doctorId) {
        final String sql = "select doctor_id, first_name, middle_name, last_name, location, phone "
                + "from doctor "
                + "where doctor_id = ?;";

        return jdbcTemplate.query(sql, new DoctorMapper(), doctorId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<Doctor> findByAllByUserId(int appUserId) {
        final String sql = "select d.doctor_id, d.first_name, d.middle_name, d.last_name,  d.location, d.phone "
                + "from doctor d "
                + "inner join prescription p on d.doctor_id = p.doctor_id "
                + "inner join app_user au on p.app_user_id = au.app_user_id "
                + "where au.app_user_id = ?;";

        return jdbcTemplate.query(sql, new DoctorMapper(), appUserId);
    }

    @Override
    public Doctor add(Doctor doctor, int prescriptionId) {
        final String sql = "insert into doctor (first_name, middle_name, last_name, location, phone) "
                + "values (?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, doctor.getFirstName());
            ps.setString(3, doctor.getLastName());
            ps.setString(4, doctor.getLocation());
            ps.setString(5, doctor.getPhone());

            if (doctor.getMiddleName() != null) {
                ps.setString(2, doctor.getMiddleName());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        doctor.setDoctorId(keyHolder.getKey().intValue());
        updatePrescription(doctor.getDoctorId(), prescriptionId);

        return doctor;
    }

    @Override
    public boolean update(Doctor doctor) {
        final String sql = "update doctor set "
                + "first_name = ?, "
                + "middle_name = ?, "
                + "last_name = ?, "
                + "location = ?, "
                + "phone = ? "
                + "where doctor_id = ?;";

        return jdbcTemplate.update(sql,
                doctor.getFirstName(),
                doctor.getMiddleName() == null ? null : doctor.getMiddleName(),
                doctor.getLastName(),
                doctor.getLocation(),
                doctor.getPhone(),
                doctor.getDoctorId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int doctorId) {
        jdbcTemplate.update("update prescription set doctor_id = ? where doctor_id = ?;", null, doctorId);
        return jdbcTemplate.update("delete from doctor where doctor_id = ?;", doctorId) > 0;
    }

    private void updatePrescription(int doctorId, int prescriptionId) {
        final String sql = "update prescription set "
                + "doctor_id = ? "
                + "where prescription_id = ?;";
        jdbcTemplate.update(sql, doctorId, prescriptionId);
    }

}
