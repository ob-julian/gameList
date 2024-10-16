package julian.web.game_list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import julian.web.game_list.service.GameService;
import julian.web.game_list.model.Game;

@Controller
public class gameController {
    
    @Autowired
    private GameService gameService;
    
    @GetMapping("/")
    public String gameListe(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        return "index";
    }

    @GetMapping("/game/{id}")
    public String gameDetails(@PathVariable Long id, Model model) {
        model.addAttribute("game", gameService.getGameById(id));
        return "game";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addGame")
    public String addGame(@ModelAttribute Game game) {
        gameService.saveGame(game);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editGame/{id}")
    public String editGame(@PathVariable Long id, @ModelAttribute Game game) {
        game.setId(id);
        gameService.editGame(game);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteGame/{id}")
    public String deleteGame(@PathVariable Long id) {
        Game game = gameService.getGameById(id);
        if (game != null) {
            gameService.deleteGame(id);
        }
        return "redirect:/";
    }
}