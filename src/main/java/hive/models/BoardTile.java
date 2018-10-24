package hive.models;

import hive.interfaces.Hive.*;

import java.util.Stack;

public class BoardTile {

    private Stack<Tile> tileStack = new Stack<>();

    public BoardTile(Tile tile) {
        this.tileStack.add(tile);
    }

    public void addToStack(Tile tile) {
        tileStack.add(tile);
    }

    public Tile removeFromStack() {
        return tileStack.pop();
    }


}
