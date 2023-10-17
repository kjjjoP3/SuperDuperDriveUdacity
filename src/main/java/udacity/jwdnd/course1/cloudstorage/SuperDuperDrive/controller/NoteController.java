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
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.CredentialServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.FileServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.NoteServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.NoteService;

import java.security.Principal;

@Controller
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    FileService fileService;

    @Autowired
    CredentialService credentialService;

    @Autowired
    AuthenticationService authenticationservice;

    @PostMapping("/note/addOrUpdate")
    public String createOrUpdateNote(@ModelAttribute Note note, ModelMap model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        int userId = authenticationservice.getUserId();
        note.setUserId(userId);

        if (note.getNoteId() == null) {
            if (noteService.createNote(note) == 1) {
                model.addAttribute("message", "Add note success!");
            } else {
                model.addAttribute("error", "Add note fail!");
            }
        } else {
            if (noteService.updateNote(note) == 1) {
                model.addAttribute("message", "Update note success!");
            } else {
                model.addAttribute("error", "Update note fail!");
            }
        }
        loadAllInformation(model, principal);
        return "home";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(@PathVariable int noteId, ModelMap model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        if (noteService.deleteNote(noteId) == 1) {
            model.addAttribute("message", "Delete note success!");
        } else {
            model.addAttribute("error", "Delete note fail!");
        }
        loadAllInformation(model, principal);
        return "home";
    }

    void loadAllInformation(ModelMap model, Principal principal) {
        if (principal != null) {
            int userId = authenticationservice.getUserId();
            model.addAttribute("fileList", fileService.getListFileByUserId(userId));
            model.addAttribute("listNote", noteService.getListNoteByUserId(userId));
            model.addAttribute("note", new Note());
            model.addAttribute("listCredential", credentialService.getCredentialsListByUserId(userId));
            model.addAttribute("credential", new Credential());
        }
    }
}
