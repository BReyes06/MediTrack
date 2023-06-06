package meditrack.data;

import meditrack.models.Tracker;

import java.util.List;

public interface TrackerRepository {
    List<Tracker> findAllByPrescriptionId(int prescriptionId);

    Tracker findById(int trackerId);

    Tracker add(Tracker tracker);

    boolean update(Tracker tracker);

    boolean deleteById(int trackerId);
}
