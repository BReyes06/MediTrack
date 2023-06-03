package meditrack.data;

import meditrack.models.Prescription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PrescriptionJdbcTemplateRepositoryTest {

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

        assertEquals(2, prescriptions.size());
    }



}