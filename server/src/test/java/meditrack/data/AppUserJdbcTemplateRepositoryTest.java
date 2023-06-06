package meditrack.data;

import meditrack.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 3;

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
    void shouldAdd() {
        AppUser user = makeUser();
        AppUser actual = repository.create(user);

        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getAppUserId());
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