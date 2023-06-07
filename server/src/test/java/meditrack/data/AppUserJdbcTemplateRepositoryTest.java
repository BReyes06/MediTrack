package meditrack.data;

import meditrack.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserJdbcTemplateRepositoryTest {

    @Autowired
    AppUserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp() {
        knownGoodState.set();
    }



    @Test
    void shouldFindByUsername() {
        AppUser marvis = repository.findByUsername("mchan");

        assertEquals(1, marvis.getAppUserId());
        assertEquals("Marvis", marvis.getFirstName());
        assertEquals("mchan@email.com", marvis.getEmail());
    }

    @Test
    void shouldFindAll() {
        List<AppUser> appUsers = repository.findAll();

        System.out.println(appUsers);

        assertTrue(appUsers.size() >= 2 && appUsers.size() <= 4);
    }

    @Test
    void shouldAdd() {
        AppUser user = makeUser();
        AppUser actual = repository.create(user);

        assertNotNull(actual);
        assertTrue(actual.getAppUserId() == 3 || actual.getAppUserId() == 4);
    }

    @Test
    void shouldUpdate() {
        AppUser user = makeUser();
        user.setAppUserId(2);
        user.setFirstName("Dukey");
        user.setEmail("dt@test.com");

        assertTrue(repository.update(user));

        AppUser result = repository.findByUsername("breyes");

        assertEquals(2, result.getAppUserId());
        assertEquals("Dukey", result.getFirstName());
        assertEquals("dt@test.com", result.getEmail());
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
    }

    private AppUser makeUser() {
        AppUser user = new AppUser();
        user.setFirstName("Johnny");
        user.setMiddleName("Tester");
        user.setLastName("Test");
        user.setEmail("jt@test.com");
        user.setPhone("1-johnnytest");
        user.setUsername("jtest");
        user.setPassword("asdlkfja");
        user.setAuthorities(Arrays.asList("USER"));

        return user;
    }

}