package hive.models;

import hive.interfaces.Hive.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;

class Board {

    private HashMap< Pair<Integer, Integer>, BoardTile> boardMap;

    //key value, met key = set (Q, R) en value is de steen met de stack enzo

    Board() {
        boardMap = new HashMap<>();
    }

    void placeStone(Player player, BoardTile tile, Integer q, Integer r) {
        Pair<Integer, Integer> coordinates = Pair.of(q, r);
        boardMap.put(coordinates, tile);
    }

}
