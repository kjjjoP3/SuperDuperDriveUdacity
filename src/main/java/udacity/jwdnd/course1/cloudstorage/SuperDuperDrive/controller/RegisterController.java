package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.mapper.UserMapper;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.UserService;

import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    UserMapper userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserService userService;

    @GetMapping("/signup")
    public ModelAndView registerForm(ModelMap model) {
        model.addAttribute("user", new User());
        return new ModelAndView("signup", model);
    }

    @PostMapping("/signup")
    public String register(ModelMap model, @Validated @ModelAttribute("user") User dto, BindingResult result,
                           RedirectAttributes redirAtt,
                           @RequestParam("password") String password) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Example Signup Error Message");
            return "signup";
        }
        if (!checkEmail(dto.getEmail())) {
            model.addAttribute("error", "E-mail is being used!");
            return "signup";
        } else {
            redirAtt.addFlashAttribute("message", "You successfully signed up! Please continue to the login page.");
            userService.registerUser(dto, password);
        }

        return "redirect:/signup";
    }

    public boolean checkEmail(String email) {
        List<User> list = userRepository.findAll();
        for (User c : list) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }

}
