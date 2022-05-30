package MapGeneration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Names {
    private List<String> names1 = new ArrayList<>();
    private List<String> names2 = new ArrayList<>();

    public Names() throws FileNotFoundException {
        Scanner reader = new Scanner(new File("java/TextFiles/Names1.txt"));
        while(reader.hasNextLine()){
            names1.add(reader.nextLine());
        }
        reader = new Scanner(new File("java/TextFiles/Name2.txt"));
        while(reader.hasNextLine()){
            names2.add(reader.nextLine());
        }
    }

    public List<String> getNames1() {
        return names1;
    }

    public List<String> getNames2() {
        return names2;
    }
}
