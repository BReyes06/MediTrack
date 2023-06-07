package meditrack.data;

import meditrack.models.Tracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrackerJdbcTemplateRepositoryTest {

    @Autowired
    TrackerJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAllByPrescriptionId() {
        List<Tracker> result = repository.findAllByPrescriptionId(1);

        assertTrue(result.size() >= 2 && result.size() <= 5);

    }

    @Test
    void shouldFindById() {
        Tracker result = repository.findById(1);
        assertNotNull(result);
        assertEquals(1, result.getPrescriptionId());
    }

    @Test
    void shouldAdd() {
        Tracker tracker = makeTracker();
        Tracker result = repository.add(tracker);

        assertNotNull(result);
        assertEquals(5, result.getTrackerId());
    }

    @Test
    void update() {
        Tracker tracker = makeTracker();
        tracker.setTrackerId(3);

        assertTrue(repository.update(tracker));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(2));
    }



    private Tracker makeTracker() {
        Tracker tracker = new Tracker();

        tracker.setAdministrationTime("2023-06-06 16:00:00");
        tracker.setPrescriptionId(2);

        return tracker;
    }
}