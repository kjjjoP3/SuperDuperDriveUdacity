package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.UserMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.Note;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.NoteService;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.UserService;

import java.security.Principal;

@Controller
public class NoteController {

    @Autowired
    NoteService noteService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userRepository;

    @ModelAttribute(value = "user")
    public User user(Model model, Principal principal, User user) {

        if (principal != null) {
            model.addAttribute("user", new User());
            user = userRepository.findByEmail(principal.getName());
            model.addAttribute("user", user);
        }

        return user;
    }

    @PostMapping("/addOrUpdateNote")
    public String addOrUpdateNote(@ModelAttribute Note note, ModelMap model) {
        User currentUser = userService.getCurrentUser();
        int userId = Math.toIntExact(currentUser.getUserId());
        if (note.getNoteId() != null) {
            noteService.saveNoteToDo(note);
            model.addAttribute("message", "Update note success!");
        } else {
            note.setUser(currentUser);
            noteService.updateNoteToDo(note);
            model.addAttribute("message", "Add note success!");
        }
        loadAllNote(model, userId);
        return "redirect:/";
    }

    void loadAllNote(ModelMap model, int userId) {
        model.addAttribute("listNote", noteService.getListNoteToDoByUserId(userId));
        model.addAttribute("note", new Note());
    }

}
