package Application;

import MapGeneration.Coordinates;
import MapGeneration.Field;
import MapGeneration.Map;
import MapGeneration.RandomGenerator;
import SimulationDiary.SimulationStats;
import Units.Squad;

public class SimulationRunner {
    public SimulationRunner() {

    }

    public void runSimulation(Map map, Field field, SimulationStats stats) {
        int numberOfSquadsAlive = stats.getSquadQuantity();
        do {
            numberOfSquadsAlive = 0;
            for (int x = 0; x < field.getSize(); x++) {
                for (int y = 0; y < field.getSize(); y++) {
                    if (map.getSquadMap()[x][y].getSquadId() != -1) {
                        numberOfSquadsAlive++;
                    }
                }
            }
            stats.increaseDuration();
            System.out.println("\n\n*****TURN: "+ stats.getSimulationDuration()+"*****");
            for (int x = 0; x < field.getSize(); x++)
                for (int y = 0; y < field.getSize(); y++)
                    if (map.getSquadMap()[x][y].getSquadId() != -1) map.getSquadMap()[x][y].setHasMoved(false);

            for (int x = 0; x < field.getSize(); x++) {
                for (int y = 0; y < field.getSize(); y++) {

                    if (map.getSquadMap()[x][y].getSquadId() != -1) {
                        Squad squad = map.getSquadMap()[x][y];
                        if (!squad.isHasMoved()) {

                            if (squad.getSquadCoordinates().getX() != x || squad.getSquadCoordinates().getY() != y) {
                                Coordinates fixCoordinates = new Coordinates();
                                fixCoordinates.setX(x);
                                fixCoordinates.setY(y);
                                squad.setSquadCoordinates(fixCoordinates);
                            }

                            System.out.println("\n\nid: " + squad.getSquadId() + "\nSquad health: " + squad.getSquadHealth() + "\nOld coordinates \n" + squad.getSquadCoordinates().toString());
                            Coordinates coordinates = squad.move(field);
                            if (field.getTab()[coordinates.getX()][coordinates.getY()] == 0 || field.getTab()[coordinates.getX()][coordinates.getY()] == 1 || field.getTab()[coordinates.getX()][coordinates.getY()] == 2) {
                                map.changeMapCoordinates(squad, coordinates, x, y);
                                squad.setSquadCoordinates(coordinates);
                                System.out.println("\nNew coordinates\n" + squad.getSquadCoordinates().toString());
                                squad.setHasMoved(true);
                                field.changeTab(x, y, 0);
                            }

                            if (map.getSquadMap()[coordinates.getX()][coordinates.getY()].ifExists()) {
                                if (map.getSquadMap()[coordinates.getX()][coordinates.getY()].getSquadId() != squad.getSquadId()) {
                                   // System.out.println(squad.getSquadId() + " want to go to: " + coordinates.toString() + " but there is squad " + map.getSquadMap()[coordinates.getX()][coordinates.getY()].getSquadId());
                                    map.getSquadMap()[coordinates.getX()][coordinates.getY()] = map.getSquadMap()[coordinates.getX()][coordinates.getY()].defence(map.getSquadMap()[coordinates.getX()][coordinates.getY()], squad.attack());
                                }
                            }

                            if (field.getTab()[coordinates.getX()][coordinates.getY()] == 2) {
                                squad.foodField();
                                field.changeTab(coordinates.getX(), coordinates.getY(), 3);
                            }

                            if (field.getTab()[coordinates.getX()][coordinates.getY()] == 1) {
                                int randomEvent = RandomGenerator.random.nextInt(6) + 1;
                                field.changeTab(coordinates.getX(), coordinates.getY(), 3);
                                switch (randomEvent) {
                                    case 1:
                                        squad.bearAttack();
                                        break;
                                    case 2:
                                        squad.banditsTheft();
                                        break;
                                    case 3:
                                        squad.herdOfCattle();
                                        break;
                                    case 4:
                                        squad.banditsAttack();
                                        break;
                                    case 5:
                                        squad.bootsDisappearing();
                                        break;
                                    case 6:
                                        squad.lostRecruits();
                                        break;
                                    case 7:
                                        squad.funnyLookingPlant();
                                        break;
                                    default:
                                        System.out.println("error");
                                        break;
                                }
                            }

                            if (squad.starvationCheck() <= 0) {
                                squad.deathFromStarvation();
                            } else if (squad.ifExists()) System.out.println("They don't lack food, as they have " + squad.getFoodQuantity() + " food units left");
                        }
                    }

                }
            }
        } while (numberOfSquadsAlive > 1);
        for (int x = 0; x < field.getSize(); x++) {
            for (int y = 0; y < field.getSize(); y++) {
                if (map.getSquadMap()[x][y].getSquadId() != -1) {
                    System.out.println("After "+ stats.getSimulationDuration() + " turns" + " Winner is squad " + map.getSquadMap()[x][y].getSquadId());
                }
            }
        }
        if(numberOfSquadsAlive == 0 ) System.out.println("After "+ stats.getSimulationDuration() + " turns everybody died");
    }
}