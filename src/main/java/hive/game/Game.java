package hive.game;

import hive.interfaces.Hive;
import hive.models.Board;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game implements Hive {

    private static final Logger logger = Logger.getLogger(Game.class);
    private static final String IO_EXCEPTION_MESSAGE = "Er was iets fout met de input";
    private static final String NULL_POINTER_EXCEPTION_MESSAGE = "JE INPUT WAS NIET GOED";

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
        if (getGameState().getCurrentPlayer() == getGameState().getBlackPlayer()) {
            if (! canBlackPlayerPass()) {
                throw new IllegalMove("Je mag niet passen");
            }
        } else if (gameState.getCurrentPlayer() == gameState.getWhitePlayer()) {
            if (! canWhitePlayerPass()) {
                throw new IllegalMove("Je mag niet passen");
            }
        } else {
            gameState.changePlayer();
        }
    }

    public boolean isWinner(Player player) {
        PlayerClass playerClass = gameState.getPlayer(player);
        PlayerClass otherPlayer;
        if (playerClass == gameState.getWhitePlayer()) {
            otherPlayer = gameState.getBlackPlayer();
        } else {
            otherPlayer = gameState.getWhitePlayer();
        }
        return (board.isQueenOfOpponentSurrounded(playerClass) && ! board.isQueenOfOpponentSurrounded(otherPlayer));
    }

    public boolean isDraw() {
        if (canBlackPlayerPass() && canWhitePlayerPass()) {
            return true;
        }
        return (board.isQueenOfOpponentSurrounded(gameState.getPlayer(Player.WHITE)) && board.isQueenOfOpponentSurrounded(gameState.getPlayer(Player.BLACK)));
    }

    Board getBoard() {
        return board;
    }

    GameState getGameState() {
        return gameState;
    }

    boolean canBlackPlayerPass() {
        PlayerClass blackPlayer = gameState.getBlackPlayer();

        if (blackPlayer.getDeck().isEmpty()) {
            return board.canPlayerMove(blackPlayer);
        }
        return (! blackPlayer.getDeck().isEmpty() && ! canTileBePlaced(blackPlayer) && ! board.canPlayerMove(blackPlayer));
    }

    private boolean canWhitePlayerPass() {
        PlayerClass whitePlayer = gameState.getBlackPlayer();

        if (whitePlayer.getDeck().isEmpty()) {
            return board.canPlayerMove(whitePlayer);
        }
        return (! whitePlayer.getDeck().isEmpty() && ! canTileBePlaced(whitePlayer) && ! board.canPlayerMove(whitePlayer));
    }

    boolean canTileBePlaced(PlayerClass player) {
        return board.canTileBePlacedForPlayer(player);
    }

    private Hive.Tile makeTileFromString(String tileString) {
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

        while (! isGameFinished()) {
            try {
                System.out.println("Het is de beurt van: " + gameState.getCurrentPlayer().getPlayerEnum());
                System.out.println("Wat voor zet wil je doen? (pass, leggen, verplaatsen)");
                move = reader.readLine();
                switch (move) {
                    case "pass": pass(); break;
                    case "leggen": playInTerminal(reader); break;
                    case "verplaatsen": moveInTerminal(reader); break;
                    default:
                        System.out.println("Geef een goede input");
                }
            } catch (Hive.IllegalMove e) {
                System.out.println(e.toString());
            } catch (IOException e) {
                logger.error(IO_EXCEPTION_MESSAGE, e);
            } catch (NullPointerException e) {
                logger.error(NULL_POINTER_EXCEPTION_MESSAGE);
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
            logger.error(IO_EXCEPTION_MESSAGE, e);
        } catch (NullPointerException e) {
            logger.error(NULL_POINTER_EXCEPTION_MESSAGE);
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
            logger.error(IO_EXCEPTION_MESSAGE, e);
        } catch (NullPointerException e) {
            logger.error(NULL_POINTER_EXCEPTION_MESSAGE);
        }
    }
}
