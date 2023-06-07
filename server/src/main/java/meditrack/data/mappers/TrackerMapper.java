package meditrack.data.mappers;

import meditrack.models.Tracker;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class TrackerMapper implements RowMapper<Tracker> {
    @Override
    public Tracker mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tracker tracker = new Tracker();
        tracker.setTrackerId(rs.getInt("tracker_id"));
        tracker.setAdministrationTime(rs.getString("administration_time"));
        tracker.setPrescriptionId(rs.getInt("prescription_id"));

        return tracker;
    }
}
