package MapGeneration;


public class RandomEventsGenerator implements RandomInterface {
    @Override
        public void randomise(Field field) {
        int events = 0;
        while(events < field.size * 2){
            int x = RandomGenerator.random.nextInt(field.size);
            int y = RandomGenerator.random.nextInt(field.size);
            if (field.tab[x][y] == 0) {
                field.tab[x][y] = 1;
                events++;
            }
        }
    }
}
