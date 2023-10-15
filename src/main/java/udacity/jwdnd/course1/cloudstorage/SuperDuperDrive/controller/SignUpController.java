package udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.model.User;
import udacity.jwdnd.course1.cloudstorage.SuperDuperDrive.service.UserService;

@Controller
public class SignUpController {

    @Autowired
    UserService userservice;

    @GetMapping("/signup")
    public ModelAndView signUp(ModelMap model){
        model.addAttribute("error", false);
        model.addAttribute("done", false);
        return new ModelAndView("signup");
    }

    @PostMapping("/signup")
    public ModelAndView register(@ModelAttribute User user, ModelMap model) {
        boolean check = true;
        boolean  noSignupError = true;
        if(userservice.getUser(user.getUsername()) != null) {
            check = false;
        } else {
            if (userservice.RegisterUser(user) <= 0) {
                check = false;
            }
        }

        if(!check) {
            model.addAttribute("error", true);
            model.addAttribute("done", false);
            return new ModelAndView("signup");
        }
        if (noSignupError == true) {
            model.addAttribute("signupSuccess", true);
            return new ModelAndView("login");
        } else {
            model.addAttribute("noSignupError", noSignupError);
        }
        model.addAttribute("done", true);
        model.addAttribute("error", false);
        return new ModelAndView("signup");
    }
}
