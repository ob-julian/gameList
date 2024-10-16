package julian.web.game_list.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import julian.web.game_list.model.User;
import julian.web.game_list.model.Role;
import julian.web.game_list.repository.UserRepository;
import julian.web.game_list.repository.RoleRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional 
    public User createUser(String username, String rawPassword, Boolean passwordChangeRequired) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setEnabled(true);
        user.setPasswordChangeRequired(passwordChangeRequired);
        userRepository.save(user);
        return user;
    }
    	
    public User createUser(String username, String rawPassword) {
        return createUser(username, rawPassword, false);
    }

    public Boolean updateUserPassword(User user, String rawPassword) {


        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setPasswordChangeRequired(false);
        userRepository.save(user);
        return true;
    }

    public Boolean updateUserPassword(Long userId, String rawPassword) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }
        return updateUserPassword(user, rawPassword);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Boolean checkPassword(String username, String rawPassword) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public Boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public void addRoleToUser(User user, Role role) {
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public void addRoleToUser(Long userId, Long roleId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new UsernameNotFoundException("Role not found"));
        addRoleToUser(user, role);
    }

    public void addRoleToUser(String username, Long roleId) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new UsernameNotFoundException("Role not found"));
        addRoleToUser(user, role);
    }

    public void addRoleToUser(Long userId, String roleName) throws UsernameNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new UsernameNotFoundException("Role not found"));
        addRoleToUser(user, role);
    }

    public void addRoleToUser(String username, String roleName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new UsernameNotFoundException("Role not found"));
        addRoleToUser(user, role);
    }

    public void removeRoleFromUser(User user, Role role) {
        user.getRoles().remove(role);
        userRepository.save(user);
    }

    public void removeRoleFromUser(Long userId, Long roleId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new UsernameNotFoundException("Role not found"));
        user.getRoles().remove(role);
        userRepository.save(user);
    } 

    public void removeRoleFromUser(String username, Long roleId) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new UsernameNotFoundException("Role not found"));
        user.getRoles().remove(role);
        userRepository.save(user);
    }

    public void removeRoleFromUser(Long userId, String roleName) throws UsernameNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new UsernameNotFoundException("Role not found"));
        user.getRoles().remove(role);
        userRepository.save(user);
    }

    public void removeRoleFromUser(String username, String roleName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new UsernameNotFoundException("Role not found"));
        user.getRoles().remove(role);
        userRepository.save(user);
    }
}