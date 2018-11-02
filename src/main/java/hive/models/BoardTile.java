package hive.models;

import hive.game.PlayerClass;
import hive.interfaces.Hive.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Iterator;
import java.util.Stack;

public class BoardTile {

    private Stack<Pair<Tile, PlayerClass>> tileStack = new Stack<>();

    public BoardTile(Tile tile, PlayerClass playerClass) {
        this.tileStack.add(makePair(tile, playerClass));
    }

    void addToStack(Tile tile, PlayerClass playerClass) {
        tileStack.add(makePair(tile, playerClass));
    }

    Tile getTopTileType() {
        return tileStack.peek().getKey();
    }

    PlayerClass getTopTileOwner() {
        return tileStack.peek().getValue();
    }

    Pair<Tile, PlayerClass> removeTopTile() {
        return tileStack.pop();
    }

    Integer getStackSize() {
        return tileStack.size();
    }

    boolean isQueenOfOpponentOnStack(PlayerClass player) {
        Iterator iterator = tileStack.iterator();

        while (iterator.hasNext()) {
            Pair<Tile, PlayerClass> tile = (Pair<Tile, PlayerClass>) iterator.next();
            if (tile.getValue() != player && tile.getKey() == Tile.QUEEN_BEE) {
                return true;
            }
        }
        return false;
    }

    private Pair makePair(Tile tileface, PlayerClass playerClass) {
        return Pair.of(tileface, playerClass);
    }

}
