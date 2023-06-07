package meditrack.data;

import meditrack.models.Pharmacy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
    void shouldFindAllByUserId() {
        List<Pharmacy> result = repository.findAllByAppUserId(1);

        assertTrue(result.size() >= 3 && result.size() <= 4);
    }

    @Test
    void shouldAdd() {
        Pharmacy pharmacy = makePharmacy();
        Pharmacy actual = repository.add(pharmacy, 3);

        assertNotNull(actual);
        assertTrue(pharmacy.getPharmacyId() <= 6 && pharmacy.getPharmacyId() >= 4);
    }

    @Test
    void shouldUpdate() {
        Pharmacy pharmacy = makePharmacy();
        pharmacy.setPharmacyId(2);

        assertTrue(repository.update(pharmacy));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
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