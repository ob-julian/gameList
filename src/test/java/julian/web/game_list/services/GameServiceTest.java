package julian.web.game_list.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import julian.web.game_list.model.Game;
import julian.web.game_list.repository.GameRepository;
import julian.web.game_list.service.GameService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;







public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllGames() {
        Game game1 = new Game();
        game1.setName("Game 1");
        Game game2 = new Game();
        game2.setName("Game 2");

        when(gameRepository.findAll()).thenReturn(Arrays.asList(game1, game2));

        List<Game> games = gameService.getAllGames();

        assertNotNull(games);
        assertEquals(2, games.size());
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    public void testGetGameById() {
        Game game = new Game();
        game.setId(1L);
        game.setName("Test Game");

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        Game foundGame = gameService.getGameById(1L);

        assertNotNull(foundGame);
        assertEquals("Test Game", foundGame.getName());
        verify(gameRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveGame() {
        Game game = new Game();
        game.setName("Test Game");

        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Game savedGame = gameService.saveGame(game);

        assertNotNull(savedGame);
        assertEquals("Test Game", savedGame.getName());
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    public void testEditGame() {
        Game existingGame = new Game();
        existingGame.setId(1L);
        existingGame.setName("Existing Game");

        Game updatedGameDetails = new Game();
        updatedGameDetails.setId(1L);
        updatedGameDetails.setName("Updated Game");

        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame));
        when(gameRepository.save(any(Game.class))).thenReturn(updatedGameDetails);

        Game updatedGame = gameService.editGame(updatedGameDetails);

        assertNotNull(updatedGame);
        assertEquals("Updated Game", updatedGame.getName());
        verify(gameRepository, times(1)).findById(1L);
        verify(gameRepository, times(1)).save(existingGame);
    }

    @Test
    public void testDeleteGame() {
        Game game = new Game();
        game.setId(1L);
        game.setName("Test Game");

        doNothing().when(gameRepository).deleteById(1L);

        gameService.deleteGame(1L);

        verify(gameRepository, times(1)).deleteById(1L);
    }
}