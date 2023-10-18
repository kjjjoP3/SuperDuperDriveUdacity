package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.config.FileUploadProperties;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Credential;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.File;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.AuthenticationService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.CredentialService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.CredentialServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.FileServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.NoteServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.NoteService;

import java.util.List;

@Controller
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    NoteService noteService;

    @Autowired
    CredentialService credentialService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private FileUploadProperties fileUploadProperties;

    @PostMapping("/file/add")
    public String addMultiFile(@RequestParam("fileUpload") MultipartFile file, ModelMap model) {
        int userId = authenticationService.getUserId();
        long maxFileSizeInBytes = parseFileSize(fileUploadProperties.getMaxFileSize());
        try {
            long maxFileSize = 10 * 1024 * 1024; // 10MB - Giới hạn kích thước tệp tin

            if (fileService.checkExistFileName(file.getOriginalFilename(), userId)) {
                model.addAttribute("error", "File name is already exists.");
            } else if (file.isEmpty()) {
                model.addAttribute("error", "Please select a file to upload.");
            } else if (file.getSize() > maxFileSizeInBytes) {
                model.addAttribute("error", "File size exceeds the limit. Maximum file size is 10MB.");
            } else if (fileService.insertMultiFile(file, userId) != 1) {
                model.addAttribute("error", "Upload file failed.");
            } else {
                model.addAttribute("message", "Upload file success!");
            }

            loadAllFile(model, userId);
        } catch (Exception ex) {
            model.addAttribute("error", "Upload file failed.");
            loadAllFile(model, userId);
        }
        loadAllFile(model, userId);
        return "home";
    }


    private long parseFileSize(String fileSize) {
        String numericValue = fileSize.replaceAll("[^\\d.]", "");
        if (fileSize.endsWith("MB")) {
            return (long) (Double.parseDouble(numericValue) * 1024 * 1024);
        } else if (fileSize.endsWith("KB")) {
            return (long) (Double.parseDouble(numericValue) * 1024);
        }
        return Long.parseLong(numericValue);
    }


    @GetMapping("/file/delete/{fileId}")
    public String deleteMultiFile(@PathVariable int fileId, ModelMap model) {
        int result = fileService.deleteFile(fileId);

        if (result > 0) {
            model.addAttribute("message", "Delete file success!");
        } else {
            model.addAttribute("error", "Delete file fail!");
        }

        loadAllFile(model, authenticationService.getUserId());
        return "home";
    }

    @GetMapping("/file/view/{fileId}")
    public ResponseEntity<byte[]> viewMultiFile(@PathVariable int fileId) {
        File file = fileService.getFileById(fileId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        headers.setContentDispositionFormData("attachment", file.getFileName());
        headers.setContentLength(file.getFileData().length);
        return new ResponseEntity<>(file.getFileData(), headers, HttpStatus.OK);
    }

    public void loadAllFile(ModelMap model, int userId) {
        List<File> fileList = fileService.getListFileByUserId(userId);
        List<Note> noteList = noteService.getListNoteByUserId(userId);
        Credential credential = new Credential();
        model.addAttribute("fileList", fileList);
        model.addAttribute("listNote", noteList);
        model.addAttribute("note", new Note());
        model.addAttribute("listCredential", credentialService.getCredentialsListByUserId(userId));
        model.addAttribute("credential", credential);
    }
}

