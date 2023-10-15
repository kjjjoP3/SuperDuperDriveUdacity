package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.UserMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.UserDetailService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.NoteService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UserMapper userRepository;

    @Autowired
    NoteService noteService;

    @Autowired
    UserDetailService userDetailService;

    @GetMapping(value = "/")
    public String home(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        int userId = userDetailService.getUserId();

        List<Note> listNote = noteService.getListNoteToDoByUserId(userId);
        model.addAttribute("listNote", listNote);

        model.addAttribute("note", new Note());
        return "home";
    }
}
