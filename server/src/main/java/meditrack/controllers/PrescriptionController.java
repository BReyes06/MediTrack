package meditrack.controllers;

import meditrack.domain.PrescriptionService;
import meditrack.domain.Result;
import meditrack.models.Prescription;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Map<String, String> prescription) {
        Prescription toAdd = new Prescription();
        toAdd.setPillCount(Integer.parseInt(prescription.get("pillCount")));
        toAdd.setHourlyInterval(Integer.parseInt(prescription.get("hourlyInterval")));
        toAdd.setProductNDC(prescription.get("product_ndc"));
        toAdd.setStartTime(prescription.get("startTime"));
        toAdd.getAppUser().setAppUserId(Integer.parseInt(prescription.get("app_user_id")));

        Result<Prescription> result = service.add(toAdd);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }
}
