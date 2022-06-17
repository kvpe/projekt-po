
package pl.pwr.simulation.map;

public class Field {

        int size;
        int[][] fieldProperties;

        public Field(int mapSize, int amountOfSquads) {
                size = mapSize;
                fieldProperties = new int[size][size];
                for (int x = 0; x < size; x++) {
                        for (int y = 0; y < size; y++) fieldProperties[x][y] = 0;
                }

                int numberOfSquads  = 1;
                int numberOfFoodFields = 1;
                int numberOfEvents = 0;

                RandomInterface generator = variable -> {
                        int x = RandomGenerator.random.nextInt(size);
                        int y = RandomGenerator.random.nextInt(size);
                        if (fieldProperties[x][y] == 0) {
                                fieldProperties[x][y] = variable;
                                return true;
                        }
                        return false;
                };

                while(numberOfSquads < amountOfSquads ){
                        if(numberOfFoodFields < mapSize * 4.2){
                                if(generator.randomise(2)) numberOfFoodFields ++;
                        }
                        else if(numberOfEvents < mapSize * 1.7){
                                if(generator.randomise(1)) numberOfEvents++;
                        }
                        else{
                                if(generator.randomise(3)) numberOfSquads ++;
                        }
                }
        }

        public void changeTab(int x, int y, int value){
                fieldProperties[x][y] = value;
        }

        public int getSize() {
                return size;
        }

        public int[][] getFieldProperties() {
                return fieldProperties;
        }
        
}
