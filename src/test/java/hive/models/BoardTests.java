package hive.models;

import hive.interfaces.Hive;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class BoardTests {

    //---------------------------------FUNCTIONAL TESTS-----------------------------------------------------------------

    @Test //2c
    public void emptyBoard() {
        Board board = new Board();
        assertEquals(Integer.valueOf(0), board.amountOfTiles());
    }

    @Test
    public void notEmptyBoard() throws Hive.IllegalMove {
        Board board = new Board();

        board.placeStone(new PlayerClass(Hive.Player.WHITE), Hive.Tile.BEETLE, 0, 0);
        board.placeStone(new PlayerClass(Hive.Player.BLACK), Hive.Tile.BEETLE, 0, 1);

        assertEquals(Integer.valueOf(2), board.amountOfTiles());
    }

    @Test(expected = Hive.IllegalMove.class) //4b
    public void blackPlacingTwoStonesOnSamePlace() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
    }


    @Test(expected = Hive.IllegalMove.class) //4b
    public void whitePlacingTwoStonesOnSamePlace() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //4d
    public void bothPlacingStonesNextToEachOtherWithoutEmptySpaceInBetween() throws Hive.IllegalMove {
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

    @Test //2b
    public void neighborsForPositiveQAndNegativeR() {
        Board board = new Board();
        ArrayList<Hex> expectedValues = new ArrayList<>();

        expectedValues.add(new Hex(4, -2));
        expectedValues.add(new Hex(6, -2));
        expectedValues.add(new Hex(4, -1));
        expectedValues.add(new Hex(6, -3));
        expectedValues.add(new Hex(5, -3));
        expectedValues.add(new Hex(5, -1));

        assertArrayEquals(expectedValues.toArray(), board.getAllNeighbors(new Hex(5, -2)).toArray());
    }

    @Test //2b
    public void neighborsForNegativeQAndPositiveR() {
        Board board = new Board();
        ArrayList<Hex> expectedValues = new ArrayList<>();

        expectedValues.add(new Hex(-2, 3));
        expectedValues.add(new Hex(0, 3));
        expectedValues.add(new Hex(-2, 4));
        expectedValues.add(new Hex(0, 2));
        expectedValues.add(new Hex(-1, 2));
        expectedValues.add(new Hex(-1, 4));

        assertArrayEquals(expectedValues.toArray(), board.getAllNeighbors(new Hex(-1, 3)).toArray());
    }

    @Test //2b
    public void neighborsForPositiveQAndPositiveR() {
        Board board = new Board();
        ArrayList<Hex> expectedValues = new ArrayList<>();

        expectedValues.add(new Hex(1, 3));
        expectedValues.add(new Hex(3, 3));
        expectedValues.add(new Hex(1, 4));
        expectedValues.add(new Hex(3, 2));
        expectedValues.add(new Hex(2, 2));
        expectedValues.add(new Hex(2, 4));

        assertArrayEquals(expectedValues.toArray(), board.getAllNeighbors(new Hex(2, 3)).toArray());
    }

    @Test //2b
    public void neighborsForNegativeQAndNegativeR() {
        Board board = new Board();
        ArrayList<Hex> expectedValues = new ArrayList<>();

        expectedValues.add(new Hex(-3, -3));
        expectedValues.add(new Hex(-1, -3));
        expectedValues.add(new Hex(-3, -2));
        expectedValues.add(new Hex(-1, -4));
        expectedValues.add(new Hex(-2, -4));
        expectedValues.add(new Hex(-2, -2));

        assertArrayEquals(expectedValues.toArray(), board.getAllNeighbors(new Hex(-2, -3)).toArray());
    }

    @Test
    public void hasOpponentNeighbors() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);

        assertTrue(board.hasOpponentNeighbor(new Hex(1, 0), blackPlayer));
    }

    @Test
    public void doesNotHaveOpponentNeighbors() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 1, 0);

        assertFalse(board.hasOpponentNeighbor(new Hex(2, 0), whitePlayer));
    }

    @Test
    public void boardIsIntact() {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        HashMap<Hex, BoardTile> boardMap = (HashMap) board.getBoardMap();

        boardMap.put(new Hex(0, 0), new BoardTile(Hive.Tile.QUEEN_BEE, blackPlayer));
        boardMap.put(new Hex(1, 0), new BoardTile(Hive.Tile.SPIDER, whitePlayer));
        boardMap.put(new Hex(-1, 1), new BoardTile(Hive.Tile.BEETLE, blackPlayer));
        boardMap.put(new Hex(2, 0), new BoardTile(Hive.Tile.SOLDIER_ANT, whitePlayer));
        boardMap.put(new Hex(-1, 0), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(new Hex(2, -1), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(new Hex(-1, -1), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(new Hex(3, -1), new BoardTile(Hive.Tile.SOLDIER_ANT, whitePlayer));


        assertTrue(board.isHiveIntactAfterMove(new Hex(3, -1)));
    }

    @Test
    public void boardIsNotIntact() {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        HashMap<Hex, BoardTile> boardMap = (HashMap) board.getBoardMap();

        boardMap.put(new Hex(0, 0), new BoardTile(Hive.Tile.QUEEN_BEE, blackPlayer));
        boardMap.put(new Hex(1, 0), new BoardTile(Hive.Tile.SPIDER, whitePlayer));
        boardMap.put(new Hex(-1, 1), new BoardTile(Hive.Tile.BEETLE, blackPlayer));
        boardMap.put(new Hex(2, 0), new BoardTile(Hive.Tile.SOLDIER_ANT, whitePlayer));
        boardMap.put(new Hex(-1, 0), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(new Hex(2, -1), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(new Hex(-1, -2), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(new Hex(3, -1), new BoardTile(Hive.Tile.SOLDIER_ANT, whitePlayer));

        assertFalse(board.isHiveIntactAfterMove(new Hex(3, -1)));
    }

    //---------------------------------RULE TESTS-----------------------------------------------------------------------

    @Test //5a en 5e
    public void makeMoveToEmptySpot() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, -1, 1);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 2, 0);

        board.moveStone(whitePlayer, -1, 1, -1, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //4c
    public void firstPlayOfBlackHasToBeNextToWhite() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 2, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //6c
    public void moveTileWhileInterceptingHive() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, -1, 1);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 2, 0);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, -1, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 2, -1);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, -1);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 1, 1);

        board.moveStone(whitePlayer, -1, 1, -1, 2);
    }

    @Test(expected = Hive.IllegalMove.class) //5d
    public void hiveDoesNotStayIntact() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        HashMap<Hex, BoardTile> boardMap = (HashMap) board.getBoardMap();

        boardMap.put(new Hex(0, 0), new BoardTile(Hive.Tile.QUEEN_BEE, blackPlayer));
        boardMap.put(new Hex(0, -1), new BoardTile(Hive.Tile.BEETLE, whitePlayer));
        boardMap.put(new Hex(0, -2), new BoardTile(Hive.Tile.BEETLE, blackPlayer));
        boardMap.put(new Hex(1, -3), new BoardTile(Hive.Tile.SOLDIER_ANT, whitePlayer));
        boardMap.put(new Hex(2, -3), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(new Hex(2, -2), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));

        blackPlayer.deductTile(Hive.Tile.QUEEN_BEE);
        whitePlayer.deductTile(Hive.Tile.QUEEN_BEE);
        board.moveStone(whitePlayer, 0,-1, 1,-1);
    }

    @Test(expected = Hive.IllegalMove.class) //5b
    public void tryMovingStoneWhileQueenIsNotSet() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT,0,0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.moveStone(whitePlayer, 0,0,1,-1);
    }

    @Test(expected = Hive.IllegalMove.class) //4b
    public void tryPlaceStoneOnEmptySpot() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 0, 1);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0,3);
    }

    @Test(expected = Hive.IllegalMove.class) //4d
    public void tryPlacingStoneNextToOtherPlayer() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 0, 1);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 0, -1);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -2);
    }

    @Test(expected = Hive.IllegalMove.class) //5d
    public void tryMovingStoneWhileDisconnectingHive() throws Hive.IllegalMove{
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        HashMap<Hex, BoardTile> boardMap = (HashMap) board.getBoardMap();

        boardMap.put(new Hex(0, -2), new BoardTile(Hive.Tile.BEETLE, blackPlayer));
        boardMap.put(new Hex(1, -3), new BoardTile(Hive.Tile.SOLDIER_ANT, whitePlayer));
        boardMap.put(new Hex(2, -3), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(new Hex(2, -2), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(new Hex(1, -1), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(new Hex(2, -1), new BoardTile(Hive.Tile.SOLDIER_ANT, whitePlayer));
        boardMap.put(new Hex(2, 0), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(new Hex(1, 1), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(new Hex(0, 1), new BoardTile(Hive.Tile.BEETLE, blackPlayer));

        whitePlayer.deductTile(Hive.Tile.QUEEN_BEE);
        blackPlayer.deductTile(Hive.Tile.QUEEN_BEE);

        board.moveStone(blackPlayer, 0,1, 0,0);
    }

    @Test(expected = Hive.IllegalMove.class) //4e
    public void tryPlacingStoneWhileQueenIsNotPlacedAfterTurnFour() throws Hive.IllegalMove{
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 0, 1);
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, -1, -1);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, -1, -2);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, -1, 2);
    }

    //---------------------------------QUEEN TESTS----------------------------------------------------------------------

    @Test(expected = Hive.IllegalMove.class) //4a
    public void blackPlacingQueenStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 1, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //4a
    public void whitePlacingQueenStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 1, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //8b
    public void placeTwoQueensOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //8a en 5e
    public void moveOfTwoTilesCanNotBeMadeWithQueen() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, -1, -1);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, -2, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, -2, 1);

        board.moveStone(whitePlayer, 1, 0, 1, -2);
    }

    @Test(expected = Hive.IllegalMove.class) //5e en 6b
    public void moveOfOneTileWithQueenIntoGap() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, -1, 1);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, -1, -1);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, -2, 0);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, -2, 2);

        board.moveStone(whitePlayer, -2, 2, -2, 1);
        board.moveStone(whitePlayer, -2, 1, -1, 0);
    }

    @Test //8a en 5e
    public void moveOfOneTileWithQueen() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, -1, -1);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, -2, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, -2, 1);

        board.moveStone(whitePlayer, 1, 0, 1, -1);
    }

    //---------------------------------SPIDER TESTS---------------------------------------------------------------------

    @Test(expected = Hive.IllegalMove.class) //4b
    public void placeTwoSpidersOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //4a
    public void whitePlacingSpiderStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 2, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //4a
    public void blackPlacingSpiderStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 2, 0);
    }

    @Test //10c en 5e
    public void moveCanBeMadeWithSpider() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 1, -1);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 1, -2);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, -2);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, -1, -1);
        board.placeStone(blackPlayer, Hive.Tile.SPIDER, -1, 1);
        blackPlayer.deductTile(Hive.Tile.QUEEN_BEE);

        board.moveStone(blackPlayer, -1, 1, 2, -1);
    }

    @Test(expected = Hive.IllegalMove.class) //5e en 6b
    public void moveCanNotBeMadeWithSpiderInGap() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, -1, -1);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, -2, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, -2, 1);

        whitePlayer.deductTile(Hive.Tile.QUEEN_BEE);
        board.moveStone(whitePlayer, 1, 0, -1, 0);
    }

    @Test //10d en 5e
    public void moveTwoTilesCanNotBeMadeWithSpider() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 1, 0);

        whitePlayer.deductTile(Hive.Tile.QUEEN_BEE);
        board.moveStone(whitePlayer, 1, 0, -1, 1);
    }

    @Test //10c en 5e
    public void moveOneTileCanNotBeMadeWithSpider() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 1, 0);

        whitePlayer.deductTile(Hive.Tile.QUEEN_BEE);
        board.moveStone(whitePlayer, 1, 0, 0, 1);
    }

    @Test(expected = Hive.IllegalMove.class) //10b
    public void moveOnOwnTileCanNotBeMadeWithSpider() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 1, 0);

        whitePlayer.deductTile(Hive.Tile.QUEEN_BEE);
        board.moveStone(whitePlayer, 1, 0, 1, 0);
    }

    //---------------------------------BEETLE TESTS---------------------------------------------------------------------

    @Test(expected = Hive.IllegalMove.class) //4a
    public void whitePlacingBeetleStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 2, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //4a
    public void blackPlacingBeetleStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.BEETLE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, 2, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //7a en 6b en 4b
    public void placeTwoBeetlesOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.BEETLE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //7a en 4b
    public void placeTwoBeetlesOfOwnPlayerOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.BEETLE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 0, 1);
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 0, 1);
    }

    @Test(expected = Hive.IllegalMove.class) //7a
    public void placeBeetleOnTopOfSpider() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 0, 0);
    }

    @Test //7a
    public void makeMoveToUsedSpotWithBeetle() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, -1, 1);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 2, 0);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, -1, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 2, -1);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, -1);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 1, 1);

        board.moveStone(whitePlayer, -1, 1, -1, 0);

        HashMap<Hex, BoardTile> boardMap = (HashMap<Hex, BoardTile>) board.getBoardMap();
        assertEquals(Integer.valueOf(2), boardMap.get(new Hex(-1, 0)).getStackSize());
    }

    @Test // 7a en 6b
    public void stackCanBeMadeWithBeetle() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 1);
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, -1, -1);
        board.moveStone(blackPlayer, -1, -1, 0, -1);
    }

    @Test(expected = Hive.IllegalMove.class) //7a en 6b
    public void moveCanNotBeMadeWithBeetleOnSameLevel() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, -1, -1);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, -2, 0);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, -1, 1);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, -2, 2);
        board.moveStone(whitePlayer, -2, 2, -2, 1);
        board.moveStone(whitePlayer, -2, 1, -1, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //7a en 5e
    public void moveOfTwoTilesCanNotBeMadeWithBeetle() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, -1, -1);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, -2, 0);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, -1, 1);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, -2, 2);
        board.moveStone(whitePlayer, -2, 2, 0, 1);
    }

    //---------------------------------ANT TESTS------------------------------------------------------------------------

    @Test(expected = Hive.IllegalMove.class) //4a
    public void blackPlacingAntStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 2, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 3, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //4a
    public void whitePlacingAntStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 2, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 3, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //9c en 4b
    public void placeTwoAntsOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //9c
    public void makeMoveToUsedSpotWithAnt() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, -1, 1);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 2, 0);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, -1, 0);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 2, -1);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, -1, -1);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 3, -1);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, -2, 1);

        board.moveStone(whitePlayer, 3, -1, 2, 0);
    }

    @Test //9c en 5e
    public void slideCanBeDoneWithAnt() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 1, -1);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 1, -2);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, -2);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, -1, -1);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, -1, 1);
        blackPlayer.deductTile(Hive.Tile.QUEEN_BEE);

        board.moveStone(blackPlayer, -1, 1, -1, 0);
        board.moveStone(blackPlayer, -1, 0, -2, 0);
        board.moveStone(blackPlayer, -2, 0, -1, 0);
    }

    @Test //9a en 5e
    public void moveCanBeMadeWithAnt() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 1);
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, -1, -1);
        board.moveStone(blackPlayer, -1, -1, 0, -2);
    }

    @Test //9a en 5e en 6b
    public void moveCanBeMadeWithAntOnEmptyTileInBigGap() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 1);
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, -1, -1);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, -1, -2);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, -1, 2);
        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 1, -2);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, -2, 2);
        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 1, -3);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 0, 2);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 2, -2);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 1, 1);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 3, -2);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, -3, 2);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 2, -3);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, -3, 1);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, -1, -3);
        board.moveStone(blackPlayer, -1, -3, 2, -1);
        board.moveStone(blackPlayer, 2, -1, -1, 1);
    }

    @Test(expected = Hive.IllegalMove.class) // 9c en 5e en 6b
    public void moveCanNotBeMadeWithAntBecauseOfGap() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 1, -1);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 1, -2);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, -2);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, -1, -1);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, -1, 1);
        blackPlayer.deductTile(Hive.Tile.QUEEN_BEE);

        board.moveStone(blackPlayer, -1, 1, -1, 0);
        board.moveStone(blackPlayer, -1, 0, 0, -1);
    }

    @Test(expected = Hive.IllegalMove.class) //9a en 5e
    public void moveCanNotBeMadeWithAntOnTheSameTile() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 1, -1);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 1);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 2, -2);

        board.moveStone(blackPlayer, 2, -2, 2, -2);
    }

    //---------------------------------GRASSHOPPER TESTS----------------------------------------------------------------

    @Test(expected = Hive.IllegalMove.class) //4a
    public void blackPlacingGrasshopperStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 2, 0);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 3, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //4a
    public void whitePlacingGrasshopperStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 2, 0);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 3, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //4b
    public void placeTwoGrasshoppersOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //11b en 5e
    public void moveOnOwnTileCanNotBeMadeWithGrasshopper() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 1, 0);

        board.moveStone(whitePlayer, 1, 0, 1, 0);
    }

    @Test(expected = Hive.IllegalMove.class) //11c en 5e
    public void moveOfGrasshopperHasToBeOverOneTile() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 1, 0);

        board.moveStone(whitePlayer, 1, 0, 0, 1);
    }

    @Test(expected = Hive.IllegalMove.class) //11d en 5e
    public void tryMovingGrasshopperOnToAnotherTile() throws Hive.IllegalMove{
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        HashMap<Hex, BoardTile> boardMap = (HashMap) board.getBoardMap();

        boardMap.put(new Hex(0, 0), new BoardTile(Hive.Tile.BEETLE, blackPlayer));
        boardMap.put(new Hex(1, -1), new BoardTile(Hive.Tile.GRASSHOPPER, whitePlayer));
        boardMap.put(new Hex(0, 1), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(new Hex(-1, 1), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(new Hex(0, 1), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));

        blackPlayer.deductTile(Hive.Tile.QUEEN_BEE);
        whitePlayer.deductTile(Hive.Tile.QUEEN_BEE);

        board.moveStone(whitePlayer, 1,-1, -1,1);
    }

    @Test(expected = Hive.IllegalMove.class) //11e en 5e
    public void tryMovingGrasshopperOverEmptyTile() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        HashMap<Hex, BoardTile> boardMap = (HashMap) board.getBoardMap();

        boardMap.put(new Hex(1, -1), new BoardTile(Hive.Tile.GRASSHOPPER, whitePlayer));
        boardMap.put(new Hex(0, 1), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(new Hex(-1, 1), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(new Hex(0, 1), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(new Hex(1, 0), new BoardTile(Hive.Tile.BEETLE, whitePlayer));

        blackPlayer.deductTile(Hive.Tile.QUEEN_BEE);
        whitePlayer.deductTile(Hive.Tile.QUEEN_BEE);

        board.moveStone(whitePlayer, 1,-1, -2,2);
    }

    @Test //11a en 5e
    public void MovingGrasshoppperOverstones() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        HashMap<Hex, BoardTile> boardMap = (HashMap) board.getBoardMap();
        boardMap.put(new Hex(0, -3), new BoardTile(Hive.Tile.GRASSHOPPER, whitePlayer));
        boardMap.put(new Hex(0, -2), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(new Hex(0, -1), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(new Hex(0, 0), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(new Hex(0, 1), new BoardTile(Hive.Tile.BEETLE, whitePlayer));
        boardMap.put(new Hex(0, 2), new BoardTile(Hive.Tile.GRASSHOPPER, whitePlayer));
        boardMap.put(new Hex(0, 3), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(new Hex(0, 4), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(new Hex(0, 5), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(new Hex(0, 6), new BoardTile(Hive.Tile.BEETLE, whitePlayer));
        boardMap.put(new Hex(0, 7), new BoardTile(Hive.Tile.GRASSHOPPER, whitePlayer));
        boardMap.put(new Hex(0, 8), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(new Hex(0, 9), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(new Hex(0, 10), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(new Hex(0, 11), new BoardTile(Hive.Tile.BEETLE, whitePlayer));

        blackPlayer.deductTile(Hive.Tile.QUEEN_BEE);
        whitePlayer.deductTile(Hive.Tile.QUEEN_BEE);

        board.moveStone(whitePlayer, 0,-3, 0,12);
    }

}

