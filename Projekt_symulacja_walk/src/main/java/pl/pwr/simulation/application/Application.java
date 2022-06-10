/*
KOLBERTOWICZ JAKUB, TOCZYNSKI ANTONI

DO ZROBIENIA:
- przekazywanie większej liczby argumentów (aby użytkownik miał większy wpływ na działanie symulacji)
- drukowanie i zapisywanie statystyk symulacji do pliku
- łączenie oddziałów
- obsługa wyjątków
- optymalizacja kodu
- testy jednostkowe
*/
package pl.pwr.simulation.application;


import pl.pwr.simulation.map.Field;
import pl.pwr.simulation.map.Map;
import pl.pwr.simulation.map.RandomGenerator;
import pl.pwr.simulation.writer.SimulationPrinter;
import pl.pwr.simulation.writer.SimulationStats;


import java.io.IOException;
import java.util.Random;

public class Application {
    public static void main(String[] args) throws IOException {
        RandomGenerator.random = new Random();
        ArgumentParser argumentParser = new ArgumentParser();
        ApplicationArguments parsedArguments = argumentParser.parse(args);
        SimulationPrinter printer = new SimulationPrinter();
        System.out.println(parsedArguments.toString());

        Field field = new Field(parsedArguments);
        Map map = new Map(field);
        SimulationStats stats = new SimulationStats();
        int numberOfSquadsAlive = stats.getSquadQuantity();
        SimulationRunner simulationRunner = new SimulationRunner();
        simulationRunner.runSimulation(map, field, stats);


}}

