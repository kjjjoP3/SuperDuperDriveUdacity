package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Credential;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.*;

import java.util.List;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    @Autowired
    CredentialService credentialservice;

    @Autowired
    AuthenticationService authenticationservice;

    @Autowired
    NoteService noteservice;

    @Autowired
    FileService fileservice;

    @Autowired
    EncryptionService encryptionService;

    @PostMapping("/add")
    public ModelAndView addAndUpdateCredential(@ModelAttribute("credential") Credential credential, ModelMap model) {
        credential.setUserId(authenticationservice.getUserId());
        if (credential.getCredentialId() == null || credential.getCredentialId().toString().equals("")) {
            if (credentialservice.insertCredential(credential) == 1) {
                model.addAttribute("message", "Insert credential success!");
            } else {
                model.addAttribute("error", "Insert credential fail!");
            }
        } else {
            if (credentialservice.updateCredential(credential) == 1) {
                model.addAttribute("message", "Insert credential success!");
            } else {
                model.addAttribute("error", "Insert credential fail!");
            }
        }
        loadAllInfomation(model, authenticationservice.getUserId());
        return new ModelAndView("home");
    }

    @GetMapping("/delete/{credentialId}")
    public ModelAndView deleteCredential(@PathVariable int credentialId, ModelMap model) {
        if (credentialservice.deleteCredential(credentialId) == 1) {
            model.addAttribute("message", "Delete credential success!!");
        } else {
            model.addAttribute("error", "Delete credential fail!!");
        }

        loadAllInfomation(model, authenticationservice.getUserId());

        return new ModelAndView("home");
    }

    void loadAllInfomation(ModelMap model, int userId) {
        model.addAttribute("fileList", fileservice.getListFileByUserId(userId));
        model.addAttribute("listNote", noteservice.getListNoteByUserId(userId));
        model.addAttribute("note", new Note());
        List<Credential> listCredentials = credentialservice.getCredentialsListByUserId(userId);
        for(Credential x: listCredentials) {
            x.setPasswordUnCredential(encryptionService.decryptValue(x.getPassword(), x.getKey()));
        }
        model.addAttribute("listCredential", listCredentials);
        model.addAttribute("credential",  new Credential());
    }
}
