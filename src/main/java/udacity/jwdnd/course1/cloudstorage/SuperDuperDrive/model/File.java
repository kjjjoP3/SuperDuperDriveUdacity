package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private int fileId;
    private String fileName;
    private String contentType;
    private String fileSize;
    private int userId;
    private byte[] fileData;

    public File(String fileName, String contentType, String fileSize, int userId, byte[] fileData) {
        super();
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }

}

