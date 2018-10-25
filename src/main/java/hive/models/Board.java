package hive.models;

import hive.interfaces.Hive;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Board {

    private HashMap< Pair<Integer, Integer>, BoardTile> boardMap;

    public Board() {
        boardMap = new HashMap<>();
    }

    public void placeStone(PlayerClass playerClass, Hive.Tile tile, Integer q, Integer r) throws Hive.IllegalMove {
        Pair<Integer, Integer> coordinates = Pair.of(q, r);

        if(! areCoordinatesAlreadySet(coordinates)) {

            if (tile.equals(Hive.Tile.BEETLE) && areCoordinatesAlreadySet(coordinates)) {
                BoardTile boardTile = boardMap.get(coordinates);

                //alleen boardtile addtostack als de positie al gevuld is, anders moet je wel een nieuwe maken
                boardMap.put(coordinates, boardTile);
                boardTile.addToStack(tile, playerClass);
                playerClass.deductTile(tile);

            } else {
                //coordinates not in hashmap, behalve als het een beetle is
                //daarna een tile maken, of een ding toevoegen als het een beetle is
                BoardTile boardTile = new BoardTile(tile, playerClass);

                boardMap.put(coordinates, boardTile);
                playerClass.deductTile(tile);
            }
        } else {
            throw new Hive.IllegalMove("MAG NIET");
        }
    }

    private boolean areCoordinatesAlreadySet(Pair<Integer, Integer> coordinates) {

        for (Pair key : boardMap.keySet()) {
            if (key.equals(coordinates)) {
                return true;
            }
        }

        return false;
    }

}
