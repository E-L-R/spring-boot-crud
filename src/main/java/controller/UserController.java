package controller;

import lombok.RequiredArgsConstructor;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping({"/", "/users"})
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/users/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "edit";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/users"; // Redirect prevents duplicate form submissions on refresh
    }

    @GetMapping("/users/{id}/edit")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "edit";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        // Ensure the ID from the URL path is set on the user object before updating
        user.setId(id);
        userService.save(user);
        return "redirect:/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
