/*
KOLBERTOWICZ JAKUB, TOCZYNSKI ANTONI

kolejność wprowadzania argumentów:
- rozmiar mapy,
- maksymalna ilosc jednostek w oddziale,
- ilosc oddzialow

np.
gradle run --args="10 30 5"

W celu prawidlowego dzialania programu nalezy zmienic sciezki plikow w klasie Names i SimulationWriter
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

