package hive.moves;

import hive.interfaces.Hive;
import org.junit.Before;
import org.junit.Test;

public class GameTests {

    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testEnums() throws Hive.IllegalMove {
        game.play(Hive.Tile.SOLDIER_ANT, 0, 0);
        game.play(Hive.Tile.BEETLE, 1, 0);
        game.play(Hive.Tile.QUEEN_BEE, 0, 1);
        game.play(Hive.Tile.QUEEN_BEE, 0, 2);
        game.play(Hive.Tile.GRASSHOPPER, 2, 0);
        game.play(Hive.Tile.SPIDER, 1, 1);
        game.play(Hive.Tile.SOLDIER_ANT, 2, 2);
    }

}
