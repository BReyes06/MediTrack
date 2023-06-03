package meditrack.data.mappers;

import meditrack.models.Pharmacy;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PharmacyMapper implements RowMapper<Pharmacy> {
    @Override
    public Pharmacy mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setPharmacyId(rs.getInt("pharmacy_id"));
        pharmacy.setName(rs.getString("name"));
        pharmacy.setEmail(rs.getString("email"));
        pharmacy.setPhone(rs.getString("phone"));
        pharmacy.setAddress(rs.getString("address"));

        return pharmacy;
    }
}
