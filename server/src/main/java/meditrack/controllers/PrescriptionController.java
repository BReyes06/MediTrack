package meditrack.controllers;

import meditrack.domain.PrescriptionService;
import meditrack.domain.Result;
import meditrack.models.AppUser;
import meditrack.models.Prescription;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prescription")
public class PrescriptionController {
    private final PrescriptionService service;

    public PrescriptionController(PrescriptionService service) {
        this.service = service;
    }

    @GetMapping("/user/{userId}")
    public List<Prescription> findAll(@PathVariable int userId) {
        return service.findAllByUserId(userId);
    }

    @GetMapping("/{prescriptionId}")
    public ResponseEntity<?> findById(@PathVariable int prescriptionId) {
        Prescription result = service.findById(prescriptionId);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Map<String, String> prescription) {
        Prescription toAdd = new Prescription();

        AppUser appUser = new AppUser();
        appUser.setAppUserId(Integer.parseInt(prescription.get("app_user_id")));
        toAdd.setAppUser(appUser);

        toAdd.setPillCount(Integer.parseInt(prescription.get("pillCount")));
        toAdd.setHourlyInterval(Integer.parseInt(prescription.get("hourlyInterval")));
        toAdd.setProductNDC(prescription.get("product_ndc"));
        toAdd.setStartTime(prescription.get("startTime"));

        Result<Prescription> result = service.add(toAdd);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }


    @PutMapping("/{prescriptionId}")
    public ResponseEntity<?> update(@RequestBody Map<String, String> toUpdate) {
        Prescription prescription = new Prescription();
        AppUser appUser = new AppUser();

        appUser.setAppUserId(Integer.parseInt(toUpdate.get("app_user_id")));
        prescription.setAppUser(appUser);
        prescription.setPillCount(Integer.parseInt(toUpdate.get("pillCount")));
        prescription.setHourlyInterval(Integer.parseInt(toUpdate.get("hourlyInterval")));
        prescription.setProductNDC(toUpdate.get("product_ndc"));
        prescription.setStartTime(toUpdate.get("startTime"));
        prescription.setPrescriptionId(Integer.parseInt(toUpdate.get("prescriptionId")));

        Result<Prescription> result = service.update(prescription);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{prescriptionId}")
    public ResponseEntity<?> delete(@PathVariable int prescriptionId) {
        Result<Prescription> result = service.deleteById(prescriptionId);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
