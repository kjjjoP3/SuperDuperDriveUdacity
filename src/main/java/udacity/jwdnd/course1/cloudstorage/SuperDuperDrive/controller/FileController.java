package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Credential;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.File;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.AuthenticationService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.CredentialServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.FileServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.NoteServiceImpl;

import java.util.List;

@Controller
public class FileController {
    @Autowired
    FileServiceImpl fileservice;

    @Autowired
    AuthenticationService authenticationservice;

    @Autowired
    NoteServiceImpl noteservice;

    @Autowired
    CredentialServiceImpl credentialservice;

    @PostMapping("/file/add")
    public String addMultiFile(@RequestParam("fileUpload") MultipartFile file, ModelMap model) throws Exception {
        int userId = authenticationservice.getUserId();

        if (fileservice.checkExistFileName(file.getOriginalFilename(), userId)) {
            model.addAttribute("error", "File name is already exists!");
        } else {
            int result = fileservice.insertMultiFile(file, userId);
            if (result == 1) {
                model.addAttribute("message", "Upload file success!");
            } else {
                model.addAttribute("error", "Upload file fail!");
            }
        }

        loadAllFile(model, userId);
        return "home";
    }

    @GetMapping("/file/delete/{fileId}")
    public String deleteMultiFile(@PathVariable String fileId, ModelMap model) {
        int fileIdInt = Integer.parseInt(fileId);
        int result = fileservice.deleteFile(fileIdInt);

        if (result > 0) {
            model.addAttribute("message", "Delete file success!");
        } else {
            model.addAttribute("error", "Delete file fail!");
        }

        loadAllFile(model, authenticationservice.getUserId());
        return "home";
    }

    @GetMapping("/file/view/{fileId}")
    public ResponseEntity<byte[]> viewMultiFile(@PathVariable int fileId) {
        File file = fileservice.getFileById(fileId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        headers.setContentDispositionFormData("attachment", file.getFileName());
        headers.setContentLength(file.getFileData().length);
        return new ResponseEntity<>(file.getFileData(), headers, HttpStatus.OK);
    }

    public void loadAllFile(ModelMap model, int userId) {
        List<File> fileList = fileservice.getListFileByUserId(userId);
        List<Note> noteList = noteservice.getListNoteByUserId(userId);
        Credential credential = new Credential();
        model.addAttribute("fileList", fileList);
        model.addAttribute("listNote", noteList);
        model.addAttribute("note", new Note());
        model.addAttribute("listCredential", credentialservice.getCredentialsListByUserId(userId));
        model.addAttribute("credential", credential);
    }
}
