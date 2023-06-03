package meditrack.data;

import meditrack.models.Pharmacy;

public interface PharmacyRepository {

    public Pharmacy findById(int pharmacyId);

}
