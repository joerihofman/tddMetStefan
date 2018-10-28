package hive.models;

import dk.ilios.asciihexgrid.AsciiBoard;
import dk.ilios.asciihexgrid.printers.LargeFlatAsciiHexPrinter;
import hive.interfaces.Hive;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

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

    public HashMap<Pair<Integer, Integer>, BoardTile> getBoardMap() {
        return boardMap;
    }

    //alleen boardtile addtostack als de positie al gevuld is, anders moet je wel een nieuwe maken
    //coordinates not in hashmap, behalve als het een beetle is
    //alleen bij de eerste move mag je tegen de tegenstander aan leggen; daarna mag je alleen maar tegen je eigen stenen
    //een nieuwe steen plaatsen
    public void placeStone(PlayerClass playerClass, Hive.Tile tile, Integer q, Integer r) throws Hive.IllegalMove {
        Pair<Integer, Integer> coordinates = Pair.of(q, r);

        if(playerClass.getQueenCount() == 1 && tile != Hive.Tile.QUEEN_BEE && playerClass.getAmountOfMoves() == 3){
            throw new Hive.IllegalMove("Er moet nu een bij gelegd worden");

        } else if(! areCoordinatesAlreadySet(coordinates)) {
            if (playerClass.getAmountOfMoves() == 0 && playerClass.getPlayerEnum() == Hive.Player.BLACK ) {
                if(getTileNeighbors(Pair.of(q, r)).isEmpty()) {
                    throw  new Hive.IllegalMove("Je moet de eerste beurt naast de tegestander leggen");
                }
            } else if (playerClass.getAmountOfMoves() > 0) {
                if (hasOpponentNeighbor(coordinates, playerClass)) {
                    throw new Hive.IllegalMove("Er mag niet naast de tegenstander gelegd worden");
                } else if (!hasOwnNeighbor(coordinates, playerClass)) {
                    throw new Hive.IllegalMove("Er moet naast een eigen steen gelegd worden");
                }
            }
            BoardTile boardTile = new BoardTile(tile, playerClass);
            playerClass.deductTile(tile);
            boardMap.put(coordinates, boardTile);
            playerClass.madeMove();

        } else {
            throw new Hive.IllegalMove("Dit mag niet");
        }
    }

    public void moveStone(PlayerClass currentPlayer, Integer fromQ, Integer fromR, Integer toQ, Integer toR) throws Hive.IllegalMove {
        //er mag niks gesplitst worden (kettingen doorbreken)
        //met mier: als neighbor grenst aan een tile, maar niet in een zwerm gaat (5 neighbors), dan mag die
        //het moet een eigen steen zijn
        //als het de laatste steen is in de stack, moet het boardtile object verwijdert worden

        if (currentPlayer.getQueenCount() == 0) {
            Pair<Integer, Integer> oldCoordinate = Pair.of(fromQ, fromR);
            Pair<Integer, Integer> newCoordinate = Pair.of(toQ, toR);
            BoardTile tileToMove = boardMap.get(oldCoordinate);



            if (canTileBeMovedFromOldPlace(tileToMove, oldCoordinate, currentPlayer) && canTileBePlacedOnNewCoordinates(newCoordinate, tileToMove)) {
                moveStoneAndDeleteTileIfEmpty(oldCoordinate, newCoordinate);
            } else {
                throw new Hive.IllegalMove("Tile kan niet verplaasts worden");
            }
        } else {
            throw new Hive.IllegalMove("Je moet eerst de queen gelegd hebben voor dat je kan verplaatsen");
        }
    }

    public boolean isHiveIntact(Pair<Integer, Integer> newPlace) {


        ArrayList<Pair<Integer, Integer>> neighbors = getTileNeighbors(newPlace);

        if (neighbors.isEmpty()) {
            return false;
        }

        Pair<Integer, Integer> tile = neighbors.get(0);

        Set<Pair<Integer, Integer>> marked = dfs(tile, newPlace, new HashSet<Pair<Integer, Integer>>());
        if (marked.size() != boardMap.size() - 1) {
            System.out.println(marked.size());
            System.out.println(boardMap.size());
            return false;
        }
        return true;
    }

    public void printBoard() {
        if (! boardMap.isEmpty()) {
            int minQ = 0;
            int minR = 0;

            for (Pair<Integer, Integer> coordinates : boardMap.keySet()) {
                if (coordinates.getKey() < minQ) {
                    minQ = coordinates.getKey();
                }

                if (coordinates.getValue() < minR) {
                    minR = coordinates.getValue();
                }
            }

            AsciiBoard asciiBoard = new AsciiBoard(0, 30, 0, 30, new LargeFlatAsciiHexPrinter());

            for (Pair<Integer, Integer> coordinates : boardMap.keySet()) {
                BoardTile tile = boardMap.get(coordinates);
                String tileType = tile.getTopTileType().toString();
                String owner = tile.getTopTileOwner().getPlayerEnum().toString();
                String bottomString = tileType + " " + owner;
                asciiBoard.printHex(coordinates.toString(), bottomString, ' ', coordinates.getKey() - minQ, coordinates.getValue() - minR);
            }

            System.out.println(asciiBoard.prettPrint(true));
        } else {
            System.out.println("BOARD IS EMPTY MY DUDE");
        }
    }

    Integer amountOfTiles(){
        return boardMap.size();
    }

    List<Pair<Integer, Integer>> getAllNeighbors(Pair<Integer, Integer> coordinatesOfCurrent) {
        ArrayList<Pair<Integer, Integer>> neighbors = new ArrayList<>();

        for (Pair<Integer, Integer> possibleNeighbors : possibleDirections) {
            Integer newQ = possibleNeighbors.getKey() + coordinatesOfCurrent.getKey();
            Integer newR = possibleNeighbors.getValue() + coordinatesOfCurrent.getValue();
            neighbors.add(Pair.of(newQ, newR));
        }

        return neighbors;
    }

    private boolean canTileBeMovedFromOldPlace(BoardTile tileToMove, Pair<Integer, Integer> coordinates, PlayerClass currentPlayer) {
        if (tileToMove.getTopTileOwner() == currentPlayer && tileToMove.getStackSize() == 2) {
            //het moet een eigen steen zijn en als het een beetle is die op een andere ligt dan mag die wel verplaatst worden
            return true;
        }
        return (tileToMove.getTopTileOwner() == currentPlayer);
        //TODO: hier moet nog gecheckt worden of er een ketting doorbroken wordt
    }

    private boolean canTileBePlacedOnNewCoordinates(Pair<Integer, Integer> newCoordinates, BoardTile tile) {
        boolean magGeplaatstWorden = false;

        if (boardMap.get(newCoordinates) == null) {
            magGeplaatstWorden = true;

        } else if (tile.getTopTileType() == Hive.Tile.BEETLE && boardMap.get(newCoordinates).getStackSize() == 1) {
            magGeplaatstWorden = true;
        }

            //TODO: de tile getneighbors moet ook gebruikt worden, maar niet op deze manier :(
//        } else if (! getTileNeighbors(newCoordinates).isEmpty()) {
//            System.out.println("ja3");
//            magGeplaatstWorden = true;

        return magGeplaatstWorden;
    }

    private void moveStoneAndDeleteTileIfEmpty(Pair<Integer, Integer> oldCoordinates, Pair<Integer, Integer> newCoordinates) throws Hive.IllegalMove {
        BoardTile boardTileToMoveFrom = boardMap.get(oldCoordinates);
        Pair<Hive.Tile, PlayerClass> tileToBeMoved = boardTileToMoveFrom.removeTopTile();
        BoardTile newTile;

        HashMap< Pair<Integer, Integer>, BoardTile> boardCopy = boardMap;


        if (boardMap.get(newCoordinates) == null) {
            newTile = new BoardTile(tileToBeMoved.getKey(), tileToBeMoved.getValue());
        } else {
            newTile = boardMap.get(newCoordinates);
        }

        boardMap.put(newCoordinates, newTile);

        if (boardTileToMoveFrom.getStackSize() == 0) {
            boardMap.remove(oldCoordinates);
        }

        if (!isHiveIntact(newCoordinates)){
            boardMap = boardCopy;
            throw new Hive.IllegalMove("De hive wordt onderbroken");
        }

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

    boolean hasOpponentNeighbor(Pair<Integer, Integer> coordinates, PlayerClass currentPlayer){
        for (Pair<Integer, Integer> neighgbor : getTileNeighbors(coordinates)) {
            if (boardMap.get(neighgbor).getTopTileOwner() != currentPlayer){
                return true;
            }
        }
        return false;
    }

    private boolean hasOwnNeighbor(Pair<Integer, Integer> coordinates, PlayerClass currentPlayer){
        for (Pair<Integer, Integer> neighgbor : getTileNeighbors(coordinates)) {
            if (boardMap.get(neighgbor).getTopTileOwner() == currentPlayer){
                return true;
            }
        }
        return false;
    }


    private Set< Pair<Integer, Integer>> dfs(Pair<Integer, Integer> tile, Pair<Integer, Integer> ignore, HashSet<Pair<Integer, Integer>> visited  ){
        visited.add(tile);

        ArrayList<Pair<Integer, Integer>> neighbors = getTileNeighbors(tile);

        for (Pair<Integer, Integer> t : neighbors){
            if(t.equals(ignore)) continue;
            if(visited.contains(t)) continue;
            dfs(t, ignore, visited);
        }

        return visited;
    }

}
