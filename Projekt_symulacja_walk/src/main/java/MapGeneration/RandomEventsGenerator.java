package MapGeneration;

import java.util.Random;


public class RandomEventsGenerator implements RandomInterface {

    //int maxAmountOfEvents = 20; //tez mysle od rozmiaru mapy powinno zależec
    @Override
        public void randomise(Field field) {
        int events = 0;
        Random random = new Random();
        while(events < field.size * 0.15){
            int x = random.nextInt(field.size);
            int y = random.nextInt(field.size);
            if (field.tab[x][y] == 0) {
                field.tab[x][y] = 1;
                events++;
            }
        }
    }
    
}
