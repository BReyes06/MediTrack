package meditrack.security;

import meditrack.data.AppUserRepository;
import meditrack.domain.Result;
import meditrack.domain.ResultType;
import meditrack.models.AppUser;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = repository.findByUsername(username);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return appUser;
    }

    public Result<AppUser> create(String username, String password, String firstName,
                                  String middleName, String lastName, String email, String phone) {
        Result<AppUser> result = validate(username, password, firstName, middleName, lastName, email, phone);

        if (!result.isSuccess()) {
            return result;
        }

        password = encoder.encode(password);

        AppUser appUser = new AppUser(0, firstName, middleName, lastName, email, phone, username, password, true, List.of("USER"));

        try {
            appUser = repository.create(appUser);
            result.setPayload(appUser);
        } catch (DuplicateKeyException e) {
            result.addMessage("Username already exists, please try again", ResultType.INVALID);
        }

        return result;
    }

    private Result<AppUser> validate(String username, String password, String firstName,
                                     String middleName, String lastName, String email, String phone) {
        Result<AppUser> result = new Result<>();
        if (username == null || username.isBlank()) {
            result.addMessage("Username is required", ResultType.INVALID);
            return result;
        }

        if (password == null) {
            result.addMessage("Password is required", ResultType.INVALID);
            return result;
        }

        if (firstName == null) {
            result.addMessage("First name is required", ResultType.INVALID);
            return result;
        }

        if (lastName == null) {
            result.addMessage("Last name is required", ResultType.INVALID);
            return result;
        }

        if (email == null) {
            result.addMessage("An email address is required", ResultType.INVALID);
            return result;
        }

        if (phone == null) {
            result.addMessage("Phone number is required", ResultType.INVALID);
            return result;
        }

        if (!isValidPassword(password)) {
            result.addMessage("Password must contain atleast 8 characters, contain a digit, a letter, and a symbol.", ResultType.INVALID);
        }

        return result;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }
        return digits > 0 && letters > 0 && others > 0;
    }
}
