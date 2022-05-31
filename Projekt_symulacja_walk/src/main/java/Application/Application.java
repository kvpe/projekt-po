/*
KOLBERTOWICZ JAKUB, TOCZYŃSKI ANTONI

DO ZROBIENIA:
- przekazywanie większej liczby argumentów (aby użytkownik miał większy wpływ na działanie symulacji)
- losowanie imion
- drukowanie i zapisywanie statystyk symulacji do pliku
- łączenie oddziałów
- obsługa wyjątków
- optymalizacja kodu
- testy jednostkowe
*/
package Application;

import MapGeneration.Field;
import MapGeneration.Map;
import MapGeneration.RandomGenerator;
import SimulationDiary.SimulationStats;

import java.io.IOException;
import java.util.Random;

public class Application {
    public static void main(String[] args) throws IOException {
        RandomGenerator.random = new Random();
        ArgumentParser argumentParser = new ArgumentParser();
        ApplicationArguments parsedArguments = argumentParser.parse(args);
        System.out.println(parsedArguments.toString());

        Field field = new Field(parsedArguments);
        Map map = new Map(field);
        field.showField();
        SimulationStats stats = new SimulationStats();
        SimulationRunner simulationRunner = new SimulationRunner();
        simulationRunner.runSimulation(map, field, stats);
    }
}
