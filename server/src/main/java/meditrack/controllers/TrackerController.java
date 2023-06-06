package meditrack.controllers;

import meditrack.domain.Result;
import meditrack.domain.TrackerService;
import meditrack.models.Tracker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tracker")
public class TrackerController {

    private final TrackerService service;


    public TrackerController(TrackerService service) {
        this.service = service;
    }

    @GetMapping("/prescription/{prescriptionId}")
    public List<Tracker> findAllByPrescriptionId(@PathVariable int prescriptionId) {
        return service.findAllByPrescriptionId(prescriptionId);
    }

    @GetMapping("/{trackerId}")
    public ResponseEntity<?> findById(@PathVariable int trackerId) {
        Tracker result = service.findById(trackerId);

        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> add(@RequestBody Map<String, String> toAdd) {
        Tracker tracker = new Tracker();
        tracker.setPrescriptionId(Integer.parseInt(toAdd.get("prescriptionId")));
        tracker.setAdministrationTime(toAdd.get("administrationTime"));

        Result<Tracker> result = service.add(tracker);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{trackerId}")
    public ResponseEntity<?> update(@PathVariable int trackerId, @RequestBody Map<String, String> toUpdate) {
        Tracker tracker = new Tracker();
        tracker.setTrackerId(trackerId);
        tracker.setAdministrationTime(toUpdate.get("administrationTime"));
        tracker.setPrescriptionId(Integer.parseInt(toUpdate.get("prescriptionId")));

        Result<Tracker> result = service.update(tracker);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{trackerId}")
    public ResponseEntity<?> delete(@PathVariable int trackerId) {
        Result<Tracker> result = service.deleteById(trackerId);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
