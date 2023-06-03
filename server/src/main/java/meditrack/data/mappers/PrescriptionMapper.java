package meditrack.data.mappers;

import meditrack.models.AppUser;
import meditrack.models.Doctor;
import meditrack.models.Pharmacy;
import meditrack.models.Prescription;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PrescriptionMapper implements RowMapper<Prescription> {
    @Override
    public Prescription mapRow(ResultSet rs, int rowNum) throws SQLException {
        Prescription prescription = new Prescription();
        AppUser appUser = new AppUser();
        Doctor doctor = new Doctor();
        Pharmacy pharmacy = new Pharmacy();

        appUser.setAppUserId(rs.getInt(rs.getInt("app_user_id")));
        doctor.setDoctorId(rs.getInt("doctor_id"));
        pharmacy.setPharmacyId(rs.getInt("pharmacy_id"));

        prescription.setPrescriptionId(rs.getInt("prescription_id"));
        prescription.setHourlyInterval(rs.getInt("hourly_interval"));
        prescription.setPillCount(rs.getInt("pill_count"));
        prescription.setStartTime(rs.getString("start_time"));
        prescription.setProductNDC(rs.getString("product_ndc"));
        prescription.setAppUser(appUser);
        prescription.setDoctor(doctor);
        prescription.setPharmacy(pharmacy);

        return prescription;
    }
}
