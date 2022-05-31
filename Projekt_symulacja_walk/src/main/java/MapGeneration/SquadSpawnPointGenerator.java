package MapGeneration;


public class SquadSpawnPointGenerator implements RandomInterface {

    int maxAmountOfSquads = 5; //will be set by a user
    @Override
    public void randomise(Field field) {
        int numberOfSquads = 0;
        while (numberOfSquads < maxAmountOfSquads) {
            int x = RandomGenerator.random.nextInt(field.size);
            int y = RandomGenerator.random.nextInt(field.size);
            if (field.tab[x][y] == 0) {
                field.tab[x][y] = 3;
                numberOfSquads++;
            }

        }
    }
}
