/*
KOLBERTOWICZ JAKUB, TOCZYNSKI ANTONI

DO ZROBIENIA:
- testy jednostkowe
- r rando

Dzień dobry, przesyłam uwagi na wyższą ocenę:

zastosować hermetyzację - chyba juz

konstuktor Map jest zbyt skomplikowany

*/
package pl.pwr.simulation.application;

import pl.pwr.simulation.map.Field;
import pl.pwr.simulation.map.SimulationMap;
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
        SimulationMap simulationMap = new SimulationMap(field, parsedArguments.getMaxSquadSize());
        SimulationStats stats = new SimulationStats();
        SimulationRunner simulationRunner = new SimulationRunner();
        simulationRunner.runSimulation(simulationMap, field, stats);

    }
}

