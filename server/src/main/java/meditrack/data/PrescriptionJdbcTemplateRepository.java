package meditrack.data;

import meditrack.data.mappers.PrescriptionMapper;
import meditrack.models.Doctor;
import meditrack.models.Pharmacy;
import meditrack.models.Prescription;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

@Repository
public class PrescriptionJdbcTemplateRepository implements PrescriptionRepository {

    private final JdbcTemplate jdbcTemplate;

    public PrescriptionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Prescription> findAllById(int appUserId) {
        final String sql = "select prescription_id, pill_count, hourly_interval, start_time, product_ndc, app_user_id, doctor_id, pharmacy_id "
                + "from prescription "
                + "where app_user_id = ?;";

        return jdbcTemplate.query(sql, new PrescriptionMapper(), appUserId);
    }

    @Override
    @Transactional
    public Prescription add(Prescription prescription) {
        final String sql = "insert into prescription (pill_count, hourly_interval, start_time, product_ndc, app_user_id, doctor_id, pharmacy_id) "
                + "values (?, ?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, prescription.getPillCount());
            ps.setInt(2, prescription.getHourlyInterval());
            ps.setString(3, prescription.getStartTime());
            ps.setString(4, prescription.getProductNDC());
            ps.setInt(5, prescription.getAppUser().getAppUserId());

            if (prescription.getDoctor() != null) {
                ps.setInt(6, prescription.getDoctor().getDoctorId());

                Doctor doctor = addDoctor(prescription.getDoctor());
                prescription.setDoctor(doctor);
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            if (prescription.getPharmacy() != null) {
                ps.setInt(7, prescription.getPharmacy().getPharmacyId());

                Pharmacy pharmacy = addPharmacy(prescription.getPharmacy());
                prescription.setPharmacy(pharmacy);
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        prescription.setPrescriptionId(keyHolder.getKey().intValue());

        return prescription;
    }

    @Override
    public boolean update(Prescription prescription) {
        final String sql = "update prescription set "
                + "pill_count = ?, "
                + "hourly_interval = ?, "
                + "start_time = ?, "
                + "product_ndc = ?, "
                + "app_user_id = ? "
                + "where prescription_id = ?;";

        return jdbcTemplate.update(sql,
                prescription.getPillCount(),
                prescription.getHourlyInterval(),
                prescription.getStartTime(),
                prescription.getProductNDC(),
                prescription.getAppUser().getAppUserId(),
                prescription.getPrescriptionId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int prescriptionId) {
        jdbcTemplate.update("delete from tracker where prescription_id = ?;", prescriptionId);
        return jdbcTemplate.update("delete from prescription where prescription_id = ?;", prescriptionId) > 0;
    }

    private Doctor addDoctor(Doctor doctor) {
        final String sql = "insert into doctor (first_name, middle_name, last_name, location, phone) "
                + "values (?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, doctor.getFirstName());
            ps.setString(2, doctor.getMiddleName());
            ps.setString(3, doctor.getLastName());
            ps.setString(4, doctor.getLocation());
            ps.setString(5, doctor.getPhone());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        doctor.setDoctorId(keyHolder.getKey().intValue());

        return doctor;
    }

    private Pharmacy addPharmacy(Pharmacy pharmacy) {
        final String sql = "insert into pharmacy (`name`, email, phone, address) "
                + "values (?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pharmacy.getName());
            ps.setString(2, pharmacy.getEmail());
            ps.setString(3, pharmacy.getPhone());
            ps.setString(4, pharmacy.getAddress());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        pharmacy.setPharmacyId(keyHolder.getKey().intValue());

        return pharmacy;
    }
}
