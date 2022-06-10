package pl.pwr.simulation.units;

public class Horseman extends Unit{
    private int horseHealth;

    public Horseman(int size) {
        super(60, 40,(float)size/2);
        horseHealth = 300;
    }

    public int getHorseHealth() {
        return horseHealth;
    }
}
