package hive.moves;

import hive.interfaces.Hive;
import hive.models.Board;
import hive.models.GameState;
import hive.models.PlayerClass;

public class Game implements Hive {

    private Board board = new Board();
    private GameState gameState = new GameState();

    public void play(Tile tile, int q, int r) throws IllegalMove {
        PlayerClass currentPlayer = gameState.getCurrentPlayer();
        board.placeStone(currentPlayer, tile, q, r);
        gameState.changePlayer();
    }

    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {
        throw new IllegalMove();
    }

    public void pass() throws IllegalMove {
        throw new IllegalMove();
    }

    public boolean isWinner(Player player) {
        return false;
    }

    public boolean isDraw() {
        return false;
    }

}
