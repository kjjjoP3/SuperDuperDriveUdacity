package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.CredentialMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Credential;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.CredentialService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.EncryptionService;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialServiceImpl implements CredentialService {
    @Autowired
    CredentialMapper credentialMapper;

    @Autowired
    EncryptionService encryptionService;

    @Override
    public List<Credential> getCredentialsListByUserId(int userId) {
        return credentialMapper.getCredentialsListByUserId(userId);
    }

    @Override
    public int insertCredential(Credential credential) {
        setEncryptedValues(credential);
        return credentialMapper.addCredential(credential);
    }

    @Override
    public int updateCredential(Credential credential) {
        setEncryptedValues(credential);
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

    private void setEncryptedValues(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodeSalt = Base64.getEncoder().encodeToString(salt);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodeSalt);
        credential.setKey(encodeSalt);
        credential.setPassword(encryptedPassword);
    }
}
