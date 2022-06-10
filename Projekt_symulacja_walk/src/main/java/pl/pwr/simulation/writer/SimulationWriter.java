package pl.pwr.simulation.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SimulationWriter {
    File file = new File("C:\\Gradle\\Projekt_symulacja_walk\\src\\main\\resources\\SimulationSummary.csv");
    public SimulationWriter(SimulationStats stats) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(+ stats.simulationDuration //duration
                + "," + stats.squadQuantity //chyba useless bo zawsze albo 0 albo 1
                + "," + stats.unitsQuantity //soldiers alive
                + "," + stats.howManyKilled
                + "," + stats.howManyStarved
                + ",");
        fileWriter.close();
    }
}
