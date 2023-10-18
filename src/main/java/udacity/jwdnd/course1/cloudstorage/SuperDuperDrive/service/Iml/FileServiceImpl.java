package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.FileMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.File;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.FileService;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    FileMapper fileMapper;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    @Override
    public int insertMultiFile(MultipartFile multipartFile, int userId) {
        long fileSize = multipartFile.getSize();
        if (fileSize > MAX_FILE_SIZE) {
            return 0;
        }

        try {
            File file = new File(
                    multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),
                    String.valueOf(fileSize),
                    userId,
                    multipartFile.getBytes()
            );

            int result = fileMapper.addFile(file);
            return result;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int deleteFile(int fileId) {
        return fileMapper.deleteFileById(fileId);
    }

    @Override
    public List<File> getListFileByUserId(int userId) {
        return fileMapper.getFileListByUserId(userId);
    }

    @Override
    public boolean checkExistFileName(String fileName, int userId) {
        return fileMapper.checkExistFileName(fileName, userId) != null;
    }

    @Override
    public File getFileById(int fileId) {
        return fileMapper.getFileById(fileId);
    }
}

