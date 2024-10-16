package julian.web.game_list.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import julian.web.game_list.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}