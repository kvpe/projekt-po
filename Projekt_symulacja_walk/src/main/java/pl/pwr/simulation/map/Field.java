
package pl.pwr.simulation.map;

public class Field {

        int size;
        int[][] fieldProperties;

        public Field(int mapSize) {
                size = mapSize;
                fieldProperties = new int[size][size];
                for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) fieldProperties[i][j] = 0;
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
