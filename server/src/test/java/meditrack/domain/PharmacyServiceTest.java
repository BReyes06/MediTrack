package meditrack.domain;

import meditrack.data.PharmacyRepository;
import meditrack.models.Pharmacy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PharmacyServiceTest {

    @Autowired
    PharmacyService service;

    @MockBean
    PharmacyRepository repository;

    @Test
    void shouldFindById() {
        Pharmacy pharmacy = makePharmacy();

        when(repository.findById(1)).thenReturn(pharmacy);
        Pharmacy actual = service.findById(1);

        assertNotNull(actual);
        assertEquals(pharmacy, actual);
    }

    @Test
    void shouldFindAllByUserId() {
        List<Pharmacy> pharmacyList = new ArrayList<>();
        pharmacyList.add(makePharmacy());
        pharmacyList.add(makePharmacy());

        when(repository.findAllByAppUserId(1)).thenReturn(pharmacyList);

        List<Pharmacy> actual = service.findAllByAppUserId(1);

        assertEquals(2, pharmacyList.size());
    }

    @Test
    void shouldAdd() {
        Pharmacy pharmacy = makePharmacy();

        when(repository.add(pharmacy, 1)).thenReturn(pharmacy);
        Result<Pharmacy> actual = service.add(pharmacy, 1);

        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldNotAddNull() {
        Result<Pharmacy> actual = service.add(null, 1);

        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Please enter valid information for pharmacy", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddInvalidPharmacyId() {
        Pharmacy pharmacy = makePharmacy();
        pharmacy.setPharmacyId(1);

        Result<Pharmacy> actual = service.add(pharmacy, 1);


        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Pharmacy may not have an Id.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutPrescriptionId() {
        Pharmacy pharmacy = makePharmacy();

        Result<Pharmacy> actual = service.add(pharmacy, 0);


        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Prescription Id is required", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddNullName() {
        Pharmacy pharmacy = makePharmacy();
        pharmacy.setName(null);

        Result<Pharmacy> actual = service.add(pharmacy, 0);


        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Pharmacy must have a name", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddNullPhone() {
        Pharmacy pharmacy = makePharmacy();
        pharmacy.setPhone(null);

        Result<Pharmacy> actual = service.add(pharmacy, 0);


        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Pharmacy must have a phone number", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddNullAAddress() {
        Pharmacy pharmacy = makePharmacy();
        pharmacy.setAddress(null);

        Result<Pharmacy> actual = service.add(pharmacy, 0);


        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Pharmacy must have an address", actual.getMessages().get(0));
    }

    @Test
    void shouldUpdate() {
        Pharmacy pharmacy = makePharmacy();
        pharmacy.setPharmacyId(1);

        when(repository.update(pharmacy)).thenReturn(true);

        Result<Pharmacy> result = service.update(pharmacy);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotUpdateNullPharmacy() {
        Result<Pharmacy> result = service.update(null);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Please enter valid information for pharmacy", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNoPharmacyId() {
        Pharmacy pharmacy = makePharmacy();
        pharmacy.setPharmacyId(0);

        Result<Pharmacy> result = service.update(pharmacy);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Pharmacy must have and Id", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNonExistentPharmacyId() {
        Pharmacy pharmacy = makePharmacy();
        pharmacy.setPharmacyId(99);

        when(repository.update(pharmacy)).thenReturn(false);
        Result<Pharmacy> result = service.update(pharmacy);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("Pharmacy 99 was not found", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateInvalidName() {
        Pharmacy pharmacy = makePharmacy();
        pharmacy.setName(null);

        Result<Pharmacy> result = service.update(pharmacy);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Pharmacy must have a name", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateInvalidPhone() {
        Pharmacy pharmacy = makePharmacy();
        pharmacy.setPhone(null);

        Result<Pharmacy> result = service.update(pharmacy);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Pharmacy must have a phone number", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateInvalidAddress() {
        Pharmacy pharmacy = makePharmacy();
        pharmacy.setAddress(null);

        Result<Pharmacy> result = service.update(pharmacy);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Pharmacy must have an address", result.getMessages().get(0));
    }

    @Test
    void shouldUpdateNullEmail() {
        Pharmacy pharmacy = makePharmacy();
        pharmacy.setPharmacyId(1);
        pharmacy.setEmail(null);

        when(repository.update(pharmacy)).thenReturn(true);
        Result<Pharmacy> result = service.update(pharmacy);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldDeleteById() {
        when(repository.deleteById(1)).thenReturn(true);
        Result<Pharmacy> result = service.deleteById(1);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDeleteNoId() {
        Result<Pharmacy> result = service.deleteById(0);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Pharmacy must have a valid Id", result.getMessages().get(0));
    }

    @Test
    void shouldNotDeleteNonExistentId() {
        when(repository.deleteById(99)).thenReturn(false);
        Result<Pharmacy> result = service.deleteById(99);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertEquals("Pharmacy 99 was not found", result.getMessages().get(0));
    }


    private Pharmacy makePharmacy() {
        Pharmacy pharmacy = new Pharmacy();

        pharmacy.setName("CVS");
        pharmacy.setEmail("test@cvs.com");
        pharmacy.setAddress("90 Corner");
        pharmacy.setPhone("1-800-callcvs");

        return pharmacy;
    }

}