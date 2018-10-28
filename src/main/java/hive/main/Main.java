package hive.main;

import hive.moves.Game;
import org.apache.log4j.Logger;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();

        game.playGameFromTheStart();

    }


}
