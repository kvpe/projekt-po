
package pl.pwr.simulation.map;

import pl.pwr.simulation.application.ApplicationArguments;

public class Field {

        int size;
        int[][] fieldProperties;
        public Field(ApplicationArguments arguments) {
                size = arguments.getSize();
                fieldProperties = new int[size][size];
                for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                                fieldProperties[i][j] = 0;
                        }
                }
        }
        public void showField(){
                for (int i = 0; i < fieldProperties.length; i++) {
                        for (int j = 0; j < fieldProperties.length; j++) System.out.print(fieldProperties[i][j]);
                        System.out.print("\n");
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
