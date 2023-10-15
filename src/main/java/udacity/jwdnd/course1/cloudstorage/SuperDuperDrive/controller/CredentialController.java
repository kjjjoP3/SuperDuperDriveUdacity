package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Credential;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.AuthenticationService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.EncryptionService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.CredentialServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.FileServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.NoteServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    @Autowired
    CredentialServiceImpl credentialService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    NoteServiceImpl noteService;

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    EncryptionService encryptionService;

    @PostMapping("/add")
    public String addAndUpdateCredential(@ModelAttribute("credential") Credential credential, ModelMap model) {
        credential.setUserId(authenticationService.getUserId());
        if (credential.getCredentialId() == null || credential.getCredentialId().toString().equals("")) {
            if (credentialService.insertCredential(credential) == 1) {
                model.addAttribute("message", "Insert credential success!");
            } else {
                model.addAttribute("error", "Insert credential fail!");
            }
        } else {
            if (credentialService.updateCredential(credential) == 1) {
                model.addAttribute("message", "Update credential success!");
            } else {
                model.addAttribute("error", "Update credential fail!");
            }
        }
        loadAllInformation(model, authenticationService.getUserId());
        return "home";
    }


    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable int credentialId, ModelMap model) {
        if (credentialService.deleteCredential(credentialId) == 1) {
            model.addAttribute("message", "Delete credential success!!");
        } else {
            model.addAttribute("error", "Delete credential fail!!");
        }
        loadAllInformation(model, authenticationService.getUserId());
        return "home";
    }


    void loadAllInformation(ModelMap model, int userId) {
        model.addAttribute("fileList", fileService.getListFileByUserId(userId));
        model.addAttribute("listNote", noteService.getListNoteByUserId(userId));
        model.addAttribute("note", new Note());
        List<Credential> listCredentials = credentialService.getCredentialsListByUserId(userId);
        for(Credential x: listCredentials) {
            x.setPasswordUnCredential(encryptionService.decryptValue(x.getPassword(), x.getKey()));
        }
        model.addAttribute("listCredential", listCredentials);
        model.addAttribute("credential", new Credential());
    }

}
