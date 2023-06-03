package meditrack.data;

import meditrack.data.mappers.PharmacyMapper;
import meditrack.models.Pharmacy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
