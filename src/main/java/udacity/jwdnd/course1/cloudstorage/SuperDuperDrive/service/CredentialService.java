package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service;

import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Credential;

import java.util.List;

public interface CredentialService {
    List<Credential> getCredentialsListByUserId(int userId);
    int insertCredential(Credential credential);
    int updateCredential(Credential credential);
    int deleteCredential(int credentialId);
}
