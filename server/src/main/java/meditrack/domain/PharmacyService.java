package meditrack.domain;

import meditrack.data.PharmacyRepository;
import meditrack.models.Pharmacy;
import org.springframework.stereotype.Service;

@Service
public class PharmacyService {

    private final PharmacyRepository repository;

    public PharmacyService(PharmacyRepository repository) {
        this.repository = repository;
    }

    public Pharmacy findById(int pharmacyId) {
        if (pharmacyId <= 0) {
            return null;
        }
        return repository.findById(pharmacyId);
    }

    public Result<Pharmacy> add(Pharmacy pharmacy, int prescriptionId) {
        Result<Pharmacy> result = validate(pharmacy);
        if (!result.isSuccess()) {
            return result;
        }

        if (pharmacy.getPharmacyId() != 0) {
            result.addMessage("Pharmacy may not have an Id.", ResultType.INVALID);
            return result;
        }

        if (prescriptionId <= 0) {
            result.addMessage("Prescription Id is required", ResultType.INVALID);
            return result;
        }

        result.setPayload(
                repository.add(pharmacy, prescriptionId)
        );

        return result;
    }

    public Result<Pharmacy> update(Pharmacy pharmacy) {
        Result<Pharmacy> result = validate(pharmacy);
        if (!result.isSuccess()) {
            return result;
        }

        if (pharmacy.getPharmacyId() <= 0) {
            result.addMessage("Pharmacy must have and Id", ResultType.INVALID);
            return result;
        }

        if (!repository.update(pharmacy)) {
            String msg = String.format("Pharmacy %s was not found", pharmacy.getPharmacyId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;

    }

    public Result<Pharmacy> deleteById(int pharmacyId) {
        Result<Pharmacy> result = new Result<>();
        if (pharmacyId <= 0) {
            result.addMessage("Pharmacy must have a valid Id", ResultType.INVALID);
            return result;
        }

        if (!repository.deleteById(pharmacyId)) {
            String msg = String.format("Pharmacy %s was not found", pharmacyId);
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }

        return result;
    }

    private Result<Pharmacy> validate(Pharmacy pharmacy) {
        Result<Pharmacy> result = new Result<>();

        if (pharmacy == null) {
            result.addMessage("Please enter valid information for pharmacy", ResultType.INVALID);
            return result;
        }

        if (pharmacy.getName() == null) {
            result.addMessage("Pharmacy must have a name", ResultType.INVALID);
        }

        if (pharmacy.getPhone() == null) {
            result.addMessage("Pharmacy must have a phone number", ResultType.INVALID);
        }

        if (pharmacy.getAddress() == null) {
            result.addMessage("Pharmacy must have an address", ResultType.INVALID);
        }

        return result;
    }
}
