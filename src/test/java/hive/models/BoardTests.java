package hive.models;

import hive.interfaces.Hive;
import org.junit.Before;
import org.junit.Test;

public class BoardTests {


    @Before
    public void setUp() {
    }

    @Test(expected = Hive.IllegalMove.class)
    public void blackPlacingTwoStonesOnSamePlace() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void blackPlacingQueenStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 1, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void blackPlacingBeetleStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, 2, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void blackPlacingSpiderStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();
        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 2, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void blackPlacingAntStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 2, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 3, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void blackPlacingGrasshopperStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 2, 0);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 3, 0);
    }


    @Test(expected = Hive.IllegalMove.class)
    public void whitePlacingTwoStonesOnSamePlace() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void whitePlacingQueenStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 1, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void whitePlacingBeetleStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 2, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void whitePlacingSpiderStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 2, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void whitePlacingAntStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 2, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 3, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void whitePlacingGrasshopperStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 2, 0);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 3, 0);
    }

    @Test
    public void bothPlacingStonesNextToEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 1, 1);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 0, 1);
        board.placeStone(blackPlayer, Hive.Tile.SPIDER, -1, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 0, -1);
    }
}
