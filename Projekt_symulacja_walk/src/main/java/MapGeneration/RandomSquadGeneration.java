package MapGeneration;

import Units.Squad;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.UUID;

public class RandomSquadGeneration  {

    public Squad randomSquad(int maxSquadSize, int mapSize) throws FileNotFoundException {
        Random random = new Random();
        Names names = new Names();
        String name = names.getNames1().get(random.nextInt(names.getNames1().size())) + names.getNames2().get(random.nextInt(names.getNames2().size()));
        int foodQuantity = random.nextInt(maxSquadSize * 5) + maxSquadSize * 2;
        int soldiersQuantity = random.nextInt(maxSquadSize);
        maxSquadSize -= soldiersQuantity;
        int archersQuantity = random.nextInt(maxSquadSize);
        maxSquadSize -= archersQuantity;
        int horsemenQuantity = random.nextInt(maxSquadSize);
        Squad squad = new Squad(archersQuantity, soldiersQuantity, horsemenQuantity, foodQuantity, name, UUID.randomUUID(), mapSize);
        return squad;
    }
}
