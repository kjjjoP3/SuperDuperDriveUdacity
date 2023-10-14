package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

}
