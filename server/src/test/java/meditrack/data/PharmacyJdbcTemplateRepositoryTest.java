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

    @Test
    void shouldAdd() {
        Pharmacy pharmacy = makePharmacy();
        Pharmacy actual = repository.add(pharmacy, 3);

        assertNotNull(actual);
        assertEquals(actual.getPharmacyId(), 4);
    }
    private Pharmacy makePharmacy() {
        Pharmacy pharmacy = new Pharmacy();

        pharmacy.setPharmacyId(1);
        pharmacy.setName("Test");
        pharmacy.setEmail("test@test.com");
        pharmacy.setAddress("123 Test");
        pharmacy.setPhone("1-800-test");

        return pharmacy;
    }
}