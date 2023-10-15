package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper;

import org.apache.ibatis.annotations.*;

import org.springframework.data.repository.query.Param;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE email = #{email}")
    User findByEmail(String email);

    @Delete("DELETE FROM user WHERE userId = #{id}")
    void deleteById(@Param("id") Long id);

    @Insert("INSERT INTO user (first_name, last_name, email, password) " +
            "VALUES (#{firstName}, #{lastName}, #{email}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void save(User user);

    @Select("SELECT * FROM user")
    List<User> findAll();

}

