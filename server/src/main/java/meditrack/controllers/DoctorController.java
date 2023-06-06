package meditrack.controllers;

import meditrack.domain.DoctorService;
import meditrack.domain.Result;
import meditrack.models.Doctor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<?> findById(@PathVariable int doctorId) {
        Doctor result = service.findById(doctorId);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Map<String, String> toAdd) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(toAdd.get("firstName"));
        doctor.setMiddleName(toAdd.get("middleName"));
        doctor.setLastName(toAdd.get("lastName"));
        doctor.setLocation(toAdd.get("location"));
        doctor.setPhone(toAdd.get("phone"));

        int prescriptionId = Integer.parseInt(toAdd.get("prescriptionId"));

        Result<Doctor> result = service.add(doctor, prescriptionId);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<?> update(@PathVariable int doctorId, @RequestBody Map<String, String> toUpdate) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(toUpdate.get("firstName"));
        doctor.setMiddleName(toUpdate.get("middleName"));
        doctor.setLastName(toUpdate.get("lastName"));
        doctor.setLocation(toUpdate.get("location"));
        doctor.setPhone(toUpdate.get("phone"));

        Result<Doctor> result = service.update(doctor);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<?> deleteById(@PathVariable int doctorId) {
        Result<Doctor> result = service.deleteById(doctorId);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
