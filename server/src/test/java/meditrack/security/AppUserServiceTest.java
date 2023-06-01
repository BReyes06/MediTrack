package meditrack.security;

import meditrack.data.AppUserRepository;
import meditrack.domain.Result;
import meditrack.domain.ResultType;
import meditrack.models.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AppUserServiceTest {

    @Autowired
    AppUserService service;

    @MockBean
    AppUserRepository repository;

    @Test
    void shouldFindJohnny() {
        AppUser johnny = makeUser();

        when(repository.findByUsername("jtest")).thenReturn(johnny);

        AppUser actual = (AppUser) service.loadUserByUsername("jtest");
        assertEquals(johnny, actual);
    }

    @Test
    void shouldAddValidUser() {
        AppUser expected = makeUser();
        expected.setPassword("$2a$10$d.ZgktC8GvqB7jUEQMihjensjX7N0orixYrkHHlv1mhTKPC1R0CCe");
        AppUser appUser = makeUser();
        appUser.setAppUserId(0);

        when(repository.create(appUser)).thenReturn(expected);
        Result<AppUser> result = service.create(appUser);


        assertEquals(ResultType.SUCCESS, result.getType());
        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddWithoutUsername() {
        AppUser user = makeUser();
        user.setUsername(null);

        Result<AppUser> result = service.create(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Username is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutPassword() {
        AppUser user = makeUser();
        user.setPassword(null);

        Result<AppUser> result = service.create(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Password is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenPasswordLessThan8Chars() {
        AppUser user = makeUser();
        user.setPassword("asdf");

        Result<AppUser> result = service.create(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Password must contain atleast 8 characters, contain a digit, a letter, and a symbol.", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenPasswordDoesNotContainLetter() {
        AppUser user = makeUser();
        user.setPassword("12345678");

        Result<AppUser> result = service.create(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Password must contain atleast 8 characters, contain a digit, a letter, and a symbol.", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenPasswordDoesNotContainDigit() {
        AppUser user = makeUser();
        user.setPassword("qwerasdf");

        Result<AppUser> result = service.create(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Password must contain atleast 8 characters, contain a digit, a letter, and a symbol.", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWhenPasswordDoesNotContainSymbol() {
        AppUser user = makeUser();
        user.setPassword("asdf5678");

        Result<AppUser> result = service.create(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Password must contain atleast 8 characters, contain a digit, a letter, and a symbol.", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutFirstName() {
        AppUser user = makeUser();
        user.setFirstName(null);

        Result<AppUser> result = service.create(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("First name is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutLastName() {
        AppUser user = makeUser();
        user.setLastName(null);

        Result<AppUser> result = service.create(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Last name is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutEmail() {
        AppUser user = makeUser();
        user.setEmail(null);

        Result<AppUser> result = service.create(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("An email address is required", result.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutPhone() {
        AppUser user = makeUser();
        user.setPhone(null);

        Result<AppUser> result = service.create(user);

        assertEquals(ResultType.INVALID, result.getType());
        assertEquals("Phone number is required", result.getMessages().get(0));
    }

    private AppUser makeUser() {
        AppUser user = new AppUser();
        user.setAppUserId(1);
        user.setFirstName("Johnny");
        user.setMiddleName("Tester");
        user.setLastName("Test");
        user.setEmail("jt@test.com");
        user.setPhone("1-johnnytest");
        user.setUsername("jtest");
        user.setPassword("P@ssw0rd!");
        user.setAuthorities(Arrays.asList("USER"));
        user.setEnabled(true);

        return user;
    }
}