package Units;

public class Archer extends Unit {
    private int range;
    public Archer(int size) {
        super(50, 50, (float)size/4);
        range = 3;
    }
}
