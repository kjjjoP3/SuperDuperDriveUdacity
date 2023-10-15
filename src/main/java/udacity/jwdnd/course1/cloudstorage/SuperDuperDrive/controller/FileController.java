package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.CredentialService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.FileService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.NoteService;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileService fileservice;

    @Autowired
    AuthenticationService authenticationservice;

    @Autowired
    NoteService noteservice;

    @Autowired
    CredentialService credentialservice;

    @PostMapping("/add")
    public ModelAndView addMultiFile(@RequestParam("fileUpload") MultipartFile file, ModelMap model) throws Exception {
        int userId = authenticationservice.getUserId();

        if (fileservice.checkExistFileName(file.getOriginalFilename(), userId)) {
            model.addAttribute("error", "File name is already exists!");
            loadAllFile(model, userId);
            return new ModelAndView("home");
        }

        if (fileservice.insertMultiFile(file, userId) != 1) {
            model.addAttribute("error", "Upload file fail!");
            loadAllFile(model, userId);
            return new ModelAndView("home");
        } else {
            model.addAttribute("message", "Upload file success!");
            loadAllFile(model, userId);
            return new ModelAndView("home");
        }
    }

    @GetMapping("/delete/{fileId}")
    public ModelAndView deleteMultiFile(@PathVariable String fileId, ModelMap model) {
        if (fileservice.deleteFile(Integer.parseInt(fileId)) > 0) {
            model.addAttribute("message", "Delete file success!");
        } else {
            model.addAttribute("error", "Delete file fail!");
        }
        loadAllFile(model, authenticationservice.getUserId());
        return new ModelAndView("home");
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity<byte[]> viewMultiFile(@PathVariable int fileId) {
        File file = fileservice.getFileById(fileId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
    }

    public void loadAllFile(ModelMap model, int userId) {
        model.addAttribute("fileList", fileservice.getListFileByUserId(userId));
        model.addAttribute("listNote", noteservice.getListNoteByUserId(userId));
        model.addAttribute("note", new Note());
        model.addAttribute("listCredential", credentialservice.getCredentialsListByUserId(userId));
        model.addAttribute("credential", new Credential());
//		return loadAllFile();
    }
}
