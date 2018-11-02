package hive.models;

import hive.interfaces.Hive;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class BoardTests {

    //---------------------------------FUNCTIONAL TESTS-----------------------------------------------------------------

    @Test
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

    @Test(expected = Hive.IllegalMove.class)
    public void blackPlacingTwoStonesOnSamePlace() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
    }


    @Test(expected = Hive.IllegalMove.class)
    public void whitePlacingTwoStonesOnSamePlace() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test(expected = Hive.IllegalMove.class)
    public void firstPlayOfBlackHasToBeNextToWhite() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 2, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
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

    @Test(expected = Hive.IllegalMove.class)
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

    @Test(expected = Hive.IllegalMove.class)
    public void tryMovingStoneWhileQueenIsNotSet() throws Hive.IllegalMove{
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT,0,0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.moveStone(whitePlayer, 0,0,1,-1);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void tryPlaceStoneOnEmptySpot() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 0, 1);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0,3);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void tryPlacingStoneNextToOtherPlayer() throws Hive.IllegalMove{
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 0, 1);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 0, -1);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -2);
    }

    @Test(expected = Hive.IllegalMove.class)
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

    @Test(expected = Hive.IllegalMove.class)
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

    @Test(expected = Hive.IllegalMove.class)
    public void blackPlacingQueenStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 1, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void whitePlacingQueenStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 1, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void placeTwoQueensOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
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

    @Test(expected = Hive.IllegalMove.class)
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

    @Test
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

    @Test(expected = Hive.IllegalMove.class)
    public void placeTwoSpidersOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 0, 0);
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
    public void blackPlacingSpiderStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 1, 0);
        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 2, 0);
    }

    @Test
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

    @Test(expected = Hive.IllegalMove.class)
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

    @Test
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

    @Test
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

    @Test(expected = Hive.IllegalMove.class)
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

    @Test(expected = Hive.IllegalMove.class)
    public void whitePlacingBeetleStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 2, 0);
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
    public void placeTwoBeetlesOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.BEETLE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void placeTwoBeetlesOfOwnPlayerOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.BEETLE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 0, 1);
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 0, 1);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void placeBeetleOnTopOfSpider() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 0, 0);
    }

    @Test
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

    @Test
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

    @Test(expected = Hive.IllegalMove.class)
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

    @Test(expected = Hive.IllegalMove.class)
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
    public void whitePlacingAntStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 2, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 3, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void placeTwoAntsOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
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

    @Test
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

    @Test
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

    @Test
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

    @Test(expected = Hive.IllegalMove.class)
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

    @Test(expected = Hive.IllegalMove.class)
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
    public void whitePlacingGrasshopperStonesWithNoneLeft() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 2, 0);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 3, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void placeTwoGrasshoppersOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveOnOwnTileCanNotBeMadeWithGrasshopper() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 1, 0);

        board.moveStone(whitePlayer, 1, 0, 1, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void moveOfGrasshopperHasToBeOverOneTile() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 1, 0);

        board.moveStone(whitePlayer, 1, 0, 0, 1);
    }

    @Test(expected = Hive.IllegalMove.class)
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

    @Test(expected = Hive.IllegalMove.class)
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

    @Test
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

