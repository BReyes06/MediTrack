package meditrack.data;

import meditrack.models.Pharmacy;

public interface PharmacyRepository {

    public Pharmacy findById(int pharmacyId);

    Pharmacy add(Pharmacy pharmacy, int prescriptionId);

    boolean deleteById(int pharmacyId);

    boolean update(Pharmacy pharmacy);
}
