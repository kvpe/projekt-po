package pl.pwr.simulation.writer;

public class SimulationStats {
    int simulationDuration;
    int squadQuantity;
    int unitsQuantity;
    int howManyKilled;
    int howManyStarved;

    public SimulationStats(int squadQuantity, int unitsQuantity) { //single units will have to be counted
        this.simulationDuration = 0;
        this.squadQuantity = squadQuantity;
        this.unitsQuantity = unitsQuantity;
        this.howManyKilled = 0;
        this.howManyStarved = 0;
    }

    public SimulationStats() { //temporary - to avoid error
    }

    public void increaseDuration(){
        this.simulationDuration++;
    }

    public void setSquadQuantity(int squadQuantity) {
        this.squadQuantity = squadQuantity;
    }

    public void setUnitsQuantity(int unitsQuantity) {
        this.unitsQuantity = unitsQuantity;
    }

    public void setHowManyKilled(int howManyKilled) {
        this.howManyKilled = howManyKilled;
    }

    public void setHowManyStarved(int howManyStarved) {
        this.howManyStarved = howManyStarved;
    }

    public int getSimulationDuration() {
        return simulationDuration;
    }

    public int getSquadQuantity() {
        return squadQuantity;
    }

    public int getUnitsQuantity() {
        return unitsQuantity;
    }

    public int getHowManyKilled() {
        return howManyKilled;
    }

    public int getHowManyStarved() {
        return howManyStarved;
    }
}

