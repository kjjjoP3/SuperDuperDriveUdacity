package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service;

import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;

import java.util.List;

public interface UserService {
    void registerUser(User user, String password);

    User getCurrentUser();

    User findByEmail(String email);

    void deleteUserById(Long id);

    List<User> getAllUsers();
}
