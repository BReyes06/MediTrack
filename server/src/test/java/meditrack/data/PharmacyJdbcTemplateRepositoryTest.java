package meditrack.data;

import meditrack.models.Pharmacy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PharmacyJdbcTemplateRepositoryTest {

    @Autowired
    PharmacyJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindById() {
        Pharmacy pharmacy = repository.findById(1);

        assertEquals("Rite Aid", pharmacy.getName());
    }
}