package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.Iml.UserServiceImpl;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.UserService;

import java.security.Principal;

@Controller
public class SignUpController {

    @Autowired
    UserService userService;

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("error", false);
        model.addAttribute("done", false);
        return "signup";
    }

    @PostMapping("/signup")
    public String register(@ModelAttribute User user, Model model, Principal principal) {
        if (principal != null) {
            // Người dùng đã đăng nhập, chuyển họ đến trang chính.
            return "redirect:/home";
        }

        boolean registrationSuccessful = userService.registerUser(user);

        if (registrationSuccessful) {
            // Đăng ký thành công, chuyển họ đến trang đăng nhập.
            model.addAttribute("signupSuccess", true);
            return "login";
        } else {
            // Đăng ký thất bại, hiển thị thông báo lỗi.
            model.addAttribute("signupError", "Username is already taken.");
            return "signup";
        }
    }

}

