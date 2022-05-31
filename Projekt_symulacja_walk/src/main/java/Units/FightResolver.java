package Units;

import MapGeneration.Coordinates;
import MapGeneration.Field;

public interface FightResolver {
        int attack();
        void deathFromStarvation();
        Coordinates move(Field field);
        Squad defence(Squad squad, int damage);
        int starvationCheck();
        void chooseUnitsToDie(int damage);
        Squad connectSquads(Squad squad1, Squad squad2);
}
