package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    @Modifying
    @Query("DELETE FROM User u WHERE u.userId = :id")
    void deleteById(@Param("id") Long id);


}
