package meditrack.domain;

import meditrack.data.AppUserRepository;
import meditrack.data.DoctorRepository;
import meditrack.data.PharmacyRepository;
import meditrack.data.PrescriptionRepository;
import meditrack.models.AppUser;
import meditrack.models.Doctor;
import meditrack.models.Pharmacy;
import meditrack.models.Prescription;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppUserRepository appUserRepository;
    private final DoctorRepository doctorRepository;
    private final PharmacyRepository pharmacyRepository;

    public PrescriptionService(PrescriptionRepository repository, AppUserRepository appUserRepository, DoctorRepository doctorRepository, PharmacyRepository pharmacyRepository) {
        this.prescriptionRepository = repository;
        this.appUserRepository = appUserRepository;
        this.doctorRepository = doctorRepository;
        this.pharmacyRepository = pharmacyRepository;
    }

    public List<Prescription> findAllByUsername(String username) {
        AppUser appUser = appUserRepository.findByUsername(username);
        List<Prescription> prescriptions = prescriptionRepository.findAllById(appUser.getAppUserId());

        for (Prescription p : prescriptions) {
            Doctor doctor = doctorRepository.findById(p.getDoctor().getDoctorId());
            p.setDoctor(doctor);

            Pharmacy pharmacy = pharmacyRepository.findById(p.getPharmacy().getPharmacyId());
            p.setPharmacy(pharmacy);
        }
        return prescriptions;
    }


    public Result<Prescription> add(Prescription prescription) {
        return null;
    }
}
