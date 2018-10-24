package hive.models;

import hive.interfaces.Hive;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private Player blackPlayer;
    private Player whitePlayer;

    @Before
    public void setUp() {
         blackPlayer = new Player(Hive.Player.BLACK);
         whitePlayer = new Player(Hive.Player.WHITE);
    }

    @Test
    public void blackPlayerAmountOfQueens(){
        assertEquals(1, blackPlayer.getQueenCount());
   }

    @Test
    public void blackPlayerAmountOfSpiders(){
        assertEquals(2, blackPlayer.getSpiderCount());
    }

    @Test
    public void blackPlayerAmountOfBeetles(){
        assertEquals(2, blackPlayer.getBeetleCount());
    }

    @Test
    public void blackPlayerAmountOfAnts(){
        assertEquals(3, blackPlayer.getAntCount());
    }

    @Test
    public void blackPlayerAmountOfGrasshoppers(){
        assertEquals(3, blackPlayer.getGrasshopperCount());
    }

        @Test
    public void whitePlayerAmountOfQueens(){
        assertEquals(1, whitePlayer.getQueenCount());
   }

    @Test
    public void whitePlayerAmountOfSpiders(){
        assertEquals(2, whitePlayer.getSpiderCount());
    }

    @Test
    public void whitePlayerAmountOfBeetles(){
        assertEquals(2, whitePlayer.getBeetleCount());
    }

    @Test
    public void whitePlayerAmountOfAnts(){
        assertEquals(3, whitePlayer.getAntCount());
    }

    @Test
    public void whitePlayerAmountOfGrasshoppers(){
        assertEquals(3, whitePlayer.getGrasshopperCount());
    }
}
