package az.millikart.springbootdemoweb.controller;

import az.millikart.springbootdemoweb.entity.User;
import az.millikart.springbootdemoweb.exception.UserNotFoundException;
import az.millikart.springbootdemoweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showUserList(Model model) {
        List<User> userList = userService.getAllUsers();
        log.info("Users : {}", userList);
        model.addAttribute("users", userList);
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) throws UserNotFoundException {
        User user = userService.getUserById(id);

        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        userService.addUser(user);
        return "redirect:/";
    }


    @PostMapping("/update")
    public String updateUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update-user";
        }

        userService.updateUser(user);
        model.addAttribute("updated-user", user);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
