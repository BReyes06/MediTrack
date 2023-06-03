package meditrack.data;

import meditrack.data.mappers.DoctorMapper;
import meditrack.models.Doctor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

}
