package hive.moves;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTests {

    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testEnums() {
        assertEquals(false, game.isDraw());
    }

}
