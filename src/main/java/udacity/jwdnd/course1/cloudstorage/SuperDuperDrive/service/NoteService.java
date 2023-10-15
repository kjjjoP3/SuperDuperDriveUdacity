package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.NoteMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    NoteMapper notemapper;

    public int createNote(Note note) {
        return notemapper.insertNote(note);
    }

    public int updateNote(Note note) {
        return notemapper.updateNote(note);
    }

    public int deleteNote(Integer noteId) {
        return notemapper.deleteNote(noteId);
    }

    public List<Note> getListNoteByUserId(Integer userId) {
        return notemapper.getListNoteByUserId(userId);
    }

    public Note getNoteById(Integer noteId) {
        return notemapper.getNoteById(noteId);
    }
}
