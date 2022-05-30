package Units;

public class Horseman extends Unit{
    private boolean hasHorse; //nie ma sensu chyba bo nie ma wielu jeźdzców w oddziale ( w sensie ich staty sie oblicza na podstawie wymyslonej ilosci)
    private int horseHealth;

    public Horseman(int size) {
        super(60, 40,(float)size/4);
        hasHorse = true;
        horseHealth = 300;
    }

    public int getHorseHealth() {
        return horseHealth;
    }
}
