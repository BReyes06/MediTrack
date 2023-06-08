package meditrack.security;

import meditrack.data.*;
import meditrack.domain.Result;
import meditrack.domain.ResultType;
import meditrack.models.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repository, PasswordEncoder encoder) {
        this.appUserRepository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return appUser;
    }

    public List<AppUser> findAll() {
        return appUserRepository.findAll();
    }

    public AppUser findById(int userId) {
        return appUserRepository.findById(userId);
    }

    public Result<AppUser> create(AppUser user) {
        Result<AppUser> result = validate(user);

        if (!result.isSuccess()) {
            return result;
        }

        user.setPassword(encoder.encode(user.getPassword()));

        user.setEnabled(true);
        user.setAuthorities(List.of("USER"));


        try {
            user = appUserRepository.create(user);
            result.setPayload(user);
        } catch (DuplicateKeyException e) {
            result.addMessage("Username already exists, please try again", ResultType.INVALID);
        }

        return result;
    }

    public Result<AppUser> update(AppUser user) {
        Result<AppUser> result = validate(user);

        if (!result.isSuccess()) {
            return result;
        }

        if (user.getAppUserId() <= 0) {
            result.addMessage("User ID must be provided", ResultType.INVALID);
            return result;
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setAuthorities(List.of("USER"));

        if (!appUserRepository.update(user)) {
            String msg = String.format("User %s was not found and could not be updated.", user.getAppUserId());
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }

        result.setPayload(user);
        return result;
    }

    public Result<AppUser> deleteById(int appUserId) {
        Result<AppUser> result = new Result<>();
        if (appUserId <= 0) {
            result.addMessage("User must have an Id", ResultType.INVALID);
            return result;
        }

        if (!appUserRepository.deleteById(appUserId)) {
            String msg = String.format("User %s not found", appUserId);
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    private Result<AppUser> validate(AppUser user) {
        Result<AppUser> result = new Result<>();
        if (user == null) {
            result.addMessage("User must contain valid information.", ResultType.INVALID);
            return result;
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            result.addMessage("Username is required", ResultType.INVALID);
        }


        if (user.getFirstName() == null) {
            result.addMessage("First name is required", ResultType.INVALID);
        }

        if (user.getLastName() == null) {
            result.addMessage("Last name is required", ResultType.INVALID);
        }

        if (user.getEmail() == null) {
            result.addMessage("An email address is required", ResultType.INVALID);
        }

        if (user.getPhone() == null) {
            result.addMessage("Phone number is required", ResultType.INVALID);
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            result.addMessage("Password is required", ResultType.INVALID);
            return result;
        }

        if (!isValidPassword(user.getPassword())) {
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
