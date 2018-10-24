package hive.models;

import hive.BelowZeroException;
import hive.interfaces.Hive;

public class Player {

    private Hive.Player playerEnum;
    private int queenCount;
    private int spiderCount;
    private int beetleCount;
    private int antCount;
    private int grasshopperCount;

    public Player(Hive.Player playerEnum) {
        this.playerEnum = playerEnum;
        this.queenCount = 1;
        this.spiderCount = 2;
        this.beetleCount = 2;
        this.antCount = 3;
        this.grasshopperCount = 3;
    }

    public Hive.Player getPlayerEnum() {
        return playerEnum;
    }

    public int getQueenCount() {
        return queenCount;
    }

    public void deductQueen() throws BelowZeroException {
        if (queenCount > 0) {
            this.queenCount -= 1;
        } else {
            throw new BelowZeroException("You don't have any more queens to put on the board");
        }
    }

    public int getSpiderCount() {
        return spiderCount;
    }

    public void deductSpider() throws BelowZeroException {
        if (spiderCount > 0) {
            this.spiderCount -= 1;
        } else {
            throw new BelowZeroException("You don't have any more spiders to put on the board");
        }
    }

    public int getBeetleCount() {
        return beetleCount;
    }

    public void deductBeetle() throws BelowZeroException {
        if (beetleCount > 0) {
            this.beetleCount -= 1;
        } else {
            throw new BelowZeroException("You don't have any more beetles to put on the board");
        }
    }

    public int getAntCount() {
        return antCount;
    }

    public void deductAnt() throws BelowZeroException {
        if (antCount > 0) {
            this.antCount -= 1;
        } else {
            throw new BelowZeroException("You don't have any more ants to put on the board");
        }
    }

    public int getGrasshopperCount() {
        return grasshopperCount;
    }

    public void deductGrasshopper() throws BelowZeroException {
        if (grasshopperCount > 0) {
            this.grasshopperCount -= 1;
        } else {
            throw new BelowZeroException("You don't have any more grasshoppers to put on the board");
        }
    }
}
