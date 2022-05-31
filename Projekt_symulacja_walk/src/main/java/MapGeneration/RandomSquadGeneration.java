package MapGeneration;

import Units.Squad;

import java.io.IOException;

public class RandomSquadGeneration  {

    public Squad randomSquad(int maxSquadSize, int mapSize, int id, Coordinates coordinates) throws IOException {
        //Names names = new Names();
       // String name = names.getNames1().get(random.nextInt(names.getNames1().size())) + names.getNames2().get(random.nextInt(names.getNames2().size()));
        String name = "not random";
        int foodQuantity = RandomGenerator.random.nextInt(maxSquadSize * 5) + maxSquadSize * 2;
        int soldiersQuantity = RandomGenerator.random.nextInt(maxSquadSize);
        maxSquadSize -= soldiersQuantity;
        int archersQuantity = RandomGenerator.random.nextInt(maxSquadSize);
        maxSquadSize -= archersQuantity;
        int horsemenQuantity = RandomGenerator.random.nextInt(maxSquadSize);
        return new Squad(archersQuantity, soldiersQuantity, horsemenQuantity, foodQuantity, name, id, mapSize, coordinates);
    }
}
