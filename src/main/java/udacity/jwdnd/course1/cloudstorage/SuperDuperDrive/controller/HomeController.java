package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Credential;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.File;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.AuthenticationService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.CredentialService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.FileService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.NoteService;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    FileService fileservice;

    @Autowired
    AuthenticationService authenticationservice;

    @Autowired
    NoteService noteservice;

    @Autowired
    CredentialService credentialservice;

    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
    public ModelAndView home(ModelMap model, HttpSession httpSession, Principal principal) {

        if (principal == null) {
            return new ModelAndView( "redirect:/login");
        }

        int userId = authenticationservice.getUserId();

        List<File> listFile = fileservice.getListFileByUserId(userId);
        model.addAttribute("fileList", listFile);

        List<Note> listNote = noteservice.getListNoteByUserId(userId);
        model.addAttribute("listNote", listNote);
        model.addAttribute("note", new Note());

        List<Credential> listCredential = credentialservice.getCredentialsListByUserId(userId);
        model.addAttribute("listCredential", listCredential);
        model.addAttribute("credential", new Credential());

        return new ModelAndView("home", model);
    }
}
