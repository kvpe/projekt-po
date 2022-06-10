package pl.pwr.simulation.units;

import pl.pwr.simulation.map.Coordinates;
import pl.pwr.simulation.map.Field;

public interface FightResolver {
        int attackAll();
        void deathFromStarvation();
        Coordinates move(Field field);
        Squad defence(Squad squad, int damage);
        int starvationCheck();
        void chooseUnitsToDie(int damage);
        Squad connectSquads(Squad squad1, Squad squad2);
}
