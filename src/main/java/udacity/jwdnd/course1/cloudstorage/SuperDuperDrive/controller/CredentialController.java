package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Credential;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.AuthenticationService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.CredentialServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.FileServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.NoteServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    @Autowired
    CredentialServiceImpl credentialservice;

    @Autowired
    AuthenticationService authenticationservice;

    @Autowired
    NoteServiceImpl noteservice;

    @Autowired
    FileServiceImpl fileservice;

    @PostMapping("/add")
    public String addAndUpdateCredential(@ModelAttribute("credential") Credential credential, ModelMap model) {
        credential.setUserId(authenticationservice.getUserId());
        if (credential.getCredentialId() == null || credential.getCredentialId().toString().equals("")) {
            if (credentialservice.insertCredential(credential) == 1) {
                model.addAttribute("message", "Insert credential success!");
            } else {
                model.addAttribute("error", "Insert credential fail!");
            }
        } else {
            if (credentialservice.updateCredential(credential) == 1) {
                model.addAttribute("message", "Update credential success!");
            } else {
                model.addAttribute("error", "Update credential fail!");
            }
        }
        loadAllInformation(model, authenticationservice.getUserId());
        return "home";
    }


    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable int credentialId, ModelMap model) {
        if (credentialservice.deleteCredential(credentialId) == 1) {
            model.addAttribute("message", "Delete credential success!!");
        } else {
            model.addAttribute("error", "Delete credential fail!!");
        }
        loadAllInformation(model, authenticationservice.getUserId());
        return "home";
    }


    void loadAllInformation(ModelMap model, int userId) {
        model.addAttribute("fileList", fileservice.getListFileByUserId(userId));
        model.addAttribute("listNote", noteservice.getListNoteByUserId(userId));
        model.addAttribute("note", new Note());
        List<Credential> listCredentials = credentialservice.getCredentialsListByUserId(userId);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        for (Credential credential : listCredentials) {
            String decryptedPassword = passwordEncoder.encode(credential.getPassword() + credential.getKey());
            credential.setPasswordUnCredential(decryptedPassword);
        }
        model.addAttribute("listCredential", listCredentials);
        model.addAttribute("credential", new Credential());
    }

}
