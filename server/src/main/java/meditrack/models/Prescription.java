package meditrack.models;


import java.time.LocalDateTime;

public class Prescription {
    private int prescriptionId;
    private int hourlyInterval;
    private int pillCount;
    private String startTime;
    private String productNDC;
    private AppUser appUser;
    private Doctor doctor;
    private Pharmacy pharmacy;

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getHourlyInterval() {
        return hourlyInterval;
    }

    public void setHourlyInterval(int hourlyInterval) {
        this.hourlyInterval = hourlyInterval;
    }

    public int getPillCount() {
        return pillCount;
    }

    public void setPillCount(int pillCount) {
        this.pillCount = pillCount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getProductNDC() {
        return productNDC;
    }

    public void setProductNDC(String productNDC) {
        this.productNDC = productNDC;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}
