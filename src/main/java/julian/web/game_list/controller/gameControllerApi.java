package julian.web.game_list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

import julian.web.game_list.service.GameService;
import julian.web.game_list.model.Game;

import java.util.List;

// Allow requests from the frontend on Angular
@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "http://localhost:4200")
public class gameControllerApi {

    @Autowired
    private GameService gameService;

    // Get all games
    @GetMapping
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    // Get a game by ID
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        Game game = gameService.getGameById(id);
        if (game != null) {
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Add a new game
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Game addGame(@RequestBody Game game) {
        return gameService.saveGame(game);
    }

    // Update an existing game
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Game> editGame(@PathVariable Long id, @RequestBody Game gameDetails) {
        Game game = gameService.editGame(gameDetails);
        if (game != null) {
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a game
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        Game game = gameService.getGameById(id);
        if (game != null) {
            gameService.deleteGame(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}