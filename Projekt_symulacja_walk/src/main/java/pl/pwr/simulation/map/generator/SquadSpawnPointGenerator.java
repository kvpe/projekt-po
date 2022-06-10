package pl.pwr.simulation.map.generator;


import pl.pwr.simulation.map.Field;
import pl.pwr.simulation.map.RandomGenerator;
import pl.pwr.simulation.map.RandomInterface;

public class SquadSpawnPointGenerator implements RandomInterface {

    int maxAmountOfSquads = 5; //will be set by a user

    @Override
    public void randomise(Field field) {
        int numberOfSquads = 0;
        while (numberOfSquads < maxAmountOfSquads) {

            int x = RandomGenerator.random.nextInt(field.getSize());
            int y = RandomGenerator.random.nextInt(field.getSize());

            if (field.getFieldProperties()[x][y] == 0) {
                field.getFieldProperties()[x][y] = 3;
                numberOfSquads++;
            }

        }
    }
}
