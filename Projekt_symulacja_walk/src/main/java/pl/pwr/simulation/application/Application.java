/*
KOLBERTOWICZ JAKUB, TOCZYNSKI ANTONI

DO ZROBIENIA:
- testy jednostkowe
- ogarnąć polimorfizm, interfejsy
- r rando

Dzień dobry, przesyłam uwagi na wyższą ocenę:

 zastosować hermetyzację - chyba juz

konstuktor Map jest zbyt skomplikowany

lepszym modelem cd. przechowywania lokalizacji drużyn może być Map<Squad, Coordinate> squadLocations


szukanie drużyn (seekSquad) brzmi jak odpowiedzialność innej klasy

 setX(-1) wygląda jak brzydki hack :))

 logikę generowania planszy wyobrażałbym sobie tak - genertujemy tablicę x na x, po czym iterujemy po każdej komórce.
 Przy pojedynczej iteracji pytamy jakąś klasę odpowiedzialną za losowe generowanie o to, co powinno tam się znaleźć
*/
package pl.pwr.simulation.application;

import pl.pwr.simulation.map.Field;
import pl.pwr.simulation.map.Map;
import pl.pwr.simulation.map.RandomGenerator;
import pl.pwr.simulation.writer.SimulationStats;

import java.io.IOException;
import java.util.Random;

public class Application {
    public static void main(String[] args) throws Exception {

        RandomGenerator.random = new Random();
        ArgumentParser argumentParser = new ArgumentParser();
        ApplicationArguments parsedArguments = argumentParser.parse(args);
        System.out.println(parsedArguments.toString());

        if(parsedArguments.checkArguments()) throw new IOException("WRONG APPLICATION ARGUMENTS");

        Field field = new Field(parsedArguments.getMapSize(), parsedArguments.getAmountOfSquads());
        Map map = new Map(field, parsedArguments.getMaxSquadSize(), parsedArguments.getAmountOfSquads());
        SimulationStats stats = new SimulationStats();
        SimulationRunner simulationRunner = new SimulationRunner();
        simulationRunner.runSimulation(map, field, stats);

    }
}

