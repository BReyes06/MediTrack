package meditrack.domain;

import meditrack.data.DoctorRepository;
import meditrack.models.Doctor;
import org.springframework.stereotype.Service;

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
