package hive.models;

import hive.interfaces.Hive;

public class GameState {
    private PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
    private PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);

    private PlayerClass currentPlayer = whitePlayer;

    public void changePlayer() {
        if (currentPlayer == blackPlayer) {
            currentPlayer = whitePlayer;
        } else {
            currentPlayer = blackPlayer;
        }
    }

    public PlayerClass getCurrentPlayer() {
        return currentPlayer;
    }

    public PlayerClass getWhitePlayer() {
        return whitePlayer;
    }

    public PlayerClass getBlackPlayer() {
        return blackPlayer;
    }

}
