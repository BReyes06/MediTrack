package meditrack.data;

import meditrack.models.AppUser;
import meditrack.models.Doctor;
import meditrack.models.Pharmacy;
import meditrack.models.Prescription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PrescriptionJdbcTemplateRepositoryTest {

    final static  int NEXT_ID = 3;

    @Autowired
    PrescriptionJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllById() {
        List<Prescription> prescriptions = repository.findAllById(1);

        assertTrue(prescriptions.size() >= 2 && prescriptions.size() <= 5);
    }

    @Test
    void shouldAdd() {
        Prescription prescription = makePrescription();
        Prescription actual = repository.add(prescription);

        assertNotNull(actual);
        assertTrue(actual.getPrescriptionId() >= NEXT_ID);

    }

    @Test
    void shouldAddWithNullDoctor() {
        Prescription prescription = makePrescription();
        prescription.setDoctor(null);

        Prescription actual = repository.add(prescription);

        assertNotNull(actual);
        assertTrue(actual.getPrescriptionId() >= NEXT_ID);
    }

    @Test
    void shouldAddWithNullPharmacy() {
        Prescription prescription = makePrescription();
        prescription.setPharmacy(null);

        Prescription actual = repository.add(prescription);

        assertNotNull(actual);
        assertTrue(actual.getPrescriptionId() >= NEXT_ID);
    }

    @Test
    void shouldUpdate() {
        Prescription prescription = makePrescription();
        prescription.setPrescriptionId(2);

        assertTrue(repository.update(prescription));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(1));
    }

    private Prescription makePrescription() {
        Prescription prescription = new Prescription();

        prescription.setHourlyInterval(6);
        prescription.setPillCount(100);
        prescription.setProductNDC("0000-0000");
        prescription.setStartTime("2023-06-01 10:00:00");
        prescription.setAppUser(makeUser());
        prescription.setDoctor(makeDoctor());
        prescription.setPharmacy(makePharmacy());

        return prescription;
    }

    private AppUser makeUser() {
        AppUser user = new AppUser();
        user.setAppUserId(1);

        return user;
    }

    private Doctor makeDoctor() {
        Doctor doctor = new Doctor();

        doctor.setDoctorId(1);
        doctor.setFirstName("Tony");
        doctor.setMiddleName("Tony");
        doctor.setLastName("Chopper");
        doctor.setLocation("Thousand Sunny");
        doctor.setPhone("1-800-tanuki");

        return doctor;
    }

    private Pharmacy makePharmacy() {
        Pharmacy pharmacy = new Pharmacy();

        pharmacy.setPharmacyId(1);
        pharmacy.setName("CVS");
        pharmacy.setEmail("test@cvs.com");
        pharmacy.setAddress("90 Corner");
        pharmacy.setPhone("1-800-callcvs");

        return pharmacy;
    }
}