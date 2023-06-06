package meditrack.data;

import meditrack.models.Prescription;

import java.util.List;

public interface PrescriptionRepository {
    public List<Prescription> findAllById(int appUserId);

    Prescription findById(int prescriptionId);

    public Prescription add(Prescription prescription);

    boolean update(Prescription prescription);

    boolean deleteById(int prescriptionId);
}
