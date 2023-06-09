package meditrack.domain;

import meditrack.data.DoctorRepository;
import meditrack.models.Doctor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class DoctorServiceTest {

    @Autowired
    DoctorService service;

    @MockBean
    DoctorRepository repository;

    @Test
    void shouldFindById() {
        Doctor doctor = makeDoctor();
        when(repository.findById(1)).thenReturn(doctor);

        Doctor actual = service.findById(1);

        assertNotNull(actual);
        assertEquals(doctor, actual);
    }

    @Test
    void shouldFindAllByUserId() {
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(makeDoctor());
        doctorList.add(makeDoctor());

        when(repository.findByAllByUserId(1)).thenReturn(doctorList);

        List<Doctor> actual = service.findAllByUserId(1);

        assertEquals(2, doctorList.size());
    }

    @Test
    void shouldAdd() {
        Doctor doctor = makeDoctor();

        when(repository.add(doctor, 1)).thenReturn(doctor);
        Result<Doctor> result = service.add(doctor, 1);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotAddNullDoctor() {
        Result<Doctor> result = service.add(null, 1);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Please enter valid information for doctor", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddNInvalidDoctorId() {
        Doctor doctor = makeDoctor();
        doctor.setDoctorId(2);

        Result<Doctor> result = service.add(doctor, 1);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Doctor may not have an Id", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutPrescriptionId() {
        Doctor doctor = makeDoctor();

        Result<Doctor> result = service.add(doctor, 0);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Prescription Id is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddNullFirstName() {
        Doctor doctor = makeDoctor();
        doctor.setFirstName(null);

        Result<Doctor> result = service.add(doctor, 1);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Doctor must have a first name", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddNullLastName() {
        Doctor doctor = makeDoctor();
        doctor.setLastName(null);

        Result<Doctor> result = service.add(doctor, 1);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Doctor must have a last name", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddNullPhone() {
        Doctor doctor = makeDoctor();
        doctor.setPhone(null);

        Result<Doctor> result = service.add(doctor, 1);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Doctor must have a phone number", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddLocation() {
        Doctor doctor = makeDoctor();
        doctor.setLocation(null);

        Result<Doctor> result = service.add(doctor, 1);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Doctor must have an location/address", result.getMessages().get(0));
    }

    @Test
    void shouldUpdate() {
        Doctor doctor = makeDoctor();
        doctor.setDoctorId(1);

        when(repository.update(doctor)).thenReturn(true);
        Result<Doctor> result = service.update(doctor);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotUpdateWithoutDoctorId() {
        Doctor doctor = makeDoctor();
        doctor.setDoctorId(-1);

        Result<Doctor> result = service.update(doctor);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("Doctor must have an Id", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullDoctor() {
        Result<Doctor> result = service.update(null);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Please enter valid information for doctor", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNonExistentDoctorId() {
        Doctor doctor = makeDoctor();
        doctor.setDoctorId(99);

        when(repository.update(doctor)).thenReturn(false);
        Result<Doctor> result = service.update(doctor);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("Doctor 99 was not found", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullFirstName() {
        Doctor doctor = makeDoctor();
        doctor.setFirstName(null);

        Result<Doctor> result = service.update(doctor);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Doctor must have a first name", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullLastName() {
        Doctor doctor = makeDoctor();
        doctor.setLastName(null);

        Result<Doctor> result = service.update(doctor);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Doctor must have a last name", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullPhone() {
        Doctor doctor = makeDoctor();
        doctor.setPhone(null);

        Result<Doctor> result = service.update(doctor);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Doctor must have a phone number", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNullLocation() {
        Doctor doctor = makeDoctor();
        doctor.setLocation(null);

        Result<Doctor> result = service.update(doctor);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Doctor must have an location/address", result.getMessages().get(0));
    }

    @Test
    void shouldDelete() {
        when(repository.deleteById(1)).thenReturn(true);
        Result<Doctor> result = service.deleteById(1);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteInvalidDoctorId() {
        Result<Doctor> result = service.deleteById(-1);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Doctor must have an Id", result.getMessages().get(0));
    }

    @Test
    void shouldNotDeleteNonExistentDoctorId() {
        when(repository.deleteById(99)).thenReturn(false);
        Result<Doctor> result = service.deleteById(99);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("Doctor 99 was not found", result.getMessages().get(0));
    }

    private Doctor makeDoctor() {
        Doctor doctor = new Doctor();

        doctor.setFirstName("Tony");
        doctor.setMiddleName("Tony");
        doctor.setLastName("Chopper");
        doctor.setLocation("Thousand Sunny");
        doctor.setPhone("1-800-tanuki");

        return doctor;
    }

}