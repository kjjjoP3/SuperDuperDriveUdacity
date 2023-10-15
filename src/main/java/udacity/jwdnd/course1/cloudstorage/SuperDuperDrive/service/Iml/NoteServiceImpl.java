package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.NoteMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.NoteService;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    NoteMapper noteMapper;
    @Override
    public int createNote(Note note) {
        return noteMapper.insertNote(note);
    }
    @Override
    public int updateNote(Note note) {
        return noteMapper.updateNote(note);
    }
    @Override
    public int deleteNote(Integer noteId) {
        return noteMapper.deleteNote(noteId);
    }
    @Override
    public List<Note> getListNoteByUserId(Integer userId) {
        return noteMapper.getListNoteByUserId(userId);
    }
    @Override
    public Note getNoteById(Integer noteId) {
        return noteMapper.getNoteById(noteId);
    }
}
