package hive.moves;

import hive.interfaces.Hive;
import hive.models.*;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GameTests {

    //3b wordt op veel plekken getest; dmv. game.play, game.move en game.pass

    @Test //1b
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

    @Test //2a en 2d (er mogen geen kommagetallen in dus kan een tile alleen maar
            //precies in een vak liggen
    public void testHex() {
        new Hex(1, 1);
    }

    @Test //3b
    public void playFirstTile() throws Hive.IllegalMove {
        Game game = new Game();
        game.play(Hive.Tile.BEETLE, 0, 0);
    }

    @Test //3b
    public void playFirstTwoTiles() throws Hive.IllegalMove {
        Game game = new Game();
        game.play(Hive.Tile.BEETLE, 0, 0);
        game.play(Hive.Tile.BEETLE, 1, 0);
    }

    @Test //1c
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

    @Test //3a
    public void whiteBegins() {
        Game game = new Game();

        assertEquals(Hive.Player.WHITE, game.getGameState().getCurrentPlayer().getPlayerEnum());
    }

    @Test //3a
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

    @Test //3b
    public void tileCanNotBePlacedByBlackPlayer() throws Hive.IllegalMove {
        Game game = new Game();
        Board board = game.getBoard();
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, -1, 1);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, -2, 1);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 2, -1);

        board.moveStone(whitePlayer, -2, 1, -1, -1);
        board.moveStone(whitePlayer, -1, 1, -1, 0);
        board.moveStone(whitePlayer, 2, -1, 1, -2);
        board.moveStone(whitePlayer, 1, 0, 1, -1);
        assertFalse(game.canTileBePlaced(blackPlayer));
    }

    @Test //3b
    public void tileCanBePlacedByBlackPlayer() throws Hive.IllegalMove {
        Game game = new Game();
        Board board = game.getBoard();
        PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, -1, 1);

        assertTrue(game.canTileBePlaced(blackPlayer));
    }

    @Test //3b
    public void blackPlayerCannotMove() throws Hive.IllegalMove{
        Game game = new Game();
        Board board = game.getBoard();
        PlayerClass blackPlayer = game.getGameState().getBlackPlayer();
        PlayerClass whitePlayer = game.getGameState().getWhitePlayer();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 1, 0);
        board.moveStone(whitePlayer, 1, 0, 0, -2);

        assertFalse(board.canPlayerMove(blackPlayer));
    }

    @Test //3b
    public void blackPlayerCanMove() throws Hive.IllegalMove{
        Game game = new Game();
        Board board = game.getBoard();
        PlayerClass blackPlayer = game.getGameState().getBlackPlayer();
        PlayerClass whitePlayer = game.getGameState().getWhitePlayer();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);

        assertTrue(board.canPlayerMove(blackPlayer));
    }

    @Test(expected = Hive.IllegalMove.class)
    public void playerCanNotPassBecauseTileCanBePlaced() throws Hive.IllegalMove {
        Game game = new Game();
        Board board = game.getBoard();

        game.play(Hive.Tile.QUEEN_BEE, 0, 0);
        game.play(Hive.Tile.QUEEN_BEE, 0, 1);
        game.play(Hive.Tile.SOLDIER_ANT, 0, -1);
        board.printBoard();
        game.pass();
    }

    @Test
    public void playerCannotPassAndOnlyMove() throws Hive.IllegalMove {
        Game game = new Game();
        Board board = game.getBoard();
        PlayerClass whitePlayer = game.getGameState().getWhitePlayer();
        PlayerClass blackPlayer = game.getGameState().getBlackPlayer();
        HashMap<Hex, BoardTile> boardMap = (HashMap) board.getBoardMap();

        game.play(Hive.Tile.QUEEN_BEE, 0, 0);
        game.play(Hive.Tile.BEETLE, 1,-1);
        game.play(Hive.Tile.BEETLE, -1, 0);
        boardMap.put(new Hex(0, -1), new BoardTile(Hive.Tile.GRASSHOPPER, whitePlayer));
        boardMap.put(new Hex(2, -2), new BoardTile(Hive.Tile.BEETLE, whitePlayer));
        boardMap.put(new Hex(1, 0), new BoardTile(Hive.Tile.GRASSHOPPER, whitePlayer));
        boardMap.put(new Hex(2, -1), new BoardTile(Hive.Tile.GRASSHOPPER, whitePlayer));

        blackPlayer.deductTile(Hive.Tile.QUEEN_BEE);

        assertTrue(board.canPlayerMove(blackPlayer));
        assertFalse(game.canTileBePlaced(blackPlayer));
    }

    @Test
    public void playerCanPassBecauseNoPlaysAndMovesCanBeMade() throws Hive.IllegalMove {
        Game game = new Game();
        Board board = game.getBoard();
        PlayerClass blackPlayer = game.getGameState().getBlackPlayer();
        PlayerClass whitePlayer = game.getGameState().getWhitePlayer();

        board.placeStone(whitePlayer, Hive.Tile.QUEEN_BEE, 0, 0);
        board.placeStone(blackPlayer, Hive.Tile.QUEEN_BEE, 0, -1);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, -1, 1);
        board.placeStone(whitePlayer, Hive.Tile.BEETLE, 1, 0);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, -2, 1);
        board.placeStone(whitePlayer, Hive.Tile.SOLDIER_ANT, 2, -1);

        board.moveStone(whitePlayer, -2, 1, -1, -1);
        board.moveStone(whitePlayer, 2, -1, 1, -2);
        board.moveStone(whitePlayer, -1, 1, -1, 0);
        board.moveStone(whitePlayer, 1, 0, 1, -1);

        assertTrue(game.canBlackPlayerPass());
    }

    @Test //3c
    public void winnerIsBlackWithWhiteQueenSurrounded() throws Hive.IllegalMove {
        Game game = new Game();

        game.play(Hive.Tile.QUEEN_BEE, 0, 0);
        game.play(Hive.Tile.BEETLE, 1, 0);
        game.play(Hive.Tile.BEETLE, -1, 1);
        game.play(Hive.Tile.QUEEN_BEE, 2, -1);
        game.play(Hive.Tile.GRASSHOPPER, -2, 1);
        game.play(Hive.Tile.BEETLE, 2, -2);
        game.play(Hive.Tile.SPIDER, 0, -1);
        game.play(Hive.Tile.SOLDIER_ANT, 1, 1);
        game.play(Hive.Tile.SOLDIER_ANT, -1, -1);
        game.play(Hive.Tile.SOLDIER_ANT, 3, -2);
        game.play(Hive.Tile.SOLDIER_ANT, -1, 0);
        game.move(2, -2, 1, -1);
        game.play(Hive.Tile.SOLDIER_ANT, -2, 2);
        game.move(1, 1, 0, 1);

        assertTrue(game.isWinner(Hive.Player.BLACK));
    }

    @Test //3c
    public void winnerIsWhiteWithBlackQueenSurrounded() throws Hive.IllegalMove {
        Game game = new Game();

        game.play(Hive.Tile.QUEEN_BEE, 0, 0);
        game.play(Hive.Tile.QUEEN_BEE, 1, 0);
        game.play(Hive.Tile.BEETLE, -1, 1);
        game.play(Hive.Tile.BEETLE, 2, -1);
        game.play(Hive.Tile.GRASSHOPPER, -2, 1);
        game.play(Hive.Tile.BEETLE, 2, -2);
        game.play(Hive.Tile.SPIDER, -1, 0);
        game.play(Hive.Tile.SOLDIER_ANT, 2, 0);
        game.play(Hive.Tile.SOLDIER_ANT, 0, -1);
        game.play(Hive.Tile.SOLDIER_ANT, 1, 1);
        game.move(-2, 1, 0, 1);
        game.play(Hive.Tile.SPIDER, 3, -2);
        game.move(0, -1, 1, -1);

        assertTrue(game.isWinner(Hive.Player.WHITE));
    }

}
