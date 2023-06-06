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

}
