package julian.web.game_list.controller;

import julian.web.game_list.service.UserService;
import julian.web.game_list.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "addUser";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addUser")
    public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam String confirmPassword, @RequestParam Long roleId, @RequestParam Boolean passwordChangeRequired) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/addUser";
        }
        userService.createUser(username, password, passwordChangeRequired);
        userService.addRoleToUser(username, roleId);

        return "redirect:/";
    }
}