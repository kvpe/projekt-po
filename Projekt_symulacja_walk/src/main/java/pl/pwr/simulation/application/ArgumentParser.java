package pl.pwr.simulation.application;

import static java.lang.Integer.parseInt;

public class ArgumentParser {

      public ApplicationArguments parse(String[] args) {

          int mapSize = parseInt(args[0]);
          int maxSquadSize = parseInt(args[1]);
          int amountOfSquads = parseInt(args[2]);

          return new ApplicationArguments(mapSize, maxSquadSize, amountOfSquads);
      }
}
