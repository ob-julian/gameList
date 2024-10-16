package julian.web.game_list.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import julian.web.game_list.model.User;
import julian.web.game_list.model.Role;
import julian.web.game_list.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(String roleName) {
        roleName = "ROLE_" + roleName.toUpperCase();
        Role existingRole = roleRepository.findByName(roleName).orElse(null);
        if (existingRole != null) {
            return existingRole;
        }
        Role role = new Role();
        role.setName(roleName);
        roleRepository.save(role);
        return role;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName).orElse(null);
    }

    public void deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }
}