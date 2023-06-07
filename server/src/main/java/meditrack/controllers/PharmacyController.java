package meditrack.controllers;

import meditrack.domain.PharmacyService;
import meditrack.domain.Result;
import meditrack.models.Pharmacy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pharmacy")
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

    @GetMapping("/user/{appUserId}")
    public List<Pharmacy> findAllByAppUserId(@PathVariable int appUserId) {
        return service.findAllByAppUserId(appUserId);
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

    @PutMapping("/{pharmacyId}")
    public ResponseEntity<?> update(@PathVariable int pharmacyId, @RequestBody Map<String, String> toUpdate) {
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setPharmacyId(pharmacyId);
        pharmacy.setName(toUpdate.get("name"));
        pharmacy.setAddress(toUpdate.get("address"));
        pharmacy.setPhone(toUpdate.get("phone"));
        pharmacy.setEmail(toUpdate.get("email"));

        Result<Pharmacy> result = service.update(pharmacy);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{pharmacyId}")
    public ResponseEntity<?> delete(@PathVariable int pharmacyId) {
        Result<Pharmacy> result = service.deleteById(pharmacyId);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
