package meditrack.data;

import meditrack.data.mappers.TrackerMapper;
import meditrack.models.Tracker;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TrackerJdbcTemplateRepository implements TrackerRepository{

    private final JdbcTemplate jdbcTemplate;


    public TrackerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tracker> findAllByPrescriptionId(int prescriptionId) {
        final String sql = "select tracker_id, administration_time, prescription_id "
                + "from tracker "
                + "where prescription_id = ?;";
        return jdbcTemplate.query(sql, new TrackerMapper(), prescriptionId);
    }

    @Override
    public Tracker findById(int trackerId) {
        final String sql = "select tracker_id, administration_time, prescription_id "
                + "from tracker "
                + "where tracker_id = ?;";
        return jdbcTemplate.query(sql, new TrackerMapper(), trackerId).stream()
                .findFirst().orElse(null);
    }

    @Override
    @Transactional
    public Tracker add(Tracker tracker) {
        final String sql = "insert into tracker (administration_time, prescription_id) "
                + "values (?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tracker.getAdministrationTime());
            ps.setInt(2, tracker.getPrescriptionId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        tracker.setTrackerId(keyHolder.getKey().intValue());
        updatePrescriptionPillCount(tracker);

        return tracker;
    }

    @Override
    public boolean update(Tracker tracker) {
        final String sql = "update tracker set "
                + "administration_time = ?, "
                + "prescription_id = ? "
                + "where tracker_id = ?;";
        return jdbcTemplate.update(sql,
                tracker.getAdministrationTime(),
                tracker.getPrescriptionId(),
                tracker.getTrackerId()) > 0;
    }

    @Override
    public boolean deleteById(int trackerId) {
        return jdbcTemplate.update("delete from tracker where tracker_id = ?;", trackerId) > 0;
    }

    private void updatePrescriptionPillCount(Tracker tracker) {
        final String sql = "update prescription set "
                + "pill_count = ? "
                + "where prescription_id = ?;";
        jdbcTemplate.update(sql, tracker.getPillCount() - 1, tracker.getPrescriptionId());
    }
}
