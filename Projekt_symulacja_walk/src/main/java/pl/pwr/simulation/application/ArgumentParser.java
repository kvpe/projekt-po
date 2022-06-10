package pl.pwr.simulation.application;

import static java.lang.Integer.parseInt;

public class ArgumentParser {

      public ApplicationArguments parse(String[] args) {
          int size = parseInt(args[0]);
         // int maxSquadSize = parseInt(args[1]);

          ApplicationArguments parsedArguments = new ApplicationArguments(size);
          return parsedArguments;
      }
}
