package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper;

import org.apache.ibatis.annotations.*;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * From NOTES Where userId = #{userId}")
    List<Note> getListNoteByUserId(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNoteById(Integer noteId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userId) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteId = #{noteId}")
    int updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    int deleteNote(Integer noteId);

}