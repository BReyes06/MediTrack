package meditrack.data;

import meditrack.data.mappers.PrescriptionMapper;
import meditrack.models.Prescription;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrescriptionJdbcTemplateRepository implements PrescriptionRepository {

    private final JdbcTemplate jdbcTemplate;

    public PrescriptionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Prescription> findAllById(int appUserId) {
        String sql = "select prescription_id, pill_count, hourly_interval, start_time, product_ndc, app_user_id, doctor_id, pharmacy_id "
                + "from prescription "
                + "where app_user_id = ?;";

        return jdbcTemplate.query(sql, new PrescriptionMapper(), appUserId);
    }

    @Override
    public Prescription add() {
        return null;
    }
}
