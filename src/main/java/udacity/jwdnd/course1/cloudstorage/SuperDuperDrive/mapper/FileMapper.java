package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper;

import org.apache.ibatis.annotations.*;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.File;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("Insert into FILES(filename, contenttype, filesize, userid, filedata) Values(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int createFile(File file);

    @Select("SELECT * From FILES WHERE fileName = #{fileName} And userId = #{userId}")
    File checkExistFileName(String fileName, int userId);

    @Select("SELECT * From FILES WHERE userId = #{userId}")
    List<File> getFileListByUserId(int userId);

    @Select("SELECT * From FILES WHERE fileId = #{fileId}")
    File getFileById(int fileId);

    @Delete("DELETE FILES WHERE fileId = #{fileId}")
    int deleteFileById(int fileId);

}
