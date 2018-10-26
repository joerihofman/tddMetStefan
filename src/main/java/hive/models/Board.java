package hive.models;

import hive.interfaces.Hive;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

    private HashMap< Pair<Integer, Integer>, BoardTile> boardMap;
    private ArrayList<Pair<Integer, Integer>> possibleDirections = new ArrayList<>();

    public Board() {
        boardMap = new HashMap<>();

        possibleDirections.add(Pair.of(-1, 0));
        possibleDirections.add(Pair.of(1, 0));
        possibleDirections.add(Pair.of(-1, 1));
        possibleDirections.add(Pair.of(1, -1));
        possibleDirections.add(Pair.of(0, -1));
        possibleDirections.add(Pair.of(0, 1));
    }

    //alleen boardtile addtostack als de positie al gevuld is, anders moet je wel een nieuwe maken
    //coordinates not in hashmap, behalve als het een beetle is
    //daarna een tile maken, of een ding toevoegen als het een beetle is
    public void placeStone(PlayerClass playerClass, Hive.Tile tile, Integer q, Integer r) throws Hive.IllegalMove {
        Pair<Integer, Integer> coordinates = Pair.of(q, r);

        if(! areCoordinatesAlreadySet(coordinates)) {

            BoardTile boardTile = new BoardTile(tile, playerClass);
            playerClass.deductTile(tile);
            boardMap.put(coordinates, boardTile);

        } else if (tile == Hive.Tile.BEETLE) {
            BoardTile boardTile = boardMap.get(coordinates);
            playerClass.deductTile(tile);
            boardTile.addToStack(tile, playerClass);

        } else {
            throw new Hive.IllegalMove("MAG NIET GELEGD WORDEN");
        }
    }

    public void moveStone() throws Hive.IllegalMove {
        throw new Hive.IllegalMove();
    }

    public Integer amountOfTiles(){
        return boardMap.size();
    }

    public ArrayList<Pair<Integer, Integer>> getNeighbors(Pair<Integer, Integer> coordinatesOfCurrent) {
        ArrayList<Pair<Integer, Integer>> neighbors = new ArrayList<>();

        for (Pair<Integer, Integer> possibleNeighbors : possibleDirections) {
            Integer newQ = possibleNeighbors.getKey() + coordinatesOfCurrent.getKey();
            Integer newR = possibleNeighbors.getValue() + coordinatesOfCurrent.getValue();
            neighbors.add(Pair.of(newQ, newR));
        }

        return neighbors;
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
