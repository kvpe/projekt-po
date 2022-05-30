package Application;

import java.io.File;
import java.io.FileNotFoundException;

public class Application {
    public static void main(String[] args) throws FileNotFoundException {
        ArgumentParser argumentParser = new ArgumentParser();
        ApplicationArguments parsedArguments = argumentParser.parse(args);
        System.out.println(parsedArguments.toString());
        File file = new File("src/java/TextFiles/");
        for(String fileNames : file.list()) System.out.println(fileNames);
//        Field field = new Field(parsedArguments);
//        Map map = new Map(field);
//        field.showField();
    }
}
