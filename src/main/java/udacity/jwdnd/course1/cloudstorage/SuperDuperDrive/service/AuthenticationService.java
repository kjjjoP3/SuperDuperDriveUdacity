package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.UserMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userMapper.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public Integer getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userMapper.getUser(authentication.getName());
        return user.getUserId();
    }
}