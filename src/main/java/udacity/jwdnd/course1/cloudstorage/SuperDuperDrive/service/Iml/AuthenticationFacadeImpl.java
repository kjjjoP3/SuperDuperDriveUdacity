package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.AuthenticationFacade;

@Service
public class AuthenticationFacadeImpl implements AuthenticationFacade {
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
