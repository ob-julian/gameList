package julian.web.game_list.controller;

import julian.web.game_list.model.User;
import julian.web.game_list.service.CustomUserDetailsService;
import julian.web.game_list.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordChangeController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/changePassword")
    public String showChangePasswordPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            model.addAttribute("username", null);
            return "changePassword";
        }
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam String username, @RequestParam String currentPassword, @RequestParam String newPassword, @RequestParam String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return "redirect:/changePassword?passwordDontMatchError";
        }
        if (newPassword.equals(currentPassword)) {
            return "redirect:/changePassword?samePasswordError";
        }
        // chanche password
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return "redirect:/changePassword?userError";
        }
        if (!userService.checkPassword(user, currentPassword)) {
            return "redirect:/changePassword?currentPasswordError";
        }
        userService.updateUserPassword(user, newPassword);
        // if user is authenticated, update the authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) { 
            customUserDetailsService.updateLoginUser(user);
        }
        return "redirect:/login";
    }
}