package hive.models;

import hive.interfaces.Hive.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class BoardTile {

    private Stack<Pair<Tile, PlayerClass>> tileStack = new Stack<>();

    public BoardTile(Tile tile, PlayerClass playerClass) {
        this.tileStack.add(makePair(tile, playerClass));
    }

    public void addToStack(Tile tile, PlayerClass playerClass) {
        tileStack.add(makePair(tile, playerClass));
    }

    public Tile getTopTileType() {
        return tileStack.peek().getKey();
    }

    public PlayerClass getTopTileOwner() {
        return tileStack.peek().getValue();
    }

    public Pair<Tile, PlayerClass> removeTopTile() {
        return tileStack.pop();
    }

    public Integer getStackSize() {
        return tileStack.size();
    }

    public boolean isQueenOfOpponentOnStack(PlayerClass player) {
        Iterator iterator = tileStack.iterator();

        while (iterator.hasNext()) {
            Pair<Tile, PlayerClass> tile = (Pair<Tile, PlayerClass>) iterator.next();
            if (tile.getValue() != player && tile.getKey() == Tile.QUEEN_BEE) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Pair<Tile, PlayerClass>> tilesOnStackToArray() {
        ArrayList<Pair<Tile, PlayerClass>> arrayList = new ArrayList<>();
        Iterator iterator = tileStack.iterator();

        while (iterator.hasNext()) {
            arrayList.add((Pair<Tile, PlayerClass>) iterator.next());
        }

        return arrayList;
    }

    public String tilesOnStackToString() {
        Iterator iterator = tileStack.iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            Pair<Tile, PlayerClass> pair = (Pair<Tile, PlayerClass>) iterator.next();
            stringBuilder.append(tileStack.indexOf(pair))
                    .append(' ')
                    .append(pair.getKey().toString())
                    .append(" - ")
                    .append(pair.getValue().getPlayerEnum().toString());
        }
        return stringBuilder.toString();
    }

    private Pair makePair(Tile tileface, PlayerClass playerClass) {
        return Pair.of(tileface, playerClass);
    }

}
