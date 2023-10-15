package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service;

import org.springframework.web.multipart.MultipartFile;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.File;

import java.util.List;

public interface FileService {
    int insertMultiFile(MultipartFile multipartFile, int userId) throws Exception;
    int deleteFile(int fileId);
    List<File> getListFileByUserId(int userId);
    boolean checkExistFileName(String fileName, int userId);
    File getFileById(int fileId);
}
