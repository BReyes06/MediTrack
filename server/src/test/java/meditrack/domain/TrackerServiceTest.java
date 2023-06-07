package meditrack.domain;

import meditrack.data.TrackerRepository;
import meditrack.models.Tracker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TrackerServiceTest {

    @Autowired
    TrackerService service;

    @MockBean
    TrackerRepository repository;

    @Test
    void shouldFindAllByPrescriptionId() {
        List<Tracker> trackerList = new ArrayList<>();
        trackerList.add(makeTracker());
        trackerList.add(makeTracker());

        when(repository.findAllByPrescriptionId(1)).thenReturn(trackerList);
        List<Tracker> result = service.findAllByPrescriptionId(1);
        assertEquals(2, trackerList.size());
    }

    @Test
    void shouldFindById() {
        Tracker tracker = makeTracker();

        when(repository.findById(1)).thenReturn(tracker);
        Tracker result = service.findById(1);

        assertNotNull(result);
        assertEquals("2023-06-06 16:00:00", result.getAdministrationTime());
    }

    @Test
    void shouldAddValidTracker() {
        Tracker tracker = makeTracker();

        when(repository.add(tracker)).thenReturn(tracker);
        Result<Tracker> result = service.add(tracker);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotAddWithTrackerId() {
        Tracker tracker = makeTracker();
        tracker.setTrackerId(1);

        Result<Tracker> result = service.add(tracker);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Tracker may not have an Id", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddNullTracker() {
        Result<Tracker> result = service.add(null);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Please include valid tracker information", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutPrescriptionId() {
        Tracker tracker = makeTracker();
        tracker.setPrescriptionId(-1);

        Result<Tracker> result = service.add(tracker);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("A tracker must have a prescription id", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutAdministrationTime() {
        Tracker tracker = makeTracker();
        tracker.setAdministrationTime(null);

        Result<Tracker> result = service.add(tracker);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Please include an administration time", result.getMessages().get(0));
    }

    @Test
    void shouldUpdate() {
        Tracker tracker = makeTracker();
        tracker.setTrackerId(1);

        when(repository.update(tracker)).thenReturn(true);
        Result<Tracker> result = service.update(tracker);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotUpdateWithoutId() {
        Tracker tracker = makeTracker();

        Result<Tracker> result = service.update(tracker);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Tracker must have an Id", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNonExistentId() {
        Tracker tracker = makeTracker();
        tracker.setTrackerId(99);

        when(repository.update(tracker)).thenReturn(false);
        Result<Tracker> result = service.update(tracker);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("Tracker 99 not found", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNull() {
        Result<Tracker> result = service.add(null);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Please include valid tracker information", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithoutPrescriptionId() {
        Tracker tracker = makeTracker();
        tracker.setTrackerId(1);
        tracker.setPrescriptionId(-1);

        Result<Tracker> result = service.update(tracker);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("A tracker must have a prescription id", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateWithoutAdministrationTime() {
        Tracker tracker = makeTracker();
        tracker.setTrackerId(1);
        tracker.setAdministrationTime(null);

        Result<Tracker> result = service.update(tracker);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Please include an administration time", result.getMessages().get(0));
    }

    @Test
    void shouldDelete() {
        when(repository.deleteById(1)).thenReturn(true);
        Result<Tracker> result = service.deleteById(1);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteNonExistentId() {
        when(repository.deleteById(99)).thenReturn(false);
        Result<Tracker> result = service.deleteById(99);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("Tracker 99 not found", result.getMessages().get(0));
    }

    @Test
    void shouldNotDeleteWithoutId() {
        Result<Tracker> result = service.deleteById(-1);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Tracker must have an Id", result.getMessages().get(0));
    }

    private Tracker makeTracker() {
        Tracker tracker = new Tracker();

        tracker.setAdministrationTime("2023-06-06 16:00:00");
        tracker.setPrescriptionId(2);

        return tracker;
    }
}