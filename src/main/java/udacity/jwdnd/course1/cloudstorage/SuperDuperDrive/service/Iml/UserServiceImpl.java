package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Role;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.repository.RoleRepository;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.repository.UserRepository;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.AuthenticationFacade;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.UserService;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void registerUser(User dto, String password) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(password));

        Role role = roleRepository.findByName("ROLE_USER"); // Tìm Role với name = "ROLE_USER"
        if (role == null) {
            role = new Role();
            role.setName("ROLE_USER");
        }

        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public <S extends User> S saveUser(S user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
