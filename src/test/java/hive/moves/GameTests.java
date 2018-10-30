package hive.moves;

import hive.interfaces.Hive;
import hive.models.GameState;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTests {

    @Test
    public void testEnums() throws Hive.IllegalMove {
        Game game = new Game();
        game.play(Hive.Tile.SOLDIER_ANT, 0, 0);
        game.play(Hive.Tile.BEETLE, 1, 0);
        game.play(Hive.Tile.QUEEN_BEE, 0, -1);
        game.play(Hive.Tile.QUEEN_BEE, 1, 1);
        game.play(Hive.Tile.GRASSHOPPER, -1, 0);
        game.play(Hive.Tile.SPIDER, 2, 0);
        game.play(Hive.Tile.SOLDIER_ANT, 1, -2);
    }

    @Test
    public void whiteStarts() {
        GameState gameState = new GameState();
        assertEquals(Hive.Player.WHITE, gameState.getCurrentPlayer().getPlayerEnum());
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

        ArrayList<Pair<Hive.Tile, Integer>> expectedDeck = new ArrayList<>();
        expectedDeck.add(Pair.of(Hive.Tile.QUEEN_BEE, 1));
        expectedDeck.add(Pair.of(Hive.Tile.SPIDER, 2));
        expectedDeck.add(Pair.of(Hive.Tile.BEETLE, 1));
        expectedDeck.add(Pair.of(Hive.Tile.SOLDIER_ANT, 3));
        expectedDeck.add(Pair.of(Hive.Tile.GRASSHOPPER, 3));

        assertArrayEquals(expectedDeck.toArray(), game.getGameState().getWhitePlayer().getDeck().toArray());
    }

    @Test
    public void checkIfPlayerHasChangedAfterPlay() throws Hive.IllegalMove {
        Game game = new Game();
        game.play(Hive.Tile.BEETLE, 0, 0);

        assertEquals(Hive.Player.BLACK, game.getGameState().getCurrentPlayer().getPlayerEnum());
    }

    @Test
    public void checkIfPlayerHasChangedAfterTwoPlays() throws Hive.IllegalMove {
        Game game = new Game();
        game.play(Hive.Tile.BEETLE, 0, 0);
        game.play(Hive.Tile.QUEEN_BEE, 1, 0);

        assertEquals(Hive.Player.WHITE, game.getGameState().getCurrentPlayer().getPlayerEnum());
    }

    @Test
    public void winnerIsBlackWithWhiteQueenSurrounded() throws Hive.IllegalMove {
        Game game = new Game();

        game.play(Hive.Tile.QUEEN_BEE, 0, 0);
        game.play(Hive.Tile.BEETLE, 1, 0);
        game.play(Hive.Tile.BEETLE, -1, 1);
        game.play(Hive.Tile.QUEEN_BEE, 2, -1);
        game.play(Hive.Tile.GRASSHOPPER, -2, 1);
        game.play(Hive.Tile.GRASSHOPPER, 2, -2);
        game.play(Hive.Tile.SPIDER, 0, -1);
        game.play(Hive.Tile.SOLDIER_ANT, 1, 1);
        game.play(Hive.Tile.SOLDIER_ANT, -1, -1);
        game.play(Hive.Tile.SOLDIER_ANT, 3, -2);
        game.play(Hive.Tile.SOLDIER_ANT, -1, 0);
        //grasshopper implementatie moet nog voor deze move
        game.move(2, -2, 1, -1);

        System.exit(2);
        game.play(Hive.Tile.SOLDIER_ANT, -2, 2);
        //Ant implementatie moet nog voor deze move
        game.move(1, 1, 0, 1);
        //grasshopper implementatie moet nog voor deze move
        game.move(-2, 1, -1 ,0);

        assertTrue(game.isWinner(Hive.Player.BLACK));
    }
}
