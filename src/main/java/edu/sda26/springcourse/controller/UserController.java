package edu.sda26.springcourse.controller;

import edu.sda26.springcourse.exception.UserAlreadyExistingException;
import edu.sda26.springcourse.model.Users;
import edu.sda26.springcourse.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }


    @GetMapping("/user")
    public String showRegisterPage(ModelMap modelMap){
        Users users = new Users();
        modelMap.addAttribute("user", users);
        return"create-users";
    }

    @PostMapping("/user/register")
    public String registerUser(@Valid  @ModelAttribute("user") Users users) throws UserAlreadyExistingException {
        users.setRole("USER");
        usersService.save(users);

        return "redirect:/";
    }
}
