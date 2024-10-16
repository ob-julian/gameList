package julian.web.game_list.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import julian.web.game_list.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}