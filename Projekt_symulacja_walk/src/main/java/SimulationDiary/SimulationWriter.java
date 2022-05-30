package SimulationDiary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SimulationWriter {
    File file = new File("java/TextFiles/SimulationSummary.csv");
    //tu nie ma co sie bawic imo bo pewnie dojdzie pare rzeczy ktore bedziemy chcieli zapisac
    public SimulationWriter(SimulationStats stats) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write( "\nDay: " + stats.simulationDuration
                + "\nNumber of squads: " + stats.squadQuantity
                + "\nSoldiers alive:" + stats.unitsQuantity
                + "\nNumber of killed: " + stats.howManyKilled
                + "\nNumber of starved to death" + stats.howManyStarved);
        fileWriter.close();
    }
}
