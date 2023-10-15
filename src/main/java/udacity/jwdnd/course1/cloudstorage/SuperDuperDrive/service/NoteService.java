package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service;

import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;

import java.util.List;

public interface NoteService {
    void saveNoteToDo(Note note);
    void updateNoteToDo(Note note);
    void deleteNoteToDo(Integer noteId);
    List<Note> getListNoteToDoByUserId(Integer userId);

    Note getNoteToDoById(Integer noteId);
}
