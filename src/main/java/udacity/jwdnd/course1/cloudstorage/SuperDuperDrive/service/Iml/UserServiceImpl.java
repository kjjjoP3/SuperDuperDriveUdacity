package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.UserMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.HashService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.UserService;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HashService hashService;

    @Override
    public boolean registerUser(User user) {
        if (getUser(user.getUsername()) != null) {
            return false;
        }

        String encodedSalt = generateRandomSalt();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        int rowsInserted = userMapper.save(new User(null, user.getUsername(), hashedPassword, user.getFirstName(), user.getLastName(), encodedSalt));

        return rowsInserted > 0;
    }

    @Override
    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    private String generateRandomSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return new String(Base64.getEncoder().encode(saltBytes));
    }
}


