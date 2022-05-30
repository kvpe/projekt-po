package Units;

import MapGeneration.Coordinates;
import MapGeneration.Field;

public interface FightResolver {
        int attack(); //pozmieniałem typy metod, nie wiem czy do konca dobrze, ogarniemy przy implementacji
        Squad deathFromStarvation(Squad squad);
        Coordinates move(Field field, Squad squad);
        Squad defence(Squad squad, int damage, boolean firstStrike);
        int starvationCheck(Squad squad);
        Squad chooseUnitsToDie(Squad squad, int damage);
        Squad connectSquads(Squad squad1, Squad squad2);
}
