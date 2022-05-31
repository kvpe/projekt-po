package SimulationDiary;

public class SimulationPrinter {

    public void print(SimulationStats stats ){
        System.out.println
                ( "\nDay: " + stats.simulationDuration
                + "\nNumber of squads: " + stats.squadQuantity 
                + "\nSoldiers alive:" + stats.unitsQuantity
                + "\nNumber of killed: " + stats.howManyKilled
                + "\nNumber of starved to death" + stats.howManyStarved);
    }
}
