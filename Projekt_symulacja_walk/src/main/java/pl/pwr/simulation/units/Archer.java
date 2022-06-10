package pl.pwr.simulation.units;

public class Archer extends Unit {
    public static int range; //static?
    public Archer(int size) {
        super(50, 50, (float)size/4);
        range = size/5;
    }
}
