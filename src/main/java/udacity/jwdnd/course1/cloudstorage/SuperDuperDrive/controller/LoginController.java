package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("error", false);
        model.addAttribute("logout", false);
        return "login";
    }
}
