package SimulationDiary;

public class SimulationStats {
    int simulationDuration;
    int squadQuantity;
    int unitsQuantity;
    int howManyKilled;
    int howManyStarved;

    public SimulationStats(int squadQuantity, int unitsQuantity) { //bedzie trzeba zliczac liczbe jednostek jakos
        this.simulationDuration = 0;
        this.squadQuantity = squadQuantity;
        this.unitsQuantity = unitsQuantity;
        this.howManyKilled = 0; // może jakas zmienna zliczajaca ilosc jednostek przed tura i po
        this.howManyStarved = 0;
    }

    public SimulationStats() { //tymczasowy zeby nie wywalalo bledu
    }
}
