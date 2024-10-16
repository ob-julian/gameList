package julian.web.game_list.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import julian.web.game_list.model.Game;
import julian.web.game_list.repository.GameRepository;

import java.util.List;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;
    
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id).orElse(null);
    }

    @Transactional
    public Game saveGame(Game spiel) {
        return gameRepository.save(spiel);
    }

    @Transactional
    public Game editGame(Game gameDetails) {
        Long id = gameDetails.getId();
        Game game = gameRepository.findById(id).orElse(null);
        if (game != null) {
            game.setName(gameDetails.getName());
            game.setDescription(gameDetails.getDescription());
            game.setPlatform(gameDetails.getPlatform());
            game.setUrl(gameDetails.getUrl());
            game.setImageUrl(gameDetails.getImageUrl());
            game.setCodeUrl(gameDetails.getCodeUrl());
            game.setLanguages(gameDetails.getLanguages());
            return gameRepository.save(game);
        } else {
            return null;
        }
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}
