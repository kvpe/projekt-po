package MapGeneration;

import java.util.Random;


public class RandomFoodGenerator implements RandomInterface {

   // public int maxAmountOfFoodFields = 20; //powinno byc chyba jakos zalezne od rozmiaru mapy, dokladny wzor ogarniemy po tym jak czesto beda glodowac
    
    @Override
    public void randomise (Field field){
        int foodFields = 0;
        Random random = new Random();
        while (foodFields < field.size * 0.75) {
            int x = random.nextInt(field.size);
            int y = random.nextInt(field.size);
            if (field.tab[x][y] == 0) {
                field.tab[x][y] = 2;
                foodFields++;
            }
        }
    }
}
