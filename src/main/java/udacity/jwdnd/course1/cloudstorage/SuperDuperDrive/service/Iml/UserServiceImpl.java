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
    UserMapper userMapper;
    @Autowired
    HashService hashService;

    @Override
    public int RegisterUser(User user){
        String encodeSalt = generateRandomSalt();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodeSalt);
        return userMapper.insert(new User(null, user.getUsername(), hashedPassword, user.getFirstName(), user.getLastName(), encodeSalt));
    }

    private String generateRandomSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return new String(Base64.getEncoder().encode(saltBytes));
    }
    @Override
    public User getUser(String username){
        return userMapper.getUser(username);
    }
}

