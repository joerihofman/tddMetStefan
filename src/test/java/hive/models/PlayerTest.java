package hive.models;

import hive.interfaces.Hive;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

    Player blackPlayer;
    Player whitePlayer;

    @Before
    public void setUp() {
         blackPlayer = new Player(Hive.Player.BLACK);
         whitePlayer = new Player(Hive.Player.WHITE);
    }

    @Test
    public void blackPlayerAmountOfTiles(){
        blackPlayer.getQueenCount();
        blackPlayer.getGrasshopperCount();
    }
}
