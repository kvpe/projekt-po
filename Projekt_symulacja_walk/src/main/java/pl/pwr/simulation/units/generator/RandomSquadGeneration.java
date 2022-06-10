package pl.pwr.simulation.units.generator;

import pl.pwr.simulation.map.Coordinates;
import pl.pwr.simulation.units.Squad;

import java.io.IOException;

import static pl.pwr.simulation.map.RandomGenerator.random;

public class RandomSquadGeneration  {

    public Squad randomSquad(int maxSquadSize, int mapSize, int id, Coordinates coordinates) throws IOException {
        Names names = new Names();
        String name = names.getNames1().get(random.nextInt(names.getNames1().size())).toUpperCase() + " " + names.getNames2().get(random.nextInt(names.getNames2().size())).toUpperCase();
        int foodQuantity = random.nextInt(maxSquadSize * 10) + maxSquadSize * 5;
        maxSquadSize -= 3;
        int soldiersQuantity = random.nextInt(maxSquadSize) + 1;
        maxSquadSize -= (soldiersQuantity -1);
        int archersQuantity = random.nextInt(maxSquadSize) + 1;
        maxSquadSize -= (archersQuantity - 1);
        int horsemenQuantity = random.nextInt(maxSquadSize) + 1;
        return new Squad(archersQuantity, soldiersQuantity, horsemenQuantity, foodQuantity, name, id, mapSize, coordinates);
    }
}
