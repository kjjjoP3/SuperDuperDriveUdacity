package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.CredentialMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Credential;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    @Autowired
    CredentialMapper credentialmapper;

    @Autowired
    HashService hashservice;

    @Autowired
    EncryptionService encryptionservice;

//	@Autowired
//  AuthenticationService authenticationservice;

    public List<Credential> getCredentialsListByUserId(int userId) {
        return credentialmapper.getCredentialsListByUserId(userId);
    }

    public int insertCredential(Credential credential) {
        credential = setValueCredential(credential);
        return credentialmapper.insertCredential(credential);
    }

    public int updateCredential(Credential credential) {
        credential = setValueCredential(credential);
        return credentialmapper.updateCredential(credential);
    }

    public int deleteCredential(int credentialId) {
        return credentialmapper.deleteCredentialById(credentialId);
    }

    public Credential getCredentialById(int credentialId) {
        return credentialmapper.getCredentialById(credentialId);
    }

    Credential setValueCredential(Credential cre) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodeSalt = Base64.getEncoder().encodeToString(salt);
        String encryptPassword = encryptionservice.encryptValue(cre.getPassword(), encodeSalt);
        cre.setKey(encodeSalt);
        cre.setPassword(encryptPassword);
        return cre;
    }

}