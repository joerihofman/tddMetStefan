package hive.models;

import hive.interfaces.Hive;
import org.junit.Before;
import org.junit.Test;

public class BoardTests {

    private Board board;
    private Player blackPlayer;

    @Before
    void setUp() {
        board = new Board();
        blackPlayer = new Player(Hive.Player.BLACK);
    }

    @Test
    void tryMakingNewTile(){
        BoardTile tile = new BoardTile(Hive.Tile.BEETLE);
        board.placeStone(blackPlayer, tile, 0, 0);
    }
}
