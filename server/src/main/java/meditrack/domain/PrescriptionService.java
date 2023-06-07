package meditrack.domain;

import meditrack.data.AppUserRepository;
import meditrack.data.DoctorRepository;
import meditrack.data.PharmacyRepository;
import meditrack.data.PrescriptionRepository;
import meditrack.models.Doctor;
import meditrack.models.Pharmacy;
import meditrack.models.Prescription;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppUserRepository appUserRepository;
    private final DoctorRepository doctorRepository;
    private final PharmacyRepository pharmacyRepository;

    public PrescriptionService(PrescriptionRepository repository, AppUserRepository appUserRepository, DoctorRepository doctorRepository, PharmacyRepository pharmacyRepository) {
        this.prescriptionRepository = repository;
        this.appUserRepository = appUserRepository;
        this.doctorRepository = doctorRepository;
        this.pharmacyRepository = pharmacyRepository;
    }

    public List<Prescription> findAllByUserId(int userId) {
        List<Prescription> prescriptions = prescriptionRepository.findAllById(userId);

        for (Prescription p : prescriptions) {
            Doctor doctor = doctorRepository.findById(p.getDoctor().getDoctorId());
            p.setDoctor(doctor);

            Pharmacy pharmacy = pharmacyRepository.findById(p.getPharmacy().getPharmacyId());
            p.setPharmacy(pharmacy);
        }
        return prescriptions;
    }

    public Prescription findById(int prescriptionId) {
        return prescriptionRepository.findById(prescriptionId);
    }


    public Result<Prescription> add(Prescription prescription) {
        Result<Prescription> result = validate(prescription);

        if (!result.isSuccess()) {
            return result;
        }

        if (prescription.getPrescriptionId() != 0) {
            result.addMessage("Prescription Id cannot be set.", ResultType.INVALID);
            return result;
        }

        prescription = prescriptionRepository.add(prescription);
        result.setPayload(prescription);

        return result;
    }

    public Result<Prescription> update(Prescription prescription) {
        Result<Prescription> result = validate(prescription);

        if (!result.isSuccess()) {
            return result;
        }

        if (prescription.getPrescriptionId() <= 0) {
            result.addMessage("Prescription must have a valid ID", ResultType.INVALID);
            return result;
        }

        if (prescription.getAppUser().getAppUserId() <= 0) {
            result.addMessage("Application user must have a valid ID", ResultType.INVALID);
            return result;
        }

        if (!prescriptionRepository.update(prescription)) {
            String msg = String.format("Prescription %s was not found and could not be updated.", prescription.getPrescriptionId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

    public Result<Prescription> deleteById(int prescriptionId) {
        Result<Prescription> result = new Result<>();

        if (prescriptionId <= 0) {
            result.addMessage("Prescription must have a valid ID", ResultType.INVALID);
            return result;
        }

        if (!prescriptionRepository.deleteById(prescriptionId)) {
            String msg = String.format("Prescription %s was not found and could not be deleted.", prescriptionId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<Prescription> validate(Prescription prescription) {
        Result<Prescription> result = new Result<>();

        if (prescription == null) {
            result.addMessage("Prescription must contain valid information", ResultType.INVALID);
            return result;
        }

        if (prescription.getAppUser() == null) {
            result.addMessage("There must be a valid user for this prescription", ResultType.INVALID);
            return result;
        }

        if (prescription.getPillCount() <= 0) {
            result.addMessage("Please include the pill count of the prescription", ResultType.INVALID);
        }

        if (prescription.getHourlyInterval() <= 0) {
            result.addMessage("Hourly interval must be at least 1 hour", ResultType.INVALID);
        }

        if (prescription.getStartTime() == null) {
            result.addMessage("Please select the day and time application of the medication began", ResultType.INVALID);
        }

        if (prescription.getProductNDC() == null) {
            result.addMessage("Please select a medication for this prescription", ResultType.INVALID);
        }

        return result;
    }
}
