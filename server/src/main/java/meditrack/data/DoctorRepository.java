package meditrack.data;

import meditrack.models.Doctor;

import java.util.List;

public interface DoctorRepository {
    public Doctor findById(int doctorId);

    List<Doctor> findByAllByUserId(int appUserId);

    Doctor add(Doctor doctor, int prescriptionId);

    boolean update(Doctor doctor);

    boolean deleteById(int doctorId);
}
