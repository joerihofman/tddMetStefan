package hive.models;

import hive.interfaces.Hive;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class BoardTests {

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

    @Test(expected = Hive.IllegalMove.class)
    public void placeTwoQueensOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void placeTwoSpidersOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SPIDER, 0, 0);
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
    public void placeTwoGrasshoppersOnTopOfEachOther() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.GRASSHOPPER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.GRASSHOPPER, 0, 0);
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
    public void placeBeetleOnTopOfSpider() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.SPIDER, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 0, 0);
    }

    @Test
    public void neighborsForPositiveQAndNegativeR() {
        Board board = new Board();
        ArrayList<Pair<Integer, Integer>> expectedValues = new ArrayList<>();

        expectedValues.add(Pair.of(4, -2));
        expectedValues.add(Pair.of(6, -2));
        expectedValues.add(Pair.of(4, -1));
        expectedValues.add(Pair.of(6, -3));
        expectedValues.add(Pair.of(5, -3));
        expectedValues.add(Pair.of(5, -1));

        assertArrayEquals(expectedValues.toArray(), board.getAllNeighbors(Pair.of(5, -2)).toArray());
    }

    @Test
    public void neighborsForNegativeQAndPositiveR() {
        Board board = new Board();
        ArrayList<Pair<Integer, Integer>> expectedValues = new ArrayList<>();

        expectedValues.add(Pair.of(-2, 3));
        expectedValues.add(Pair.of(0, 3));
        expectedValues.add(Pair.of(-2, 4));
        expectedValues.add(Pair.of(0, 2));
        expectedValues.add(Pair.of(-1, 2));
        expectedValues.add(Pair.of(-1, 4));

        assertArrayEquals(expectedValues.toArray(), board.getAllNeighbors(Pair.of(-1, 3)).toArray());
    }

    @Test
    public void neighborsForPositiveQAndPositiveR() {
        Board board = new Board();
        ArrayList<Pair<Integer, Integer>> expectedValues = new ArrayList<>();

        expectedValues.add(Pair.of(1, 3));
        expectedValues.add(Pair.of(3, 3));
        expectedValues.add(Pair.of(1, 4));
        expectedValues.add(Pair.of(3, 2));
        expectedValues.add(Pair.of(2, 2));
        expectedValues.add(Pair.of(2, 4));

        assertArrayEquals(expectedValues.toArray(), board.getAllNeighbors(Pair.of(2, 3)).toArray());
    }

    @Test
    public void neighborsForNegativeQAndNegativeR() {
        Board board = new Board();
        ArrayList<Pair<Integer, Integer>> expectedValues = new ArrayList<>();

        expectedValues.add(Pair.of(-3, -3));
        expectedValues.add(Pair.of(-1, -3));
        expectedValues.add(Pair.of(-3, -2));
        expectedValues.add(Pair.of(-1, -4));
        expectedValues.add(Pair.of(-2, -4));
        expectedValues.add(Pair.of(-2, -2));

        assertArrayEquals(expectedValues.toArray(), board.getAllNeighbors(Pair.of(-2, -3)).toArray());
    }

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
    public void hasOpponentNeighbors() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);

        assertTrue(board.hasOpponentNeighbor(Pair.of(1, 0), blackPlayer));
    }

    @Test
    public void doesNotHaveOpponentNeighbors() throws Hive.IllegalMove {
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 1, 0);

        assertFalse(board.hasOpponentNeighbor(Pair.of(2, 0), whitePlayer));
    }

    @Test(expected = Hive.IllegalMove.class)
    public void firstPlayOfBlackHasToBeNextToWhiteWithException() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 2, 0);
    }

    @Test
    public void isBoardIntact() {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        HashMap<Pair<Integer, Integer>, BoardTile> boardMap = (HashMap) board.getBoardMap();

        boardMap.put(Pair.of(0, 0), new BoardTile(Hive.Tile.QUEEN_BEE, blackPlayer));
        boardMap.put(Pair.of(1, 0), new BoardTile(Hive.Tile.SPIDER, whitePlayer));
        boardMap.put(Pair.of(-1, 1), new BoardTile(Hive.Tile.BEETLE, blackPlayer));
        boardMap.put(Pair.of(2, 0), new BoardTile(Hive.Tile.SOLDIER_ANT, whitePlayer));
        boardMap.put(Pair.of(-1, 0), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(Pair.of(2, -1), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(Pair.of(-1, -1), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(Pair.of(3, -1), new BoardTile(Hive.Tile.SOLDIER_ANT, whitePlayer));


        assertTrue(board.isHiveIntactAfterMove(Pair.of(3, -1)));
    }

    @Test
    public void isBoardNotIntact() {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        HashMap<Pair<Integer, Integer>, BoardTile> boardMap = (HashMap) board.getBoardMap();

        boardMap.put(Pair.of(0, 0), new BoardTile(Hive.Tile.QUEEN_BEE, blackPlayer));
        boardMap.put(Pair.of(1, 0), new BoardTile(Hive.Tile.SPIDER, whitePlayer));
        boardMap.put(Pair.of(-1, 1), new BoardTile(Hive.Tile.BEETLE, blackPlayer));
        boardMap.put(Pair.of(2, 0), new BoardTile(Hive.Tile.SOLDIER_ANT, whitePlayer));
        boardMap.put(Pair.of(-1, 0), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(Pair.of(2, -1), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));
        boardMap.put(Pair.of(-1, -2), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(Pair.of(3, -1), new BoardTile(Hive.Tile.SOLDIER_ANT, whitePlayer));

        assertFalse(board.isHiveIntactAfterMove(Pair.of(3, -1)));
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
    public void HiveDoesNotStayIntact() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        HashMap<Pair<Integer, Integer>, BoardTile> boardMap = (HashMap) board.getBoardMap();

        boardMap.put(Pair.of(0, 0), new BoardTile(Hive.Tile.QUEEN_BEE, blackPlayer));
        boardMap.put(Pair.of(0, -1), new BoardTile(Hive.Tile.BEETLE, whitePlayer));
        boardMap.put(Pair.of(0, -2), new BoardTile(Hive.Tile.BEETLE, blackPlayer));
        boardMap.put(Pair.of(1, -3), new BoardTile(Hive.Tile.SOLDIER_ANT, whitePlayer));
        boardMap.put(Pair.of(2, -3), new BoardTile(Hive.Tile.GRASSHOPPER, blackPlayer));
        boardMap.put(Pair.of(2, -2), new BoardTile(Hive.Tile.QUEEN_BEE, whitePlayer));

        blackPlayer.deductTile(Hive.Tile.QUEEN_BEE);
        whitePlayer.deductTile(Hive.Tile.QUEEN_BEE);
        board.moveStone(whitePlayer, 0,-1, 1,-1);
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

    @Test(expected = Hive.IllegalMove.class)
    public void slideCanNotBeDoneWithAnt() throws Hive.IllegalMove {
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

    @Test
    public void slideCanBeDoneWithBeetle() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        Board board = new Board();

        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 1);
        board.placeStone(blackPlayer, Hive.Tile.BEETLE, -1, -1);
        board.printBoard();
        board.moveStone(blackPlayer, -1, -1, 0, -1);

        board.printBoard();

//        board.moveStone(blackPlayer, -1, 1, -1, 0);
//        board.moveStone(blackPlayer, -1, 0, 0, -1);
    }
}
