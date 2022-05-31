
package     MapGeneration;

import Application.ApplicationArguments;

public class Field {

        int size;
        int[][] tab;
        public Field(ApplicationArguments arguments) {
                size = arguments.getSize();
                tab = new int[size][size];
                for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                                tab[i][j] = 0;
                        }
                }
        }
        public void showField(){
                for (int i = 0; i < tab.length; i++) {
                        for (int j = 0; j < tab.length; j++) System.out.print(tab[i][j]);
                        System.out.print("\n");
                }
        }

        public void changeTab(int x, int y, int value){
                tab[x][y] = value;
        }

        public int getSize() {
                return size;
        }

        public int[][] getTab() {
                return tab;
        }
        
}
