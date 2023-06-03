package meditrack.data.mappers;

import meditrack.models.Doctor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorMapper implements RowMapper<Doctor> {

    @Override
    public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Doctor doctor = new Doctor();

        doctor.setDoctorId(rs.getInt("doctor_id"));
        doctor.setFirstName(rs.getString("first_name"));
        doctor.setMiddleName(rs.getString("middle_name"));
        doctor.setLastName(rs.getString("last_name"));
        doctor.setLocation(rs.getString("location"));
        doctor.setPhone(rs.getString("phone"));

        return doctor;
    }
}
