package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service;

import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;

import java.util.List;

public interface NoteService {
    int createNote(Note note);
    int updateNote(Note note);
    int deleteNote(Integer noteId);
    List<Note> getListNoteByUserId(Integer userId);
    Note getNoteById(Integer noteId);
}
