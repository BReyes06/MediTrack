package meditrack.domain;

import meditrack.data.DoctorRepository;
import meditrack.data.PharmacyRepository;
import meditrack.data.PrescriptionRepository;
import meditrack.models.AppUser;
import meditrack.models.Doctor;
import meditrack.models.Pharmacy;
import meditrack.models.Prescription;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PrescriptionServiceTest {

    @Autowired
    PrescriptionService service;

    @MockBean
    PrescriptionRepository prescriptionRepository;

    @MockBean
    DoctorRepository doctorRepository;

    @MockBean
    PharmacyRepository pharmacyRepository;

    @Test
    void shouldFindAllByUsername() {
        Prescription prescription = makePrescription();
        Prescription prescription2 = prescription;
        prescription2.setPrescriptionId(2);
        prescription2.setProductNDC("1111-1111");

        when(prescriptionRepository.findAllById(1)).thenReturn(List.of(prescription, prescription2));
        when(doctorRepository.findById(1)).thenReturn(prescription.getDoctor());
        when(pharmacyRepository.findById(1)).thenReturn(prescription.getPharmacy());

        List<Prescription> result = service.findAllByUserId(1);

        assertEquals(2, result.size());
    }

    @Test
    void shouldAddValidPrescription() {
        Prescription prescription = makePrescription();
        prescription.setPrescriptionId(0);
        Prescription expected = makePrescription();

        when(prescriptionRepository.add(prescription)).thenReturn(expected);

        Result<Prescription> result = service.add(prescription);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotAddWhenNullPrescription() {
        Result<Prescription> result = service.add(null);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Prescription must contain valid information", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenNullAppUser() {
        Prescription prescription = makePrescription();
        prescription.setAppUser(null);

        Result<Prescription> result = service.add(prescription);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("There must be a valid user for this prescription", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenPillCountInvalid() {
        Prescription prescription = makePrescription();
        prescription.setPillCount(0);

        Result<Prescription> result = service.add(prescription);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Please include the pill count of the prescription", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenHourlyIntervalInvalid() {
        Prescription prescription = makePrescription();
        prescription.setHourlyInterval(0);

        Result<Prescription> result = service.add(prescription);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Hourly interval must be at least 1 hour", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenStartTimeInvalid() {
        Prescription prescription = makePrescription();
        prescription.setStartTime(null);

        Result<Prescription> result = service.add(prescription);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Please select the day and time application of the medication began", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenProductNDCInvalid() {
        Prescription prescription = makePrescription();
        prescription.setProductNDC(null);

        Result<Prescription> result = service.add(prescription);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Please select a medication for this prescription", result.getMessages().get(0));
    }

    @Test
    void shouldUpdateValidPrescription() {
        Prescription prescription = makePrescription();
        prescription.setPrescriptionId(3);

        when(prescriptionRepository.update(prescription)).thenReturn(true);
        Result<Prescription> result = service.update(prescription);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotUpdateWhenInvalidPrescriptionId() {
        Prescription prescription = makePrescription();
        prescription.setPrescriptionId(0);

        Result<Prescription> result = service.update(prescription);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Prescription must have a valid ID", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNonExistentPrescriptionId() {
        Prescription prescription = makePrescription();
        prescription.setPrescriptionId(999);

        when(prescriptionRepository.update(prescription)).thenReturn(false);
        Result<Prescription> result = service.update(prescription);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("Prescription 999 was not found and could not be updated.", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWhenNullPrescription() {
        Result<Prescription> result = service.update(null);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Prescription must contain valid information", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWhenInvalidAppUserId() {
        Prescription prescription = makePrescription();
        prescription.getAppUser().setAppUserId(0);

        Result<Prescription> result = service.update(prescription);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Application user must have a valid ID", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWhenNullAppUser() {
        Prescription prescription = makePrescription();
        prescription.setAppUser(null);

        Result<Prescription> result = service.update(prescription);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("There must be a valid user for this prescription", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWhenInvalidPillCount() {
        Prescription prescription = makePrescription();
        prescription.setPillCount(0);

        Result<Prescription> result = service.update(prescription);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Please include the pill count of the prescription", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWhenInvalidHourlyInterval() {
        Prescription prescription = makePrescription();
        prescription.setHourlyInterval(0);

        Result<Prescription> result = service.update(prescription);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Hourly interval must be at least 1 hour", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWhenInvalidStartTime() {
        Prescription prescription = makePrescription();
        prescription.setStartTime(null);

        Result<Prescription> result = service.update(prescription);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Please select the day and time application of the medication began", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWhenInvalidProductNDC() {
        Prescription prescription = makePrescription();
        prescription.setProductNDC(null);

        Result<Prescription> result = service.update(prescription);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Please select a medication for this prescription", result.getMessages().get(0));
    }

    @Test
    void shouldDeleteValidPrescriptionId() {
        Prescription prescription = makePrescription();

        when(prescriptionRepository.deleteById(1)).thenReturn(true);

        Result<Prescription> result = service.deleteById(prescription.getPrescriptionId());

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteNonExistentPrescriptionId() {
        Prescription prescription = makePrescription();
        prescription.setPrescriptionId(999);

        when(prescriptionRepository.deleteById(prescription.getPrescriptionId())).thenReturn(false);
        Result<Prescription> result = service.deleteById(prescription.getPrescriptionId());

        assertEquals(ResultType.NOT_FOUND, result.getType());
    }

    private Prescription makePrescription() {
        Prescription prescription = new Prescription();

        prescription.setPrescriptionId(1);
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