package controller;

import dto.CreateUserRequest;
import dto.UpdateUserRequest;
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
        model.addAttribute("user", new CreateUserRequest());
        model.addAttribute("isEdit", false);
        return "edit";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") CreateUserRequest request) {
        userService.save(toUser(request));
        return "redirect:/users"; // Redirect prevents duplicate form submissions on refresh
    }

    @GetMapping("/users/{id}/edit")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/users";
        }

        model.addAttribute("user", toUpdateRequest(user));
        model.addAttribute("userId", id);
        model.addAttribute("isEdit", true);
        return "edit";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") UpdateUserRequest request) {
        userService.update(id, request);
        return "redirect:/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users";
    }

    private User toUser(CreateUserRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        return user;
    }

    private UpdateUserRequest toUpdateRequest(User user) {
        return new UpdateUserRequest(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAge()
        );
    }
}
