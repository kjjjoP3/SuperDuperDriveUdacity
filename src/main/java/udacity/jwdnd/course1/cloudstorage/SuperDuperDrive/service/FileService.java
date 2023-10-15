package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.FileMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.File;

import java.util.List;

@Service
public class FileService {
    @Autowired
    FileMapper filemapper;

//	@Autowired
//  AuthenticationService authenticationservice;

    public int insertMultiFile(MultipartFile multipartFile, int userId) throws Exception{
        File file = new File(multipartFile.getOriginalFilename(), multipartFile.getContentType(), String.valueOf(multipartFile.getSize()), userId, multipartFile.getBytes());
        return filemapper.createFile(file);
    }

    public int deleteFile(int fileId){
        return filemapper.deleteFileById(fileId);
    }

    public List<File> getListFileByUserId(int userId){
        return filemapper.getFileListByUserId(userId);
    }

    public boolean checkExistFileName(String fileName, int userId){
        return filemapper.checkExistFileName(fileName, userId) != null;
    }

    public File getFileById(int fileId){
        return filemapper.getFileById(fileId);
    }
}
