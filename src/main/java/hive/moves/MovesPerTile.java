package hive.moves;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class MovesPerTile {

    public List queenBee() {
        //hier moet nog iets in waardoor de bee niet 'door' een gat kan van 1 tile; er moet een minimale opening zijn van 2 tiles
        return allAround();
    }

    public void spider() {

    }

    public List beetle() {
        return allAround();
    }

    public void workerAnt() {
        // als de te verplaatsen steen, en de plek waar de steen heen gaat dezelfde twee buren heeft, dan mag je er niet in (gat te klein)
        //als de te verplaatsen steen, en de plek waar de steen heen gaat maar een dezelfde buur heeft, dan mag het wel (gat groot genoeg)

    }

    public void grassHopper() {

    }

    private List allAround() {
        ArrayList<Pair<Integer, Integer>> possibleDirections = new ArrayList<>();

        possibleDirections.add(Pair.of(-1, 0));
        possibleDirections.add(Pair.of(1, 0));
        possibleDirections.add(Pair.of(-1, 1));
        possibleDirections.add(Pair.of(1, -1));
        possibleDirections.add(Pair.of(0, -1));
        possibleDirections.add(Pair.of(0, 1));

        return possibleDirections;
    }
}
