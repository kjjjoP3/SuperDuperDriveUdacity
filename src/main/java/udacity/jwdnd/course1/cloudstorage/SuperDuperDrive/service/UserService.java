package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service;

import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;

import java.util.List;

public interface UserService {
    void registerUser(User user, String password);
    <S extends User> S saveUser(S user);

    User findByEmail(String email);

    void deleteUserById(Long id);

    List<User> getAllUsers();
}
