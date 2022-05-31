package Units;

public class Horseman extends Unit{
    private boolean hasHorse;
    private int horseHealth;

    public Horseman(int size) {
        super(60, 40,(float)size/2);
        hasHorse = true;
        horseHealth = 300;
    }

    public int getHorseHealth() {
        return horseHealth;
    }
}
