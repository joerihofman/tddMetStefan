package hive.models;

import hive.interfaces.Hive;

public class GameState {
    private PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
    private PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);

    private PlayerClass currentPlayer = blackPlayer;

    public void changePlayer() {
        if (currentPlayer == blackPlayer) {
            currentPlayer = whitePlayer;
        } else {
            currentPlayer = whitePlayer;
        }
    }

    public PlayerClass getCurrentPlayer() {
        return currentPlayer;
    }

}
