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

    public NoteServiceImpl(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @Override
    public void saveNoteToDo(Note note) {
         noteMapper.insertNote(note);
    }
    public void updateNoteToDo(Note note) {
         noteMapper.updateNote(note);
    }
    @Override
    public void deleteNoteToDo(Integer noteId) {
         noteMapper.deleteNote(noteId);
    }

    @Override
    public List<Note> getListNoteToDoByUserId(Integer userId) {
        return noteMapper.getListNoteByUserId(userId);
    }

    @Override
    public Note getNoteToDoById(Integer noteId) {
        return noteMapper.getNoteById(noteId);
    }
}
