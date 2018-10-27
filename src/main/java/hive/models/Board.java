package hive.models;

import hive.interfaces.Hive;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public void moveStone(PlayerClass currentPlayer, Integer fromQ, Integer fromR, Integer toQ, Integer toR) throws Hive.IllegalMove {
        //er mag niks gesplitst worden (kettingen doorbreken)
        //met mier: als neighbor grenst aan een tile, maar niet in een zwerm gaat (5 neighbors), dan mag die
        //het moet een eigen steen zijn

        if (currentPlayer.getQueenCount() == 0) {

            Pair<Integer, Integer> oldCoordinate = Pair.of(fromQ, fromR);
            BoardTile tileToMove = boardMap.get(oldCoordinate);
            Pair<Hive.Tile, PlayerClass> topOfStack = tileToMove.removeTopTile();

            if (canTileBeMoved(tileToMove, oldCoordinate, currentPlayer)) {

                Pair<Integer, Integer> newCoordinate = Pair.of(toQ, toR);
                System.out.println("IK WEET HET NIET MEER");
            }
        }
        throw new Hive.IllegalMove();
    }

    public Integer amountOfTiles(){
        return boardMap.size();
    }

    public List<Pair<Integer, Integer>> getAllNeighbors(Pair<Integer, Integer> coordinatesOfCurrent) {
        ArrayList<Pair<Integer, Integer>> neighbors = new ArrayList<>();

        for (Pair<Integer, Integer> possibleNeighbors : possibleDirections) {
            Integer newQ = possibleNeighbors.getKey() + coordinatesOfCurrent.getKey();
            Integer newR = possibleNeighbors.getValue() + coordinatesOfCurrent.getValue();
            neighbors.add(Pair.of(newQ, newR));
        }

        return neighbors;
    }

    public boolean canTileBeMoved(BoardTile tileToMove, Pair<Integer, Integer> coordinates, PlayerClass currentPlayer) {
        if (tileToMove.getTopTileOwner() == currentPlayer) { //het moet een eigen steen zijn
            if (tileToMove.getStackSize() == 1) { //als het een beetle is die er op licht dan mag die wel verplaatst worden
                return true;
            }
        }
        return (getTileNeighbors(coordinates).size() == 1); //als de steen maar een buur heeft mag die ook verplaatsen
    }

    private ArrayList<Pair<Integer, Integer>> getTileNeighbors(Pair<Integer, Integer> coordinatesOfCurrent) {
        ArrayList<Pair<Integer, Integer>> tileNeighbors = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> neighbors = (ArrayList<Pair<Integer, Integer>>) getAllNeighbors(coordinatesOfCurrent);

        for (Pair<Integer, Integer> neighbor : neighbors) {
            if (boardMap.containsKey(neighbor)) {
                tileNeighbors.add(neighbor);
            }
        }

        return tileNeighbors;
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
