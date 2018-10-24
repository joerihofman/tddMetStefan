package hive.moves;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveTests {

    private Move move;

    @Before
    public void setUp() {
        move = new Move();
    }

    @Test
    public void testEnums() {
        assertEquals(false, move.isDraw());
    }

}
