package pl.pwr.simulation.writer;

import pl.pwr.simulation.map.Field;
import pl.pwr.simulation.map.Map;
import pl.pwr.simulation.units.Squad;

public class SimulationPrinter {

    public void printResults(SimulationStats stats){
        System.out.println
                (
                        "\n************************************************************"
                        + "\nSimulation duration: " + stats.simulationDuration
                        + "\nNumber of squads alive: " + stats.squadsQuantity
                        + "\nNumber of soldiers alive: " + SimulationStats.unitsQuantity
                        + "\nNumber of soldiers killed in battle: " + SimulationStats.howManyKilled
                        + "\nNumber of soldiers starved to death: " + SimulationStats.howManyStarved
                        + "\n************************************************************");
    }

    public void print(SimulationStats stats ){
        System.out.println
                ( "\n************************************************************" + "\nDay: " + stats.simulationDuration
                + "\nNumber of squads: " + stats.squadsQuantity
                + "\nSoldiers alive: " + SimulationStats.unitsQuantity
                + "\nNumber of soldiers killed in battle: " + SimulationStats.howManyKilled
                + "\nNumber of soldiers starved to death: " + SimulationStats.howManyStarved
                + "\n************************************************************");
    }

    public void print(Squad squad){
        System.out.println
                ("\n------------------------------------------------------------" + "\nID: " + squad.getSquadId() + "\n" + squad.getName() + " are about to move."
                + "\nTheir coordinates are: " + squad.getSquadCoordinates().toString()
                + "\nAmount of units: " + squad.getSquadQuantity()
                + "\nDamage: " + squad.getDamage()
                + "\nMap speed: " + (double)Math.round(squad.getSquadMapSpeed() * 1000) / 1000
                + "\nSquad Health: " + squad.getSquadHealth()
                + "\nFood units: " +squad.getFoodQuantity()
                + "\n------------------------------------------------------------\n");
    }

    public void printMap(Field field, Map map){
        System.out.print("  ");
        for (int i = 0; i < field.getSize(); i ++){
            System.out.print(i);
        }
        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < field.getSize(); i ++){
            System.out.print("-");
        }
        System.out.println();
        for(int x = 0; x < field.getSize(); x++){
            for (int y = 0; y < field.getSize() + 3; y++){

                if(y == 0) System.out.print(x);
                else if(y == 1 || y == field.getSize() + 2) System.out.print("|");
                else if (field.getFieldProperties()[x][y - 2] == 1) System.out.print("e");
                else if (field.getFieldProperties()[x][y - 2] == 2) System.out.print("F");
                else if (field.getFieldProperties()[x][y - 2] == 3 && map.getSquadMap()[x][y - 2].getSquadId() != -1) System.out.print(map.getSquadMap()[x][y - 2].getSquadId());
                else if (field.getFieldProperties()[x][y - 2] == 0) System.out.print(" ");
                else System.out.print(" ");
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int i = 0; i < field.getSize(); i ++){
            System.out.print("-");
        }
    }
}
