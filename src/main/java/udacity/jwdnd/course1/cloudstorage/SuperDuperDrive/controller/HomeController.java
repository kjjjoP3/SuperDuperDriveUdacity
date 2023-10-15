package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Credential;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.File;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.AuthenticationService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.CredentialServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.FileServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.NoteServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    NoteServiceImpl noteService;

    @Autowired
    CredentialServiceImpl credentialService;

    @GetMapping(value = {"/home", "/"})
    public String home(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        int userId = authenticationService.getUserId();

        List<File> listFile = fileService.getListFileByUserId(userId);
        List<Note> listNote = noteService.getListNoteByUserId(userId);
        List<Credential> listCredential = credentialService.getCredentialsListByUserId(userId);

        model.addAttribute("fileList", listFile);
        model.addAttribute("listNote", listNote);
        model.addAttribute("note", new Note());
        model.addAttribute("listCredential", listCredential);
        model.addAttribute("credential", new Credential());

        return "home";
    }

}
