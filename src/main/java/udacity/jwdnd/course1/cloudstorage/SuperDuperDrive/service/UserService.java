package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service;

import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;

public interface UserService {
    int RegisterUser(User user);
    User getUser(String username);
}
