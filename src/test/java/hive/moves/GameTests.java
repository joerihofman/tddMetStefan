package hive.moves;

import hive.interfaces.Hive;
import hive.models.GameState;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTests {

    @Test
    public void testEnums() throws Hive.IllegalMove {
        Game game = new Game();
        game.play(Hive.Tile.SOLDIER_ANT, 0, 0);
        game.play(Hive.Tile.BEETLE, 1, 0);
        game.play(Hive.Tile.QUEEN_BEE, 0, 1);
        game.play(Hive.Tile.QUEEN_BEE, 0, 2);
        game.play(Hive.Tile.GRASSHOPPER, 2, 0);
        game.play(Hive.Tile.SPIDER, 1, 1);
        game.play(Hive.Tile.SOLDIER_ANT, 2, 2);
    }

    @Test
    public void whiteStarts() {
        GameState gameState = new GameState();
        assertEquals(Hive.Player.WHITE, gameState.getCurrentPlayer());
    }

    @Test
    public void playFirstTile() throws Hive.IllegalMove {
        Game game = new Game();
        game.play(Hive.Tile.BEETLE, 0, 0);
    }

    @Test
    public void playFirstTwoTiles() throws Hive.IllegalMove {
        Game game = new Game();
        game.play(Hive.Tile.BEETLE, 0, 0);
        game.play(Hive.Tile.BEETLE, 1, 0);
    }

    @Test
    public void playTileAndCheckDeck() throws Hive.IllegalMove {
        Game game = new Game();
        game.play(Hive.Tile.BEETLE, 0, 0);
    }


}
