package meditrack.data;

import meditrack.data.mappers.DoctorMapper;
import meditrack.models.Doctor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;

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

    private void updatePrescription(int doctorId, int prescriptionId) {
        final String sql = "update prescription set "
                + "doctor_id = ? "
                + "where prescription_id = ?;";
        jdbcTemplate.update(sql, doctorId, prescriptionId);
    }

}
