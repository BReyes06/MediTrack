package meditrack.data;

import meditrack.models.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DoctorJdbcTemplateRepositoryTest {

    @Autowired
    DoctorJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindById() {
        Doctor doctor = repository.findById(1);

        assertEquals("Phillip", doctor.getFirstName());
    }

    @Test
    void shouldFindAllByUseId() {
        List<Doctor> result = repository.findByAllByUserId(1);

        assertEquals(2, result.size());
    }

    @Test
    void shouldAdd() {
        Doctor doctor = makeDoctor();
        Doctor actual = repository.add(doctor, 3);

        assertNotNull(actual);
        assertEquals(actual.getDoctorId(), 4);
    }

    @Test
    void shouldUpdate() {
        Doctor doctor = makeDoctor();
        doctor.setDoctorId(2);

        assertTrue(repository.update(doctor));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
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