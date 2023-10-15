package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.UserMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.UserService;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public int RegisterUser(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String salt = generateRandomSalt();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        return userMapper.insert(new User(null, user.getUsername(), hashedPassword, user.getFirstName(), user.getLastName(), salt));
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

