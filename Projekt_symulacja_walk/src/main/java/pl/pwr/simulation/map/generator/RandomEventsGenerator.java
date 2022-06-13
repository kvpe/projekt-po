package pl.pwr.simulation.map.generator;


import pl.pwr.simulation.map.Field;
import pl.pwr.simulation.map.RandomGenerator;
import pl.pwr.simulation.map.RandomInterface;

public class RandomEventsGenerator implements RandomInterface {
    @Override
    public void randomise(Field field) {
        int events = 0;
        while(events < field.getSize() * 1.75){
            int x = RandomGenerator.random.nextInt(field.getSize());
            int y = RandomGenerator.random.nextInt(field.getSize());
            if (field.getFieldProperties()[x][y] == 0) {
                field.getFieldProperties()[x][y] = 1;
                events++;
            }
        }
    }
}
