package hive.interfaces;

/**
 * Hive game.
 */
public interface Hive {
    /**
     * Play a new tile.
     * @param tile BoardTile to play
     * @param q Q coordinate of hexagon to play to
     * @param r R coordinate of hexagon to play to
     * @throws IllegalMove If the tile could not be played
     */
    public void play(Tile tile, int q, int r) throws IllegalMove;

    /**
     * Move an existing tile.
     * @param fromQ Q coordinate of the tile to move
     * @param fromR R coordinate of the tile to move
     * @param toQ Q coordinate of the hexagon to move to
     * @param toR R coordinare of the hexagon to move to
     * @throws IllegalMove If the tile could not be moved
     */
    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove;

    /**
     * Pass the turn.
     * @throws IllegalMove If the turn could not be passed
     */
    public void pass() throws IllegalMove;

    /**
     * Check whether the given player is the winner.
     * @param player PlayerClass to check
     * @return Boolean
     */
    public boolean isWinner(Player player);

    /**
     * Check whether the game is a draw.
     * @return Boolean
     */
    public boolean isDraw();

    /**
     * Illegal move exception.
     */
    public class IllegalMove extends Exception {
        public IllegalMove() { super(); }
        public IllegalMove(String message) { super(message); }
    }

    /**
     * Types of tiles.
     */
    public enum Tile {
        QUEEN_BEE("Q"),
        SPIDER("S"),
        BEETLE("B"),
        GRASSHOPPER("G"),
        SOLDIER_ANT("A");

        private String letter;

        Tile(String letter){
            this.letter = letter;
        }

        @Override
        public String toString() {
            return letter;
        }
    }

    /**
     * Players.
     */
    public enum Player {
        WHITE("W"),
        BLACK("B");

        private String letter;

        Player(String letter){
            this.letter = letter;
        }

        @Override
        public String toString() {
            return letter;
        }
    }
}
