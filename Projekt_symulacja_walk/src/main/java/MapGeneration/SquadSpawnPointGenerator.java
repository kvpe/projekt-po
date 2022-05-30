package MapGeneration;

import java.util.Random;

public class SquadSpawnPointGenerator implements RandomInterface {

    int maxAmountOfSquads = 5; //jako argument wejsciowy
    @Override
    public void randomise(Field field) {
        int numberOfSquads = 0;
        Random random = new Random();
        while (numberOfSquads < maxAmountOfSquads) {
            int x = random.nextInt(field.size);
            int y = random.nextInt(field.size);
            if (field.tab[x][y] == 0) {
                field.tab[x][y] = 3;
                numberOfSquads++;
            }

        }
    }
}
