package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();
}
