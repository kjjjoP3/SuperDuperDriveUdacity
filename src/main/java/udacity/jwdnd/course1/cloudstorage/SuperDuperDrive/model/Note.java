package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer noteId;

    private String noteTitle;

    private String noteDescription;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
