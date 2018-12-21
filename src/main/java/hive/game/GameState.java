package hive.game;

import nl.hanze.hive.Hive;

class GameState {
    private PlayerClass whitePlayer = new PlayerClass(Hive.Player.WHITE);
    private PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);

    private PlayerClass currentPlayer = whitePlayer;

    void changePlayer() {
        if (currentPlayer == blackPlayer) {
            currentPlayer = whitePlayer;
        } else {
            currentPlayer = blackPlayer;
        }
    }

    PlayerClass getCurrentPlayer() {
        return currentPlayer;
    }

    PlayerClass getWhitePlayer() {
        return whitePlayer;
    }

    PlayerClass getBlackPlayer() {
        return blackPlayer;
    }

    PlayerClass getPlayer(Hive.Player player) {
        if (player == Hive.Player.WHITE) {
            return whitePlayer;
        } else {
            return blackPlayer;
        }
    }

}
