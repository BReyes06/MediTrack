package meditrack.data;

import meditrack.models.AppUser;

public interface AppUserRepository {
    public AppUser findByUsername(String username);

    public AppUser create(AppUser user);

    public boolean update(AppUser user);

}
