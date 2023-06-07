package meditrack.data;

import meditrack.models.AppUser;

import java.util.List;

public interface AppUserRepository {
    public AppUser findByUsername(String username);

    public AppUser findById(int appUserId);

    List<AppUser> findAll();

    public AppUser create(AppUser user);

    public boolean update(AppUser user);

    public boolean deleteById(int appUserId);
}
