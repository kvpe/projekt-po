package pl.pwr.simulation.application;

public final class ApplicationArguments {

    private final int mapSize;
    private final int maxSquadSize;
    private final int amountOfSquads;

    public int getMaxSquadSize() {
        return maxSquadSize;
    }

    public int getAmountOfSquads() {
        return amountOfSquads;
    }

    public int getMapSize() {
        return mapSize;
    }

    public ApplicationArguments(int size, int maxSquadSize, int amountOfSquads) {
        this.mapSize = size;
        this.maxSquadSize = maxSquadSize;
        this.amountOfSquads = amountOfSquads;
    }

    public String toString(){
        return "Map size: "+ this.mapSize
                + "\nmax squad size: " + this.maxSquadSize
                + "\namount of Squads: " +this.amountOfSquads;
    }

    public boolean checkArguments(){
        return mapSize <= 5 || maxSquadSize < 1 || amountOfSquads > (mapSize * mapSize * 0.1) || amountOfSquads < 2;
    }
}