package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String salt;
}

