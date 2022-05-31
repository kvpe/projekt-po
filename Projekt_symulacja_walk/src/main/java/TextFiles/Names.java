package TextFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Names {
    private List<String> names1 = new ArrayList<>();
    private List<String> names2 = new ArrayList<>();

    public Names() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("firstPartOfName.txt"));
        String line;
        while((line = reader.readLine()) != null){
            names1.add(line);
        }
        reader.close();
        reader = new BufferedReader(new FileReader("secondPartOfName.txt"));
        while((line = reader.readLine()) != null){
            names1.add(line);
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
