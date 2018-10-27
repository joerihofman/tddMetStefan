package hive.models;

import hive.interfaces.Hive;

public class PlayerClass {

    private Hive.Player playerEnum;
    private int queenCount;
    private int spiderCount;
    private int beetleCount;
    private int antCount;
    private int grasshopperCount;
    private int amountOfMoves;

    public PlayerClass(Hive.Player playerEnum) {
        this.playerEnum = playerEnum;
        this.queenCount = 1;
        this.spiderCount = 2;
        this.beetleCount = 2;
        this.antCount = 3;
        this.grasshopperCount = 3;
        this.amountOfMoves = 0;
    }

    public Hive.Player getPlayerEnum() {
        return playerEnum;
    }

    public void madeMove() {
        this.amountOfMoves += 1;
    }

    public Integer getAmountOfMoves() {
        return amountOfMoves;
    }

    public void deductTile(Hive.Tile tile) throws Hive.IllegalMove {

        if (tile.equals(Hive.Tile.QUEEN_BEE)){
            deductQueen();
        } else if (tile.equals(Hive.Tile.SPIDER)) {
            deductSpider();
        } else if (tile.equals(Hive.Tile.BEETLE)) {
            deductBeetle();
        } else if (tile.equals(Hive.Tile.SOLDIER_ANT)) {
            deductAnt();
        } else if (tile.equals(Hive.Tile.GRASSHOPPER)) {
            deductGrasshopper();
        }
    }

    public int getQueenCount() {
        return queenCount;
    }

    private void deductQueen() throws Hive.IllegalMove {
        if (queenCount > 0) {
            this.queenCount -= 1;
        } else {
            throw new Hive.IllegalMove("You don't have any more queens to put on the board");
        }
    }

    public int getSpiderCount() {
        return spiderCount;
    }

    private void deductSpider() throws Hive.IllegalMove {
        if (spiderCount > 0) {
            this.spiderCount -= 1;
        } else {
            throw new Hive.IllegalMove("You don't have any more spiders to put on the board");
        }
    }

    public int getBeetleCount() {
        return beetleCount;
    }

    private void deductBeetle() throws Hive.IllegalMove {
        if (beetleCount > 0) {
            this.beetleCount -= 1;
        } else {
            throw new Hive.IllegalMove("You don't have any more beetles to put on the board");
        }
    }

    public int getAntCount() {
        return antCount;
    }

    private void deductAnt() throws Hive.IllegalMove {
        if (antCount > 0) {
            this.antCount -= 1;
        } else {
            throw new Hive.IllegalMove("You don't have any more ants to put on the board");
        }
    }

    public int getGrasshopperCount() {
        return grasshopperCount;
    }

    private void deductGrasshopper() throws Hive.IllegalMove {
        if (grasshopperCount > 0) {
            this.grasshopperCount -= 1;
        } else {
            throw new Hive.IllegalMove("You don't have any more grasshoppers to put on the board");
        }
    }
}
