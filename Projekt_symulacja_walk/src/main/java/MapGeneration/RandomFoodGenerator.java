package MapGeneration;



public class RandomFoodGenerator implements RandomInterface {
    @Override
    public void randomise (Field field){
        int foodFields = 0;
        while (foodFields < field.size * 5) {
            int x = RandomGenerator.random.nextInt(field.size);
            int y = RandomGenerator.random.nextInt(field.size);
            if (field.tab[x][y] == 0) {
                field.tab[x][y] = 2;
                foodFields++;
            }
        }
    }
}
