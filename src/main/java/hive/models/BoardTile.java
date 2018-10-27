package hive.models;

import hive.interfaces.Hive.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Stack;

public class BoardTile {

    private Stack<Pair<Tile, PlayerClass>> tileStack = new Stack<>();

    public BoardTile(Tile tile, PlayerClass playerClass) {
        this.tileStack.add(makePair(tile, playerClass));
    }

    public void addToStack(Tile tile, PlayerClass playerClass) {
        tileStack.add(makePair(tile, playerClass));
    }

    public Tile getTopTile() {
        return tileStack.peek().getKey();
    }

    public Pair<Tile, PlayerClass> removeTopTile() {
        return tileStack.pop();
    }

    public Integer getStackSize() {
        return tileStack.size();
    }

    private Pair makePair(Tile tileface, PlayerClass playerClass) {
        return Pair.of(tileface, playerClass);
    }

}
