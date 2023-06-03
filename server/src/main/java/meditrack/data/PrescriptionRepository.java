package meditrack.data;

import meditrack.models.Prescription;

import java.util.List;

public interface PrescriptionRepository {
    public List<Prescription> findAllById(int appUserId);

    public Prescription add();

}
