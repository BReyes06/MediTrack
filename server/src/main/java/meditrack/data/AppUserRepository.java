package meditrack.data;

import meditrack.models.AppUser;

public interface AppUserRepository {
    public AppUser findByUsername(String username);

    public AppUser create(AppUser user);

    public void update(AppUser user);

}
