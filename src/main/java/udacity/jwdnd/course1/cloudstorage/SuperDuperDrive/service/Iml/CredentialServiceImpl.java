package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.CredentialMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Credential;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.CredentialService;

import java.util.List;

@Service
public class CredentialServiceImpl implements CredentialService {
    @Autowired
    CredentialMapper credentialMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public List<Credential> getCredentialsListByUserId(int userId) {
        return credentialMapper.getCredentialsListByUserId(userId);
    }
    @Override
    public int insertCredential(Credential credential) {
        credential = setValueCredential(credential);
        return credentialMapper.insertCredential(credential);
    }
    @Override
    public int updateCredential(Credential credential) {
        credential = setValueCredential(credential);
        return credentialMapper.updateCredential(credential);
    }
    @Override
    public int deleteCredential(int credentialId) {
        return credentialMapper.deleteCredentialById(credentialId);
    }
    @Override
    public Credential getCredentialById(int credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }

    public Credential setValueCredential(Credential cre) {
        String salt = BCrypt.gensalt(16);
        String encryptedPassword = passwordEncoder.encode(cre.getPassword() + salt);
        cre.setKey(salt);
        cre.setPassword(encryptedPassword);
        return cre;
    }

}