package hive.moves;

import hive.interfaces.Hive;

public class Move implements Hive {

    public void play(Tile tile, int q, int r) throws IllegalMove {
        throw new IllegalMove();
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
