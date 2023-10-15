package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper;

import org.apache.ibatis.annotations.*;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Credential;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredentialById(int credentialId);

    @Select("SELECT * From CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getCredentialsListByUserId(int userId);

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid) Values(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialId = #{credentialId}")
    int updateCredential(Credential credential);

    @Delete("DELETE CREDENTIALS WHERE credentialid = #{credentialId}")
    int deleteCredentialById(int credentialId);

}
