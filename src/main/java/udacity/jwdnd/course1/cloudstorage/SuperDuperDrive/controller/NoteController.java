package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Credential;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.AuthenticationService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.CredentialService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.FileService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.NoteService;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    NoteService noteservice;

    @Autowired
    FileService fileservice;

    @Autowired
    AuthenticationService authenticationservice;

    @Autowired
    CredentialService credentialservice;

    @PostMapping("/addOrUpdate")
    public ModelAndView createNote(@ModelAttribute("note") Note note, ModelMap model) {
        int userId = authenticationservice.getUserId();
        note.setUserId(userId);

        if (note.getNoteId() == null || note.getNoteId().toString().equals("")) {
            if (noteservice.createNote(note) == 1) {
                model.addAttribute("message", "Add note success!");
            } else {
                model.addAttribute("error", "Add note fail!");
            }
        } else {
            if (noteservice.updateNote(note) == 1) {
                model.addAttribute("message", "Update note success!");
            } else {
                model.addAttribute("error", "Update note fail!");
            }
        }
        loadAllNote(model, userId);
        return new ModelAndView("home");
    }

    @GetMapping("/delete/{noteId}")
    public ModelAndView deleteNote(@PathVariable int noteId, ModelMap model) {
        int userId = authenticationservice.getUserId();
        if(noteservice.deleteNote(noteId)==1) {
            model.addAttribute("message", "Delete note success!");
        } else {
            model.addAttribute("error", "Delete note fail!");
        }

        loadAllNote(model, userId);
        return new ModelAndView("home");
    }

    void loadAllNote(ModelMap model, int userId) {
        model.addAttribute("fileList", fileservice.getListFileByUserId(userId));
        model.addAttribute("listNote", noteservice.getListNoteByUserId(userId));
        model.addAttribute("note", new Note());
        model.addAttribute("listCredential", credentialservice.getCredentialsListByUserId(userId));
        model.addAttribute("credential", new Credential());
    }
}