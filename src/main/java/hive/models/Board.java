package hive.models;

import dk.ilios.asciihexgrid.AsciiBoard;
import dk.ilios.asciihexgrid.printers.LargeFlatAsciiHexPrinter;
import hive.interfaces.Hive;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class Board {

    private Map< Hex, BoardTile> boardMap;
    private List<Hex> possibleDirections = new ArrayList<>();

    public Board() {
        boardMap = new HashMap<>();

        possibleDirections.add(new Hex(-1, 0));
        possibleDirections.add(new Hex(1, 0));
        possibleDirections.add(new Hex(-1, 1));
        possibleDirections.add(new Hex(1, -1));
        possibleDirections.add(new Hex(0, -1));
        possibleDirections.add(new Hex(0, 1));
    }

    public Map getBoardMap() {
        return boardMap;
    }

    public void placeStone(PlayerClass playerClass, Hive.Tile tile, Integer q, Integer r) throws Hive.IllegalMove {
        Hex coordinates = new Hex(q, r);

        if(playerClass.getQueenCount() == 1 && tile != Hive.Tile.QUEEN_BEE && playerClass.getAmountOfMoves() == 3) {
            throw new Hive.IllegalMove("Er moet nu een bij gelegd worden");

        } else if(! areCoordinatesAlreadySet(coordinates)) {
            if (playerClass.getAmountOfMoves() == 0 && playerClass.getPlayerEnum() == Hive.Player.BLACK ) {
                if (getTileNeighbors(new Hex(q, r)).isEmpty()) {
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

        if (currentPlayer.getQueenCount() == 0) {
            Hex oldCoordinate = new Hex(fromQ, fromR);
            Hex newCoordinate = new Hex(toQ, toR);
            BoardTile tileToMove = boardMap.get(oldCoordinate);

            List<Hex> movesForStone = (ArrayList) getMovesPerStone(tileToMove, oldCoordinate);

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

            for (Hex coordinates : boardMap.keySet()) {
                if (coordinates.getKey() < minQ) {
                    minQ = coordinates.getKey();
                }

                if (coordinates.getValue() < minR) {
                    minR = coordinates.getValue();
                }
            }

            AsciiBoard asciiBoard = new AsciiBoard(0, 30, 0, 30, new LargeFlatAsciiHexPrinter());

            for (Map.Entry<Hex, BoardTile> entry : boardMap.entrySet()) {
                Hex coordinates = entry.getKey();
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
        for (Map.Entry<Hex, BoardTile> entry : boardMap.entrySet()) {
            if (boardMap.get(entry.getKey()).isQueenOfPlayerOnStack(player) && getTileNeighbors(entry.getKey()).size() == 6) {
                return true;
            }
        }
        return false;
    }

    Integer amountOfTiles(){
        return boardMap.size();
    }

    List<Hex> getAllNeighbors(Hex coordinatesOfCurrent) {
        List<Hex> neighbors = new ArrayList<>();

        for (Hex possibleNeighbors : possibleDirections) {
            Integer newQ = possibleNeighbors.getKey() + coordinatesOfCurrent.getKey();
            Integer newR = possibleNeighbors.getValue() + coordinatesOfCurrent.getValue();
            neighbors.add(new Hex(newQ, newR));
        }

        return neighbors;
    }


    private List getMovesPerStone(BoardTile tile, Hex oldCoordinates) throws Hive.IllegalMove {
        List possibleMoveDirections;

        switch (tile.getTopTileType()) {
            case QUEEN_BEE:
                possibleMoveDirections = queenBee(getEmptyNeighborDirections(oldCoordinates), oldCoordinates);
                break;
            case BEETLE:
                possibleMoveDirections = beetle(getAllNeighbors(oldCoordinates), oldCoordinates, tile);
                break;
            case SOLDIER_ANT:
                possibleMoveDirections = soldierAnt(oldCoordinates);
                break;
            case GRASSHOPPER:
                possibleMoveDirections = grassHopper(getTileNeighbors(oldCoordinates), oldCoordinates);
                break;
            case SPIDER:
                possibleMoveDirections = spider(oldCoordinates);
                break;
            default:
                throw new Hive.IllegalMove("Ik heb geen idee wat er gebeurt is");
        }

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

    private boolean canTileBePlacedOnNewCoordinates(Hex newCoordinates, BoardTile tile) {
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

    private void moveStoneAndDeleteTileIfEmpty(Hex oldCoordinates, Hex newCoordinates) throws Hive.IllegalMove {
        BoardTile boardTileToMoveFrom = boardMap.get(oldCoordinates);
        Pair<Hive.Tile, PlayerClass> tileToBeMoved = boardTileToMoveFrom.removeTopTile();

        Map< Hex, BoardTile> boardCopy = boardMap;

        //TODO: deze werkt niet met de test 'slideCanBeDoneWithBeetle' in BoardTests
//        if (tileToBeMoved.getKey() != Hive.Tile.GRASSHOPPER && ! hiveStaysIntactWhileMoving(oldCoordinates, newCoordinates)){
//            throw new Hive.IllegalMove("De hive wordt wel onderbroken maar blijf intact");
//        }

        if (boardMap.get(newCoordinates) == null) {
            BoardTile newTile = new BoardTile(tileToBeMoved.getKey(), tileToBeMoved.getValue());
            boardMap.put(newCoordinates, newTile);
        } else {
            BoardTile tile = boardMap.get(newCoordinates);
            tile.addToStack(tileToBeMoved.getKey(), tileToBeMoved.getValue());
        }


        if (boardTileToMoveFrom.getStackSize() == 0) {
            boardMap.remove(oldCoordinates);
        }

        if (! isHiveIntactAfterMove(newCoordinates)) {
            boardMap = boardCopy;
            throw new Hive.IllegalMove("De hive is niet Intact meer");
        }
    }

    boolean isHiveIntactAfterMove(Hex newPlace) {
        List<Hex> neighbors = getTileNeighbors(newPlace);

        if (! neighbors.isEmpty()) {
            Hex tile = neighbors.get(0);
            Set<Hex> marked = dfs(tile, newPlace, new HashSet<>());
            return (marked.size() == (boardMap.size() - 1));

        } else {
            return false;
        }
    }

    public List<Hex> getTileNeighbors(Hex coordinatesOfCurrent) {
        List<Hex> tileNeighbors = new ArrayList<>();
        List<Hex> neighbors = getAllNeighbors(coordinatesOfCurrent);

        for (Hex neighbor : neighbors) {
            if (boardMap.containsKey(neighbor)) {
                tileNeighbors.add(neighbor);
            }
        }
        return tileNeighbors;
    }

    public List<Hex> getEmptyNeighbors(Hex coordinatesOfCurrent) {
        ArrayList<Hex> emptyNeighbors = new ArrayList<>();
        ArrayList<Hex> neighbors = (ArrayList<Hex>) getAllNeighbors(coordinatesOfCurrent);

        for (Hex neighbor : neighbors) {
            if (! boardMap.containsKey(neighbor)) {
                emptyNeighbors.add(neighbor);
            }
        }
        return emptyNeighbors;
    }

    public List<Hex> getEmptyNeighborDirections(Hex coordinatesOfCurrent) {
        List<Hex> emptyNeighborDirections = new ArrayList<>();

        for (Hex possibleEmptyNeighbor : possibleDirections) {
            Integer newQ = possibleEmptyNeighbor.getKey() + coordinatesOfCurrent.getKey();
            Integer newR = possibleEmptyNeighbor.getValue() + coordinatesOfCurrent.getValue();
            Hex possibleNeighbor = new Hex(newQ, newR);

            if(! boardMap.containsKey(possibleNeighbor)) {
                emptyNeighborDirections.add(possibleNeighbor);
            }
        }
        return emptyNeighborDirections;
    }

    private boolean areCoordinatesAlreadySet(Hex coordinates) {
        for (Hex key : boardMap.keySet()) {
            if (key.equals(coordinates)) {
                return true;
            }
        }
        return false;
    }

    boolean hasOpponentNeighbor(Hex coordinates, PlayerClass currentPlayer) {
        for (Hex neighgbor : getTileNeighbors(coordinates)) {
            if (boardMap.get(neighgbor).getTopTileOwner() != currentPlayer){
                return true;
            }
        }
        return false;
    }

    private boolean hasOwnNeighbor(Hex coordinates, PlayerClass currentPlayer) {
        for (Hex neighgbor : getTileNeighbors(coordinates)) {
            if (boardMap.get(neighgbor).getTopTileOwner() == currentPlayer){
                return true;
            }
        }
        return false;
    }

    private Set<Hex> dfs(Hex tile, Hex ignore, Set<Hex> visited ) {
        visited.add(tile);

        List<Hex> neighbors = getTileNeighbors(tile);

        for (Hex neighborTile : neighbors){
            if(neighborTile.equals(ignore)) continue;
            if(visited.contains(neighborTile)) continue;
            dfs(neighborTile, ignore, visited);
        }

        return visited;
    }

    private List queenBee(List emptyNeighborDirections, Hex coordinates) {
        //hier moet nog iets in waardoor de bee niet 'door' een gat kan van 1 tile; er moet een minimale opening zijn van 2 tiles
        //twee dezelfde buren

        List<Hex> possibleMoves = new ArrayList<>();

        for (Hex possibleMove : possibleDirections) {
            if (emptyNeighborDirections.contains(possibleMove) && canTileBeMovedInGap(coordinates, possibleMove)) {
                possibleMoves.add(possibleMove);
            }
        }

        return directionsToCoordinates(possibleMoves, coordinates);
    }

    public List spider(Hex coordinates) {
        ArrayList<Hex> movesList = new ArrayList<>();
        movesList.addAll(recursiveForEmptyPlaces(new HashSet<>(), coordinates, 0, coordinates, true));

        return movesList;
    }


    private List beetle(List<Hex> allNeighbors, Hex coordinates, BoardTile tile) {

        List<Hex> possibleMoves = new ArrayList<>();

        for (Hex neighbor : allNeighbors) {
            if (boardMap.get(neighbor) == null && canTileBeMovedInGap(coordinates, neighbor)) {
                possibleMoves.add(neighbor);
            } else if (boardMap.get(neighbor) != null && boardMap.get(neighbor).getStackSize() == tile.getStackSize()) {
                possibleMoves.add(neighbor);
            }
        }

        return possibleMoves;
    }

    private List soldierAnt(Hex coordinates) {
        ArrayList<Hex> movesList = new ArrayList<>();
        movesList.addAll(recursiveForEmptyPlaces(new HashSet<>(), coordinates, 0, coordinates, false));

        return movesList;
    }

    private Set<Hex> recursiveForEmptyPlaces(Set<Hex> visited, Hex currentPlace, int recursionDepth, Hex originalLocation, boolean isSpider) {
        Integer maxRecursion = Integer.MAX_VALUE;

        if (isSpider) {
            maxRecursion = 3;
        }

        if (recursionDepth < maxRecursion) {
            recursionDepth = recursionDepth + 1;
            for (Hex neighborOfCurrent : getEmptyNeighbors(currentPlace)) {
                List<Hex> tilesOfNeighborOfCurrent = getTileNeighbors(neighborOfCurrent);
                if (! tilesOfNeighborOfCurrent.isEmpty() && !visited.contains(neighborOfCurrent)) {
                    if (!(tilesOfNeighborOfCurrent.size() == 1 && tilesOfNeighborOfCurrent.contains(originalLocation)) && canTileBeMovedInGap(currentPlace, neighborOfCurrent)) {
                        visited.add(neighborOfCurrent);
                        recursiveForEmptyPlaces(visited, neighborOfCurrent, recursionDepth, originalLocation, isSpider);
                    }
                }
            }
        }
        return visited;
    }

    private List grassHopper(List<Hex> tileNeighbors, Hex coordinates) {

        List<Hex> possibleMoveDirections = new ArrayList();
        List<Hex> endCoordinates = new ArrayList<>();

        for (Hex direction : possibleDirections) {
            Integer newQ = direction.getKey() + coordinates.getKey();
            Integer newR = direction.getValue() + coordinates.getValue();
            if (tileNeighbors.contains(new Hex(newQ, newR))) {
                possibleMoveDirections.add(direction);
            }
        }

        for (Hex possibleDirection : possibleMoveDirections) {
            endCoordinates.add(findEmptyTileInDirection(possibleDirection, coordinates));
        }

        return endCoordinates;
    }

    private Hex findEmptyTileInDirection(Hex direction, Hex startPosition) {
        Integer newQ = direction.getKey() + startPosition.getKey();
        Integer newR = direction.getValue() + startPosition.getValue();
        Hex newCoord = new Hex(newQ, newR);
        if (boardMap.containsKey(newCoord)) {
            newCoord = findEmptyTileInDirection(direction, newCoord);
        }

        return newCoord;
    }

    public boolean canTileBeMovedInGap(Hex currentLocation, Hex emptyNeighbor) {
        List<Hex> usedTileNeighbors = getTileNeighbors(currentLocation);
        List<Hex> usedTileNeighborsEmptyNeighbor = getTileNeighbors(emptyNeighbor);

        int neighborCounter = 0;

        for (Hex usedTile : usedTileNeighbors) {
            if (usedTileNeighborsEmptyNeighbor.contains(usedTile)) {
                neighborCounter++;
            }
        }
        return (neighborCounter < 2);
    }

    private List directionsToCoordinates(List directions, Hex coordinateOfTile) {
        List<Hex> coordinates = new ArrayList<>();
        for (Hex direction : (ArrayList<Hex>) directions) {
            Integer newQ = coordinateOfTile.getKey() + direction.getKey();
            Integer newR = coordinateOfTile.getValue() + direction.getValue();
            coordinates.add(new Hex(newQ, newR));
        }

        return coordinates;
    }

    private boolean hiveStaysIntactWhileMoving(Hex oldCoordinates, Hex newCoordinates){
        List<Hex> tileNeighborsOld = getTileNeighbors(oldCoordinates);
        List<Hex> tileNeighborsNew = getTileNeighbors(newCoordinates);
        for(Hex neigbor : tileNeighborsNew){
            if (tileNeighborsOld.contains(neigbor)){
                return true;
            }
            else if (tileNeighborsOld.contains(newCoordinates)){
                return true;
            }

        } return false;
    }

}
