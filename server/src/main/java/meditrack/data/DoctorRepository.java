package meditrack.data;

import meditrack.models.Doctor;

public interface DoctorRepository {
    public Doctor findById(int doctorId);
}
