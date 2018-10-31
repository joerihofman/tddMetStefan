package hive.moves;

import hive.interfaces.Hive;
import hive.models.Board;
import hive.models.GameState;
import hive.models.PlayerClass;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game implements Hive {

    private static final Logger logger = Logger.getLogger(Game.class);

    private Board board = new Board();
    private GameState gameState = new GameState();

    public void play(Tile tile, int q, int r) throws IllegalMove {
        PlayerClass currentPlayer = gameState.getCurrentPlayer();
        board.placeStone(currentPlayer, tile, q, r);
        gameState.changePlayer();
    }

    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {
        PlayerClass currentPlayer = gameState.getCurrentPlayer();
        board.moveStone(currentPlayer, fromQ, fromR, toQ, toR);
        gameState.changePlayer();
    }

    public void pass() throws IllegalMove {
        throw new IllegalMove("Er kan nog niet gepassd worden omdat die niks doet");
    }

    public boolean isWinner(Player player) {
        PlayerClass playerClass = gameState.getPlayer(player);
        return (board.isQueenSurrounded(playerClass));
    }

    public boolean isDraw() {
        //deze moet nog
        return false;
    }

    public void printBoard() {
        board.printBoard();
    }

    public GameState getGameState() {
        return gameState;
    }

    public Hive.Tile makeTileFromString(String tileString) {
        switch (tileString){
            case ("bee"): return Hive.Tile.QUEEN_BEE;
            case ("spider"): return Hive.Tile.SPIDER;
            case ("beetle"): return Hive.Tile.BEETLE;
            case ("ant"): return Hive.Tile.SOLDIER_ANT;
            case ("grass"): return Hive.Tile.GRASSHOPPER;
            default: return null;
        }
    }

    public void playGameFromTheStart() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String move;

        while (! isGameFinished()) { //als er nog geen winnaar of gelijkspel is
            try {
                System.out.println("Het is de beurt van: " + gameState.getCurrentPlayer().getPlayerEnum());
                System.out.println("Wat voor zet wil je doen? (pass, leggen, verplaatsen)");
                move = reader.readLine();
                if (move.equals("pass")) {
                    pass();
                } else if (move.equals("leggen")) {
                    playInTerminal(reader);
                } else if(move.equals("verplaatsen")) {
                    moveInTerminal(reader);
                } else {
                    System.out.println("Doe even een goede input ofzo");
                }
            } catch (Hive.IllegalMove e) {
                System.out.println(e.toString());
            } catch (IOException e) {
                logger.error("er was iets fout met de input ofzo ", e);
            } catch (NullPointerException e) {
                logger.error("JE INPUT WAS NIET GOED IDIOOT");
            }

        }
    }

    private boolean isGameFinished() {
        return (isWinner(gameState.getCurrentPlayer().getPlayerEnum()) || isDraw());
    }

    private void playInTerminal(BufferedReader reader) {
        String tile;
        String q;
        String r;

        try {
            System.out.println("Wat wil je leggen? (bee, spider, beetle, ant, grass)");
            tile = reader.readLine();
            System.out.println("Op welke Q?");
            q = reader.readLine();
            System.out.println("Op welke R?");
            r = reader.readLine();
            play(makeTileFromString(tile), Integer.valueOf(q), Integer.valueOf(r));
            board.printBoard();
        } catch (Hive.IllegalMove e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            logger.error("er was iets fout met de input ofzo ", e);
        } catch (NullPointerException e) {
            logger.error("JE INPUT WAS NIET GOED IDIOOT");
        }
    }

    private void moveInTerminal(BufferedReader reader) {
        String q;
        String r;
        String toQ;
        String toR;

        try {
            System.out.println("Van welke Q?");
            q = reader.readLine();
            System.out.println("Van welke R?");
            r = reader.readLine();
            System.out.println("Naar welke Q?");
            toQ = reader.readLine();
            System.out.println("Naar welke R?");
            toR = reader.readLine();
            move(Integer.valueOf(q), Integer.valueOf(r), Integer.valueOf(toQ), Integer.valueOf(toR));
            board.printBoard();
        } catch (Hive.IllegalMove e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            logger.error("er was iets fout met de input ofzo ", e);
        } catch (NullPointerException e) {
            logger.error("JE INPUT WAS NIET GOED IDIOOT");
        }
    }
}
