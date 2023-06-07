package meditrack.models;

public class Tracker {
    private int trackerId;
    private String administrationTime;
    private int pillCount;
    private int prescriptionId;

    public int getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(int trackerId) {
        this.trackerId = trackerId;
    }

    public String getAdministrationTime() {
        return administrationTime;
    }

    public void setAdministrationTime(String administrationTime) {
        this.administrationTime = administrationTime;
    }

    public int getPillCount() {
        return pillCount;
    }

    public void setPillCount(int pillCount) {
        this.pillCount = pillCount;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
}
