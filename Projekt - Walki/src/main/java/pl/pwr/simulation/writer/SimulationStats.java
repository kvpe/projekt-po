package pl.pwr.simulation.writer;


public final class SimulationStats {
    public int simulationDuration = 0;
    public int  squadsQuantity = 0;
    public static int unitsQuantity = 0;
    public static int howManyKilled = 0;
    public static int howManyStarved = 0;

    public void increaseDuration(){
        this.simulationDuration++;
    }

    public void setSquadsQuantity(int squadsQuantity) {
        this.squadsQuantity = squadsQuantity;
    }

    public int getSimulationDuration() {
        return simulationDuration;
    }

    public int getSquadsQuantity() {
        return squadsQuantity;
    }
    
}

