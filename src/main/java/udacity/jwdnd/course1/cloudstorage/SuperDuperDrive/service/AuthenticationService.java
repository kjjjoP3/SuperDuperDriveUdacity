package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.UserMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {
    @Autowired
    UserMapper usermapper;
    @Autowired
    HashService hashservice;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = usermapper.getUser(username);
        if (user != null){
            String encodeSalt = user.getSalt();
            String hashedPassword = hashservice.getHashedValue(password,encodeSalt);
            if (user.getPassword().equals(hashedPassword)) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public Integer getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = usermapper.getUser(authentication.getName());
        return user.getUserId();
    }
}