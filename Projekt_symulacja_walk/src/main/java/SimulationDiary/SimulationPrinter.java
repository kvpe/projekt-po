package SimulationDiary;

public class SimulationPrinter {
    SimulationStats stats = new SimulationStats();

    public void print(){
        System.out.println
                ( "\nDay: " + stats.simulationDuration
                + "\nNumber of squads: " + stats.squadQuantity 
                + "\nSoldiers alive:" + stats.unitsQuantity
                + "\nNumber of killed: " + stats.howManyKilled
                + "\nNumber of starved to death" + stats.howManyStarved);
    }
}
