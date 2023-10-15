package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper;

import org.apache.ibatis.annotations.*;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("Select * From NOTES Where userId = #{userId}")
    List<Note> getListNoteByUserId(Integer userId);

    @Select("Select * From NOTES Where noteId = #{noteId}")
    Note getNoteById(Integer noteId);

    @Insert("Insert into NOTES (notetitle, notedescription, userId) Values(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Update("Update NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} Where noteId = #{noteId}")
    int updateNote(Note note);

    @Delete("Delete From NOTES Where noteId = #{noteId}")
    int deleteNote(Integer noteId);

}