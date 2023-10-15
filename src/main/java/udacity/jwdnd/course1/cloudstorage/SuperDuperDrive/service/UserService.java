package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.UserMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    @Autowired
    HashService hashservice;
    @Autowired
    UserMapper usermapper;

    //	@Autowired
//    AuthenticationService authenticationservice;
    SecureRandom ramdom = new SecureRandom();

    public int RegisterUser(User user){
        byte[] salt = new byte[16];
        ramdom.nextBytes(salt);
        String encodeSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashservice.getHashedValue(user.getPassword(), encodeSalt);
        return usermapper.insert(new User(null, user.getUsername(), hashedPassword, user.getFirstName(), user.getLastName(), encodeSalt));
    }

    public User getUser(String username){
        return usermapper.getUser(username);
    }
}

