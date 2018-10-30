package hive.models;

import dk.ilios.asciihexgrid.AsciiBoard;
import dk.ilios.asciihexgrid.printers.LargeFlatAsciiHexPrinter;
import hive.interfaces.Hive;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Array;
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

    public Map getBoardMap() {
        return boardMap;
    }

    //alleen boardtile addtostack als de positie al gevuld is, anders moet je wel een nieuwe maken
    //coordinates not in hashmap, behalve als het een beetle is
    //alleen bij de eerste move mag je tegen de tegenstander aan leggen; daarna mag je alleen maar tegen je eigen stenen
    //een nieuwe steen plaatsen
    public void placeStone(PlayerClass playerClass, Hive.Tile tile, Integer q, Integer r) throws Hive.IllegalMove {
        Pair<Integer, Integer> coordinates = Pair.of(q, r);

        if(playerClass.getQueenCount() == 1 && tile != Hive.Tile.QUEEN_BEE && playerClass.getAmountOfMoves() == 3) {
            throw new Hive.IllegalMove("Er moet nu een bij gelegd worden");

        } else if(! areCoordinatesAlreadySet(coordinates)) {
            if (playerClass.getAmountOfMoves() == 0 && playerClass.getPlayerEnum() == Hive.Player.BLACK ) {
                if (getTileNeighbors(Pair.of(q, r)).isEmpty()) {
                    throw new Hive.IllegalMove("Je moet de eerste beurt naast de tegestander leggen");
                }
            } else if (playerClass.getAmountOfMoves() == 0 && playerClass.getPlayerEnum() == Hive.Player.WHITE){
                if (q != 0 || r != 0){
                    throw new Hive.IllegalMove("De eerste zet moet altijd 0, 0 zijn");
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
        //met mier: als neighbor grenst aan een tile, maar niet in een zwerm gaat (5 neighbors), dan mag die
        //het moet een eigen steen zijn
        //als het de laatste steen is in de stack, moet het boardtile object verwijdert worden

        if (currentPlayer.getQueenCount() == 0) {
            Pair<Integer, Integer> oldCoordinate = Pair.of(fromQ, fromR);
            Pair<Integer, Integer> newCoordinate = Pair.of(toQ, toR);
            BoardTile tileToMove = boardMap.get(oldCoordinate);

            ArrayList<Pair<Integer, Integer>> movesForStone = (ArrayList) getMovesPerStone(tileToMove, oldCoordinate);

            System.out.println(movesForStone.toString());

            if (movesForStone.contains(newCoordinate)) {

                if (canTileBeMovedFromOldPlace(tileToMove, currentPlayer) && canTileBePlacedOnNewCoordinates(newCoordinate, tileToMove)) {
                    moveStoneAndDeleteTileIfEmpty(oldCoordinate, newCoordinate);
                } else {
                    throw new Hive.IllegalMove("Tile kan niet verplaasts worden");
                }
            } else {
                throw new Hive.IllegalMove("Move mag niet gemaakt worden");
            }

        } else {
            throw new Hive.IllegalMove("Je moet eerst de queen hebben gelegd voor dat je kan verplaatsen");
        }
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

            for (Map.Entry<Pair<Integer, Integer>, BoardTile> entry : boardMap.entrySet()) {
                Pair<Integer, Integer> coordinates = entry.getKey();
                BoardTile tile = boardMap.get(entry.getKey());
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

    public boolean isQueenSurrounded(PlayerClass player) {
        for (Map.Entry<Pair<Integer, Integer>, BoardTile> entry : boardMap.entrySet()) {
            if (boardMap.get(entry.getKey()).isQueenOfPlayerOnStack(player) && getTileNeighbors(entry.getKey()).size() == 6) {
                return true;
            }
        }
        return false;
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


    private List getMovesPerStone(BoardTile tile, Pair<Integer, Integer> oldCoordinates) throws Hive.IllegalMove {
        ArrayList possibleMoveDirections;

        switch (tile.getTopTileType()) {
            case QUEEN_BEE:
                possibleMoveDirections = (ArrayList) queenBee(getEmptyNeighborDirections(oldCoordinates), oldCoordinates);
                break;
            case BEETLE:
                possibleMoveDirections = (ArrayList) beetle();
                break;
            case SOLDIER_ANT:
                possibleMoveDirections = (ArrayList) workerAnt();
                break;
            case GRASSHOPPER:
                possibleMoveDirections = (ArrayList) grassHopper(getTileNeighbors(oldCoordinates), oldCoordinates);
                break;
            case SPIDER:
                possibleMoveDirections = (ArrayList) spider();
                break;
            default:
                throw new Hive.IllegalMove("Ik heb geen idee wat er gebeurt is");
        }

        possibleMoveDirections.toString();

        return possibleMoveDirections;
    }

    private boolean canTileBeMovedFromOldPlace(BoardTile tileToMove, PlayerClass currentPlayer) {
        if (tileToMove.getTopTileOwner() == currentPlayer && tileToMove.getStackSize() == 2) {
            //het moet een eigen steen zijn en als het een beetle is die op een andere ligt dan mag die wel verplaatst worden
            return true;
        }
        return (tileToMove.getTopTileOwner() == currentPlayer);
        //TODO: hier moet nog gecheckt worden of er een ketting doorbroken wordt
    }

    private boolean canTileBePlacedOnNewCoordinates(Pair<Integer, Integer> newCoordinates, BoardTile tile) {
        boolean canBeMoved = false;

        if (boardMap.get(newCoordinates) == null) {
            canBeMoved = true;

        } else if (tile.getTopTileType() == Hive.Tile.BEETLE && boardMap.get(newCoordinates).getStackSize() == 1) {
            canBeMoved = true;
        }

            //TODO: de tile getneighbors moet ook gebruikt worden, maar niet op deze manier :(
//        } else if (! getTileNeighbors(newCoordinates).isEmpty()) {
//            System.out.println("ja3");
//            canBeMoved = true;

        return canBeMoved;
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

        if (! isHiveIntact(newCoordinates)) {
            boardMap = boardCopy;
            throw new Hive.IllegalMove("De hive is niet Intact meer");
        }
        if (!staysHiveIntact(oldCoordinates, newCoordinates)){
            boardMap = boardCopy;
            throw new Hive.IllegalMove("De hive wordt wel onderbroken maar blijf intact");
        }
    }

    boolean isHiveIntact(Pair<Integer, Integer> newPlace) {
        ArrayList<Pair<Integer, Integer>> neighbors = getTileNeighbors(newPlace);

        if (! neighbors.isEmpty()) {
            Pair<Integer, Integer> tile = neighbors.get(0);
            Set<Pair<Integer, Integer>> marked = dfs(tile, newPlace, new HashSet<>());
            return (marked.size() == (boardMap.size() - 1));

        } else {
            return false;
        }
    }

    public ArrayList<Pair<Integer, Integer>> getTileNeighbors(Pair<Integer, Integer> coordinatesOfCurrent) {
        ArrayList<Pair<Integer, Integer>> tileNeighbors = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> neighbors = (ArrayList<Pair<Integer, Integer>>) getAllNeighbors(coordinatesOfCurrent);

        for (Pair<Integer, Integer> neighbor : neighbors) {
            if (boardMap.containsKey(neighbor)) {
                tileNeighbors.add(neighbor);
            }
        }
        return tileNeighbors;
    }

    public ArrayList<Pair<Integer, Integer>> getEmptyNeighborDirections(Pair<Integer, Integer> coordinatesOfCurrent) {
        ArrayList<Pair<Integer, Integer>> emptyNeighborDirections = new ArrayList<>();

        for (Pair<Integer, Integer> possibleEmptyNeighbor : possibleDirections) {
            Integer newQ = possibleEmptyNeighbor.getKey() + coordinatesOfCurrent.getKey();
            Integer newR = possibleEmptyNeighbor.getValue() + coordinatesOfCurrent.getValue();
            Pair<Integer, Integer> possibleNeighbor = Pair.of(newQ, newR);
            if(! boardMap.containsKey(possibleNeighbor)) {
                emptyNeighborDirections.add(possibleNeighbor);
            }
        }
        return emptyNeighborDirections;
    }

    private boolean areCoordinatesAlreadySet(Pair<Integer, Integer> coordinates) {
        for (Pair key : boardMap.keySet()) {
            if (key.equals(coordinates)) {
                return true;
            }
        }
        return false;
    }

    boolean hasOpponentNeighbor(Pair<Integer, Integer> coordinates, PlayerClass currentPlayer) {
        for (Pair<Integer, Integer> neighgbor : getTileNeighbors(coordinates)) {
            if (boardMap.get(neighgbor).getTopTileOwner() != currentPlayer){
                return true;
            }
        }
        return false;
    }

    private boolean hasOwnNeighbor(Pair<Integer, Integer> coordinates, PlayerClass currentPlayer) {
        for (Pair<Integer, Integer> neighgbor : getTileNeighbors(coordinates)) {
            if (boardMap.get(neighgbor).getTopTileOwner() == currentPlayer){
                return true;
            }
        }
        return false;
    }

    private Set<Pair<Integer, Integer>> dfs(Pair tile, Pair ignore, HashSet<Pair<Integer, Integer>> visited ) {
        visited.add(tile);

        ArrayList<Pair<Integer, Integer>> neighbors = getTileNeighbors(tile);

        for (Pair<Integer, Integer> neighborTile : neighbors){
            if(neighborTile.equals(ignore)) continue;
            if(visited.contains(neighborTile)) continue;
            dfs(neighborTile, ignore, visited);
        }

        return visited;
    }

    public List queenBee(ArrayList<Pair<Integer, Integer>> emptyNeighborDirections, Pair<Integer, Integer> coordinates) {
        //hier moet nog iets in waardoor de bee niet 'door' een gat kan van 1 tile; er moet een minimale opening zijn van 2 tiles
        //twee dezelfde buren

        ArrayList<Pair<Integer, Integer>> placesToMoveTo = new ArrayList<>();

        for (Pair<Integer, Integer> possibleMove : possibleDirections) {
            if (emptyNeighborDirections.contains(possibleMove)) {
                placesToMoveTo.add(possibleMove);
            }
        }

        return directionsToCoordinates(placesToMoveTo, coordinates);
    }

    public List spider() {
        ArrayList list = new ArrayList<>();
        return list;
    }

    public List beetle() {
        return possibleDirections;
    }

    public List workerAnt() {
        // als de te verplaatsen steen, en de plek waar de steen heen gaat dezelfde twee buren heeft, dan mag je er niet in (gat te klein)
        //als de te verplaatsen steen, en de plek waar de steen heen gaat maar een dezelfde buur heeft, dan mag het wel (gat groot genoeg)

        ArrayList list = new ArrayList<>();
        return list;

    }

    public List grassHopper(ArrayList<Pair<Integer, Integer>> getTileNeighbors, Pair<Integer, Integer> coordinates) {

        ArrayList<Pair<Integer, Integer>> possibleMoveDirections = new ArrayList();

        for (Pair<Integer, Integer> direction : possibleDirections) {
            Integer newQ = direction.getKey() + coordinates.getKey();
            Integer newR = direction.getValue() + coordinates.getValue();
            if (getTileNeighbors.contains(Pair.of(newQ, newR))) {
                possibleMoveDirections.add(direction);
            }
        }

        ArrayList list = new ArrayList<>();
        return list;
    }

    private List directionsToCoordinates(List directions, Pair<Integer, Integer> coordinateOfTile) {
        ArrayList<Pair<Integer, Integer>> coordinates = new ArrayList<>();
        for (Pair<Integer, Integer> direction : (ArrayList<Pair<Integer, Integer>>) directions) {
            Integer newQ = coordinateOfTile.getKey() + direction.getKey();
            Integer newR = coordinateOfTile.getValue() + direction.getValue();
            coordinates.add(Pair.of(newQ, newR));
        }

        return coordinates;
    }

    private boolean staysHiveIntact(Pair<Integer, Integer> oldCoordinates, Pair<Integer, Integer> newCoordinates){
        ArrayList<Pair<Integer, Integer>> tileNeighborsOld = getTileNeighbors(oldCoordinates);
        ArrayList<Pair<Integer, Integer>> tileNeigborsNew = getTileNeighbors(newCoordinates);
        System.out.println(oldCoordinates);
        System.out.println(newCoordinates);

        System.out.println("dit zijn de neighbors van de nieuwe tile" + tileNeigborsNew);

        System.out.println("Dit zijn de oude coordinaten " + oldCoordinates);
        for(Pair<Integer, Integer> neigbor : tileNeigborsNew){
            System.out.println("Dit is een neighbor in de forloop" + neigbor);
            if (tileNeighborsOld.contains(neigbor)){
                return true;
            }

        } return false;
    }


}
