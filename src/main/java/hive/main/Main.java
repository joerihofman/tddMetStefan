package hive.main;

import hive.interfaces.Hive;
import hive.models.PlayerClass;
import hive.moves.Game;
import org.apache.log4j.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        PlayerClass playerClass = new PlayerClass(Hive.Player.BLACK);

    }
}
