package meditrack.domain;

import meditrack.data.DoctorRepository;
import meditrack.data.PharmacyRepository;
import meditrack.data.PrescriptionRepository;
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

        List<Prescription> result = service.findAllByUsername("mchan");

        assertEquals(2, result.size());
    }

    private Prescription makePrescription() {
        Prescription prescription = new Prescription();

        prescription.setPrescriptionId(1);
        prescription.setHourlyInterval(6);
        prescription.setPillCount(100);
        prescription.setProductNDC("0000-0000");
        prescription.setStartTime("01/-6/2023 10:00:00");
        prescription.setDoctor(makeDoctor());
        prescription.setPharmacy(makePharmacy());

        return prescription;
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