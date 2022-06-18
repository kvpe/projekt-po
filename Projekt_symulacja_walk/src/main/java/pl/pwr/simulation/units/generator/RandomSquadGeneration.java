package pl.pwr.simulation.units.generator;

import pl.pwr.simulation.map.Coordinates;
import pl.pwr.simulation.units.Squad;

import java.io.IOException;

import static pl.pwr.simulation.map.RandomGenerator.random;

public class RandomSquadGeneration  {

    public Squad randomSquad(int maxSquadSize, int mapSize, int id) throws IOException {
        Names names = new Names();
        String name = names.getNames1().get(random.nextInt(names.getNames1().size())).toUpperCase() + " " + names.getNames2().get(random.nextInt(names.getNames2().size())).toUpperCase();

        int foodQuantity = random.nextInt(maxSquadSize * 10) + maxSquadSize * 5;
        int soldiersQuantity;
        int archersQuantity;
        int horsemenQuantity;
        do {
            soldiersQuantity = random.nextInt(maxSquadSize);
            maxSquadSize -= (soldiersQuantity);

            archersQuantity = random.nextInt(maxSquadSize);
            maxSquadSize -= (archersQuantity);

            horsemenQuantity = random.nextInt(maxSquadSize);
        }while(soldiersQuantity == 0 && archersQuantity == 0 && horsemenQuantity == 0);

        return new Squad(archersQuantity, soldiersQuantity, horsemenQuantity, foodQuantity, name, id, mapSize, false);
    }
}
