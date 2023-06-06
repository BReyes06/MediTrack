package meditrack.domain;

import meditrack.data.DoctorRepository;
import meditrack.models.Doctor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }


    public Doctor findById(int doctorId) {
        if (doctorId <= 0) {
            return null;
        }
        return repository.findById(doctorId);
    }

    public List<Doctor> findAllByUserId(int appUserId) {
        if (appUserId <= 0) {
            return null;
        }
        return repository.findByAllByUserId(appUserId);
    }

    public Result<Doctor> add(Doctor doctor, int prescriptionId) {
        Result<Doctor> result = validate(doctor);
        if (!result.isSuccess()) {
            return result;
        }

        if (doctor.getDoctorId() != 0) {
            result.addMessage("Doctor may not have an Id", ResultType.INVALID);
            return result;
        }

        if (prescriptionId <= 0) {
            result.addMessage("Prescription Id is required", ResultType.INVALID);
            return result;
        }

        result.setPayload(
                repository.add(doctor, prescriptionId)
        );

        return result;
    }

    public Result<Doctor> update(Doctor doctor) {
        Result<Doctor> result = validate(doctor);

        if (!result.isSuccess()) {
            return result;
        }

        if (doctor.getDoctorId() <= 0) {
            result.addMessage("Doctor must have an Id", ResultType.INVALID);
        }

        if (!repository.update(doctor)) {
            String msg = String.format("Doctor %s was not found", doctor.getDoctorId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public Result<Doctor> deleteById(int doctorId) {
        Result<Doctor> result = new Result<>();
        if (doctorId <= 0) {
            result.addMessage("Doctor must have an Id", ResultType.INVALID);
            return result;
        }

        if (!repository.deleteById(doctorId)) {
            String msg = String.format("Doctor %s was not found", doctorId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<Doctor> validate(Doctor doctor) {
        Result<Doctor> result = new Result<>();

        if (doctor == null) {
            result.addMessage("Please enter valid information for doctor", ResultType.INVALID);
            return result;
        }

        if (doctor.getFirstName() == null) {
            result.addMessage("Doctor must have a first name", ResultType.INVALID);
        }

        if (doctor.getLastName() == null) {
            result.addMessage("Doctor must have a last name", ResultType.INVALID);
        }

        if (doctor.getPhone() == null) {
            result.addMessage("Doctor must have a phone number", ResultType.INVALID);
        }

        if (doctor.getLocation() == null) {
            result.addMessage("Doctor must have an location/address", ResultType.INVALID);
        }

        return result;
    }
}
