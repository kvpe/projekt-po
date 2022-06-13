package pl.pwr.simulation.application;

import pl.pwr.simulation.map.Coordinates;
import pl.pwr.simulation.map.Field;
import pl.pwr.simulation.map.Map;
import pl.pwr.simulation.map.RandomGenerator;
import pl.pwr.simulation.units.Squad;
import pl.pwr.simulation.units.SquadOperations;
import pl.pwr.simulation.writer.SimulationPrinter;
import pl.pwr.simulation.writer.SimulationStats;
import pl.pwr.simulation.writer.SimulationWriter;

import java.io.IOException;

public class SimulationRunner {

    SquadOperations operation = new SquadOperations();

    public void runSimulation(Map map, Field field, SimulationStats stats) throws Exception {

        SimulationPrinter printer = new SimulationPrinter();
        Squad squad;
        Coordinates initialCords;
        Coordinates newCords;

        do {
            stats.setSquadsQuantity(countSquads(map));
            stats.increaseDuration();
            initialCords = seekSquad(map);

            if (initialCords == null) initialCords = seekSquad(map);
            if (initialCords == null) throw new IOException("Something went wrong");

            squad = map.getSquadMap()[initialCords.getX()][initialCords.getY()];
            squad.setSquadCoordinates(initialCords);

            printer.print(stats);
            if( map.getSquadMap().length <= 10 && stats.getSquadsQuantity() <= 10) printer.printMap(field, map);
            printer.print(squad);

            newCords = operation.move(field, squad);
            resolveSquadMove(map, field, squad, newCords, initialCords);

            stats.setSquadsQuantity(countSquads(map));

        } while (stats.getSquadsQuantity() > 1);

        initialCords = seekSquad(map);
        if (initialCords == null) initialCords = seekSquad(map);

        if (initialCords != null){
            System.out.println("After " + stats.getSimulationDuration() + " turns" + " winners are " + map.getSquadMap()[initialCords.getX()][initialCords.getY()].getName());
            new SimulationWriter(stats, map.getSquadMap()[initialCords.getX()][initialCords.getY()]);
        }
        else{
            System.out.println("After " + stats.getSimulationDuration() + " turns everybody died");
            new SimulationWriter(stats);
        }
        printer.printResults(stats);

    }

    private Coordinates seekSquad(Map map) {

        Coordinates cords = new Coordinates();

        for (int x = 0; x < map.getSquadMap().length; x++) {
            for (int y = 0; y < map.getSquadMap().length; y++) {
                if (map.getSquadMap()[x][y].ifExists() && !map.getSquadMap()[x][y].isHasMoved()){
                    cords.setX(x);
                    cords.setY(y);
                    return cords;
                }
            }
        }

        cords.setX(-1);

        for (int x = 0; x < map.getSquadMap().length; x++) {
            for (int y = 0; y < map.getSquadMap().length; y++) {
                if (map.getSquadMap()[x][y].ifExists() && map.getSquadMap()[x][y].isHasMoved()) {
                    map.getSquadMap()[x][y].setHasMoved(false);
                    if (cords.getX() == -1) {
                        cords.setX(x);
                        cords.setY(y);
                    }
                }
            }
        }

        if (cords.getX() == -1) return null;
        return cords;

    }

    private void squadStarvationCheck(Squad squad){
        if(operation.starvationCheck(squad) <= 0) operation.deathFromStarvation(squad);
        else if(squad.ifExists()) System.out.println(squad.getName() + " don't lack food as they have " + squad.getFoodQuantity() + " food units left.");
    }

    private void resolveSquadMove(Map map, Field field, Squad squad, Coordinates newCords, Coordinates initialCords) throws Exception {

        if (map.getSquadMap()[newCords.getX()][newCords.getY()].ifExists()) {
            if (map.getSquadMap()[newCords.getX()][newCords.getY()].getSquadId() != squad.getSquadId()) {
                System.out.println(squad.getName() + " want to go to: " + newCords + " but there are " + map.getSquadMap()[newCords.getX()][newCords.getY()].getName());
                if(RandomGenerator.random.nextInt(20) == 15 && countSquads(map) > 2){
                    squad = operation.connectSquads(squad, map.getSquadMap()[newCords.getX()][newCords.getY()], map.getSquadMap().length);
                    map.getSquadMap()[newCords.getX()][newCords.getY()] = squad;
                    if(squad == null) throw new IOException("SQUAD MERGER FAILED");
                }
                else {
                    operation.defence(operation.attackAll(squad), map.getSquadMap()[newCords.getX()][newCords.getY()]);
                    if (!map.getSquadMap()[newCords.getX()][newCords.getY()].ifExists()) field.changeTab(newCords.getX(), newCords.getY(), 0);
                    else operation.defence(operation.attackAll(map.getSquadMap()[newCords.getX()][newCords.getY()]), squad);
                }
                squad.setHasMoved(true);
            }
            else {
                System.out.println(squad.getName() + " decided to stay where they are" );
                changeField(map, field, newCords, initialCords);
                squad.setHasMoved(true);
            }
        }

        else {
            if (field.getFieldProperties()[newCords.getX()][newCords.getY()] == 1) {
                chooseEvent(squad, field, newCords);
            }

            if (field.getFieldProperties()[newCords.getX()][newCords.getY()] == 2) {
                operation.foodField(squad);
            }
            if(squad.ifExists()){
            squad.setSquadCoordinates(newCords);
            System.out.println("New coordinates: " + squad.getSquadCoordinates().toString());
            squad.setHasMoved(true);
            changeField(map, field, newCords, initialCords);
            if(map.checkSquadsInRange(squad) != null) operation.defence(operation.attackArchers(squad), map.checkSquadsInRange(squad));
            squadStarvationCheck(squad);
            }
        }
    }

    private int countSquads(Map map){
        SimulationStats.unitsQuantity = 0;
        int numberOfSquadsAlive = 0;
        for (int x = 0; x < map.getSquadMap().length; x++) {
            for (int y = 0; y < map.getSquadMap().length; y++) {
                if (map.getSquadMap()[x][y].getSquadId() != -1) {
                    numberOfSquadsAlive ++;
                    SimulationStats.unitsQuantity += map.getSquadMap()[x][y].getSquadQuantity();
                }
            }
        }
        return numberOfSquadsAlive;
    }

    private void chooseEvent(Squad squad, Field field, Coordinates coordinates){
        int randomEvent = RandomGenerator.random.nextInt(6) + 1;
        field.changeTab(coordinates.getX(), coordinates.getY(), 3);
        field.changeTab(squad.getSquadCoordinates().getX(), squad.getSquadCoordinates().getY(), 0);
        switch (randomEvent) {
            case 1 -> operation.bearAttack(squad);
            case 2 -> operation.banditsTheft(squad);
            case 3 -> operation.herdOfCattle(squad);
            case 4 -> operation.banditsAttack(squad);
            case 5 -> operation.bootsDisappearing(squad);
            case 6 -> operation.lostRecruits(squad);
            case 7 -> operation.funnyLookingPlant(squad);
            default -> System.out.println("error");
        }
    }

    private void changeField(Map map, Field field, Coordinates newCoordinates, Coordinates initialCords){
        field.changeTab(newCoordinates.getX(), newCoordinates.getY(), 3);
        field.changeTab(initialCords.getX(), initialCords.getY(), 0);
        map.changeMapCoordinates(map.getSquadMap()[initialCords.getX()][initialCords.getY()], newCoordinates, initialCords);
    }
}

