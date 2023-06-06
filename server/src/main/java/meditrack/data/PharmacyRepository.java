package meditrack.data;

import meditrack.models.Pharmacy;

import java.util.List;

public interface PharmacyRepository {

    public Pharmacy findById(int pharmacyId);

    List<Pharmacy> findAllByAppUserId(int appUserId);

    Pharmacy add(Pharmacy pharmacy, int prescriptionId);

    boolean deleteById(int pharmacyId);

    boolean update(Pharmacy pharmacy);
}
