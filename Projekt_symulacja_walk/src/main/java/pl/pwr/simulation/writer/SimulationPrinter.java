package pl.pwr.simulation.writer;

import pl.pwr.simulation.units.Squad;

public class SimulationPrinter {

    public void print(SimulationStats stats ){
        System.out.println
                ( "\n********************" + "\nDay: " + stats.simulationDuration
                + "\nNumber of squads: " + stats.squadQuantity 
                + "\nSoldiers alive:" + stats.unitsQuantity
                + "\nNumber of killed: " + stats.howManyKilled
                + "\nNumber of starved to death: " + stats.howManyStarved
                + "\n********************");
    }

    public void print(Squad squad){
        System.out.println
                ( "\n--------------------" + "\n" + squad.getName() + " are about to move."
                + "\nTheir coordinates are: " + squad.getSquadCoordinates().toString()
                + "\nAmount of units: " + squad.getSquadQuantity()
                + "\nFood units: " +squad.getFoodQuantity()
                + "\n--------------------\n");
    }
}
