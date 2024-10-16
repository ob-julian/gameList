package julian.web.game_list.initializer;

import julian.web.game_list.model.User;
import julian.web.game_list.model.Role;
import julian.web.game_list.service.UserService;
import julian.web.game_list.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        if (userService.getAllUsers().isEmpty()) {
            User initialAdmin = userService.createUser("admin", "admin", true);
            if (roleService.getAllRoles().isEmpty()) {
                roleService.createRole("USER");
            }
            Role adminRole = roleService.createRole("ADMIN");
            userService.addRoleToUser(initialAdmin, adminRole);
            initialAdmin.setPasswordChangeRequired(true);
            System.out.println("Default admin user created with username: admin and password: admin");
        }
    }
}