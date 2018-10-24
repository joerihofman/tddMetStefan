package hive.main;

import hive.BelowZeroException;
import hive.interfaces.Hive;
import hive.models.Player;
import org.apache.log4j.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        Player player = new Player(Hive.Player.BLACK);
        try {
            logger.info(player.getQueenCount());
            player.deductQueen();
            logger.info(player.getQueenCount());
            player.deductQueen();
            logger.info(player.getQueenCount());
        } catch (BelowZeroException e) {
            e.printStackTrace();
        }

    }
}
