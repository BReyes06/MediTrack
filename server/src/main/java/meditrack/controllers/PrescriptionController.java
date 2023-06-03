package meditrack.controllers;

import meditrack.domain.PrescriptionService;
import meditrack.domain.Result;
import meditrack.models.Prescription;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {
    private final PrescriptionService service;

    public PrescriptionController(PrescriptionService service) {
        this.service = service;
    }

    @GetMapping("/prescriptions/{username}")
    public List<Prescription> findAll(@PathVariable String username) {
        return service.findAllByUsername(username);
    }

    @GetMapping("/{prescriptionId}")
    public ResponseEntity<?> findById(@PathVariable int prescriptionId) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Prescription prescription) {
        Result<Prescription> result = service.add(prescription);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }
}
