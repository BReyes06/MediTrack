package meditrack.controllers;

import meditrack.domain.PharmacyService;
import meditrack.domain.Result;
import meditrack.models.Pharmacy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/pharmacy")
public class PharmacyController {

    private final PharmacyService service;

    public PharmacyController(PharmacyService service) {
        this.service = service;
    }

    @GetMapping("/{pharmacyId}")
    public ResponseEntity<?> findById(@PathVariable int pharmacyId) {
        Pharmacy pharmacy = service.findById(pharmacyId);

        if (pharmacy == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pharmacy, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Map<String, String> toAdd) {
        Pharmacy prescription = new Pharmacy();
        prescription.setName(toAdd.get("name"));
        prescription.setEmail(toAdd.get("email"));
        prescription.setPhone(toAdd.get("phone"));
        prescription.setAddress(toAdd.get("address"));

        int prescriptionId = Integer.parseInt(toAdd.get("prescriptionId"));

        Result<Pharmacy> result = service.add(prescription, prescriptionId);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
    }
}
