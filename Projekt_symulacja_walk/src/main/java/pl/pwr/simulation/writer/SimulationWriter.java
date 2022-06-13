package pl.pwr.simulation.writer;

import pl.pwr.simulation.units.Squad;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SimulationWriter {
    File file = new File("E:\\IntelliJ Java projekty\\Projekt_symulacja_walk\\src\\main\\resources\\SimulationSummary.csv");
    public SimulationWriter(SimulationStats stats, Squad squad) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(stats.simulationDuration
                + "," + squad.getName()
                + "," + SimulationStats.unitsQuantity
                + "," + SimulationStats.howManyKilled
                + "," + SimulationStats.howManyStarved);
        fileWriter.close();
    }
    public SimulationWriter(SimulationStats stats) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(stats.simulationDuration
                + "," + "NO WINNER"
                + "," + SimulationStats.unitsQuantity
                + "," + SimulationStats.howManyKilled
                + "," + SimulationStats.howManyStarved);
        fileWriter.close();
    }
}
