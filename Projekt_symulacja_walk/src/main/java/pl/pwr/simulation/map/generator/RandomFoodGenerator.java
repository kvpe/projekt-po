package pl.pwr.simulation.map.generator;


import pl.pwr.simulation.map.Field;
import pl.pwr.simulation.map.RandomGenerator;
import pl.pwr.simulation.map.RandomInterface;

public class RandomFoodGenerator implements RandomInterface {
    @Override
    public void randomise (Field field){
        int foodFields = 0;
        int counter = 0;
        while (foodFields < field.getSize() * 4.5) {
            int x = RandomGenerator.random.nextInt(field.getSize());
            int y = RandomGenerator.random.nextInt(field.getSize());
            if (field.getFieldProperties()[x][y] == 0) {
                field.getFieldProperties()[x][y] = 2;
                foodFields++;
            }
            else counter ++;
            if(counter > Math.pow(field.getSize(), 4)) break;
        }
    }
}
