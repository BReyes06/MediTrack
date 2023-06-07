package meditrack.domain;

import meditrack.data.PrescriptionRepository;
import meditrack.data.TrackerRepository;
import meditrack.models.Tracker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackerService {

    private final TrackerRepository trackerRepository;
    private final PrescriptionRepository prescriptionRepository;

    public TrackerService(TrackerRepository trackerRepository, PrescriptionRepository prescriptionRepository) {
        this.trackerRepository = trackerRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    public List<Tracker> findAllByPrescriptionId(int prescriptionId) {
        return trackerRepository.findAllByPrescriptionId(prescriptionId);
    }

    public Tracker findById(int trackerId) {
        return trackerRepository.findById(trackerId);
    }

    public Result<Tracker> add(Tracker tracker) {
        Result<Tracker> result = validate(tracker);
        if (!result.isSuccess()) {
            return result;
        }

        if (tracker.getTrackerId() != 0) {
            result.addMessage("Tracker may not have an Id", ResultType.INVALID);
            return result;
        }


        tracker.setPillCount(
                prescriptionRepository.findById(tracker.getPrescriptionId()).getPillCount()
        );
        result.setPayload(trackerRepository.add(tracker));

        return result;
    }

    public Result<Tracker> update(Tracker tracker) {
        Result<Tracker> result = validate(tracker);
        if (!result.isSuccess()) {
            return result;
        }

        if (tracker.getTrackerId() <= 0) {
            result.addMessage("Tracker must have an Id", ResultType.INVALID);
            return result;
        }

        if (!trackerRepository.update(tracker)) {
            String msg = String.format("Tracker %s not found", tracker.getTrackerId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

    public Result<Tracker> deleteById(int trackerId) {
        Result<Tracker> result = new Result<>();
        if (trackerId <= 0) {
            result.addMessage("Tracker must have an Id", ResultType.INVALID);
            return result;
        }

        if (!trackerRepository.deleteById(trackerId)) {
            String msg = String.format("Tracker %s not found", trackerId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

    private Result<Tracker> validate(Tracker tracker) {
        Result<Tracker> result = new Result<>();

        if (tracker == null) {
            result.addMessage("Please include valid tracker information", ResultType.INVALID);
            return result;
        }

        if (tracker.getPrescriptionId() <= 0) {
            result.addMessage("A tracker must have a prescription id", ResultType.INVALID);
        }

        if (tracker.getAdministrationTime() == null) {
            result.addMessage("Please include an administration time", ResultType.INVALID);
        }

        return result;
    }
}
