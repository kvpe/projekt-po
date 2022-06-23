package pl.pwr.simulation.units.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Names {

    private final List<String> names1 = new ArrayList<>();
    private final List<String> names2 = new ArrayList<>();

    public Names() throws IOException {
        Scanner reader = new Scanner(new File("E:\\IntelliJ Java projekty\\PO PROJEKT\\src\\main\\resources\\firstPartOfName.txt"));
        while(reader.hasNextLine()){
            names1.add(reader.nextLine());
        }
        reader.close();
        reader = new Scanner(new File("E:\\IntelliJ Java projekty\\PO PROJEKT\\src\\main\\resources\\secondPartOfName.txt"));
        while(reader.hasNextLine()){
            names2.add(reader.nextLine());
        }
        reader.close();
    }

    public List<String> getNames1() {
        return names1;
    }

    public List<String> getNames2() {
        return names2;
    }
}
