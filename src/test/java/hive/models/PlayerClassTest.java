package hive.models;

import hive.interfaces.Hive;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerClassTest {

    private PlayerClass blackPlayer;
    private PlayerClass whitePlayer;

    @Before
    public void setUp() {
         blackPlayer = new PlayerClass(Hive.Player.BLACK);
         whitePlayer = new PlayerClass(Hive.Player.WHITE);
    }

    @Test //1c
    public void blackPlayerAmountOfQueens(){
        assertEquals(1, blackPlayer.getQueenCount());
   }

    @Test //1c
    public void blackPlayerAmountOfSpiders(){
        assertEquals(2, blackPlayer.getSpiderCount());
    }

    @Test //1c
    public void blackPlayerAmountOfBeetles(){
        assertEquals(2, blackPlayer.getBeetleCount());
    }

    @Test //1c
    public void blackPlayerAmountOfAnts(){
        assertEquals(3, blackPlayer.getAntCount());
    }

    @Test //1c
    public void blackPlayerAmountOfGrasshoppers(){
        assertEquals(3, blackPlayer.getGrasshopperCount());
    }

    @Test //1c
    public void whitePlayerAmountOfQueens(){
        assertEquals(1, whitePlayer.getQueenCount());
   }

    @Test //1c
    public void whitePlayerAmountOfSpiders(){
        assertEquals(2, whitePlayer.getSpiderCount());
    }

    @Test //1c
    public void whitePlayerAmountOfBeetles(){
        assertEquals(2, whitePlayer.getBeetleCount());
    }

    @Test //1c
    public void whitePlayerAmountOfAnts(){
        assertEquals(3, whitePlayer.getAntCount());
    }

    @Test //1c
    public void whitePlayerAmountOfGrasshoppers(){
        assertEquals(3, whitePlayer.getGrasshopperCount());
    }

    @Test(expected = Hive.IllegalMove.class)
    public void blackDeductQueensUntilNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Hive.Tile tile = Hive.Tile.QUEEN_BEE;
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void blackDeductSpidersUntilNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Hive.Tile tile = Hive.Tile.SPIDER;
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void blackDeductBeetlesUntilNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Hive.Tile tile = Hive.Tile.BEETLE;
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void blackDeductAntsUntilNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Hive.Tile tile = Hive.Tile.SOLDIER_ANT;
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void blackDeductGrasshoppersUntilNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.BLACK);
        Hive.Tile tile = Hive.Tile.GRASSHOPPER;
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void whiteDeductQueensUntilNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.WHITE);
        Hive.Tile tile = Hive.Tile.QUEEN_BEE;
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void whiteDeductSpidersUntilNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.WHITE);
        Hive.Tile tile = Hive.Tile.SPIDER;
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void whiteDeductBeetlesUntilNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.WHITE);
        Hive.Tile tile = Hive.Tile.BEETLE;
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void whiteDeductAntsUntilNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.WHITE);
        Hive.Tile tile = Hive.Tile.SOLDIER_ANT;
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
    }

    @Test(expected = Hive.IllegalMove.class)
    public void whiteDeductGrasshoppersUntilNoneLeft() throws Hive.IllegalMove {
        PlayerClass blackPlayer = new PlayerClass(Hive.Player.WHITE);
        Hive.Tile tile = Hive.Tile.GRASSHOPPER;
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
        blackPlayer.deductTile(tile);
    }
}
