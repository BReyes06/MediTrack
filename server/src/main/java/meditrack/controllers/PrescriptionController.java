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
@RequestMapping("/prescription")
public class PrescriptionController {
    private final PrescriptionService service;

    public PrescriptionController(PrescriptionService service) {
        this.service = service;
    }

    @GetMapping("/prescriptions/{userId}")
    public List<Prescription> findAll(@PathVariable int userId) {
        return service.findAllByUserId(userId);
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


    @PutMapping("/update/{prescriptionId}")
    public ResponseEntity<?> update(@RequestBody Map<String, String> prescription) {
        Prescription toUpdate = new Prescription();
        AppUser appUser = new AppUser();
        appUser.setAppUserId(Integer.parseInt(prescription.get("app_user_id")));
        toUpdate.setAppUser(appUser);
        toUpdate.setPillCount(Integer.parseInt(prescription.get("pillCount")));
        toUpdate.setHourlyInterval(Integer.parseInt(prescription.get("hourlyInterval")));
        toUpdate.setProductNDC(prescription.get("product_ndc"));
        toUpdate.setStartTime(prescription.get("startTime"));

        Result<Prescription> result = service.update(toUpdate);

        return null;
    }
}
