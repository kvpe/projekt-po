package pl.pwr.simulation.application;

import pl.pwr.simulation.map.Coordinates;
import pl.pwr.simulation.map.Field;
import pl.pwr.simulation.map.SimulationMap;
import pl.pwr.simulation.map.RandomGenerator;
import pl.pwr.simulation.units.Squad;
import pl.pwr.simulation.units.SquadOperations;
import pl.pwr.simulation.writer.SimulationPrinter;
import pl.pwr.simulation.writer.SimulationStats;
import pl.pwr.simulation.writer.SimulationWriter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SimulationRunner {


    SquadOperations operation = new SquadOperations();

    public void runSimulation(SimulationMap simulationMap, Field field, SimulationStats stats) throws Exception {

        SimulationPrinter printer = new SimulationPrinter();
        Squad squad;
        Coordinates newCords;
        int currentListIndex = 0;
        stats.setSquadsQuantity(countSquads(simulationMap));

        do {

            squad = simulationMap.getSquadList().get(currentListIndex);

            if(currentListIndex == 0){
                stats.increaseDuration();
                printer.print(stats);
                if (simulationMap.getSquadMap().length <= 10 && stats.getSquadsQuantity() <= 10) printer.printMap(field, simulationMap);
            }

            if(squad.ifExists()) {
                printer.print(squad, simulationMap.getSquadLocations());
                newCords = operation.move(field, squad, simulationMap.getSquadLocations());
                resolveSquadMove(simulationMap, field, squad, newCords, simulationMap.getSquadLocations().get(squad));
            }

            else if(!squad.ifExists()) simulationMap.getSquadList().remove(currentListIndex);

            checkList(simulationMap.getSquadList());
            currentListIndex++;
            if(currentListIndex >= simulationMap.getSquadList().size()) currentListIndex = 0;
            stats.setSquadsQuantity(countSquads(simulationMap));

        } while (stats.getSquadsQuantity() > 1);

        if (stats.getSquadsQuantity() == 1){
            System.out.println("After " + stats.getSimulationDuration() + " turns" + " winners are " + simulationMap.getSquadList().get(0).getName());
            new SimulationWriter(stats, simulationMap.getSquadList().get(0));
        }
        else{
            System.out.println("After " + stats.getSimulationDuration() + " turns everybody died");
            new SimulationWriter(stats);
        }
        printer.printResults(stats);

    }

    private void checkList(List<Squad> list){
        for(int i = 0; i < list.size(); i++){
            if(!list.get(i).ifExists()){
                list.remove(i);
                i -= 1;
                if(i < 0) i = 0;
            }
        }
    }
    private void squadStarvationCheck(Squad squad){
        if(operation.starvationCheck(squad) <= 0) operation.getEvents().get(0).apply(squad);
        else if(squad.ifExists()) System.out.println(squad.getName() + " don't lack food as they have " + squad.getFoodQuantity() + " food units left.");
    }

    private void resolveSquadMove(SimulationMap simulationMap, Field field, Squad squad, Coordinates newCords, Coordinates initialCords) throws Exception {

        if (simulationMap.getSquadMap()[newCords.getX()][newCords.getY()].ifExists()) {

            if (simulationMap.getSquadMap()[newCords.getX()][newCords.getY()] != squad) {
                System.out.println(squad.getName() + " want to go to: " + newCords + " but there are " + simulationMap.getSquadMap()[newCords.getX()][newCords.getY()].getName());
                if(RandomGenerator.random.nextInt(20) == 15 && countSquads(simulationMap) > 2){
                    squad = operation.connectSquads(squad, simulationMap.getSquadMap()[newCords.getX()][newCords.getY()], simulationMap.getSquadMap().length);
                    simulationMap.getSquadMap()[newCords.getX()][newCords.getY()] = squad;
                    simulationMap.getSquadList().add(squad);
                    simulationMap.getSquadLocations().put(squad, newCords);
                    if(squad == null) throw new IOException("SQUAD MERGER FAILED");
                }
                else {
                    operation.defence(operation.attackAll(squad), simulationMap.getSquadMap()[newCords.getX()][newCords.getY()]);
                    if (!simulationMap.getSquadMap()[newCords.getX()][newCords.getY()].ifExists()){
                        field.changeTab(newCords.getX(), newCords.getY(), 0);
                    }
                    else operation.defence(operation.attackAll(simulationMap.getSquadMap()[newCords.getX()][newCords.getY()]), squad);
                }
            }
            else {
                System.out.println(squad.getName() + " decided to stay where they are" );
                changeField(simulationMap, field, newCords, initialCords);
            }
        }

        else {
            if (field.getFieldProperties()[newCords.getX()][newCords.getY()] == 1) {
                chooseEvent(squad, field, simulationMap.getSquadLocations());
            }
            if (field.getFieldProperties()[newCords.getX()][newCords.getY()] == 2) {
                operation.getEvents().get(1).apply(squad);
            }
            if(squad.ifExists()){
            simulationMap.getSquadLocations().put(squad, newCords);
            changeField(simulationMap, field, newCords, initialCords);
            if(simulationMap.checkSquadsInRange(squad) != null) operation.defence(operation.attackArchers(squad), simulationMap.checkSquadsInRange(squad));

            squadStarvationCheck(squad);
            }
        }
    }

    private int countSquads(SimulationMap simulationMap){
        SimulationStats.unitsQuantity = 0;
        int numberOfSquadsAlive = 0;
        for (int x = 0; x < simulationMap.getSquadMap().length; x++) {
            for (int y = 0; y < simulationMap.getSquadMap().length; y++) {
                if (simulationMap.getSquadMap()[x][y].getSquadId() != -1) {
                    numberOfSquadsAlive ++;
                    SimulationStats.unitsQuantity += simulationMap.getSquadMap()[x][y].getSquadQuantity();
                }
            }
        }
        return numberOfSquadsAlive;
    }

    private void chooseEvent(Squad squad, Field field, Map<Squad, Coordinates> squadLocation){
        int randomEvent = RandomGenerator.random.nextInt(6) + 2;
        field.changeTab(squadLocation.get(squad).getX(), squadLocation.get(squad).getY(), 3);
        field.changeTab(squadLocation.get(squad).getX(), squadLocation.get(squad).getY(), 0);
        operation.getEvents().get(randomEvent).apply(squad);
    }

    private void changeField(SimulationMap simulationMap, Field field, Coordinates newCoordinates, Coordinates initialCords){
        field.changeTab(newCoordinates.getX(), newCoordinates.getY(), 3);
        field.changeTab(initialCords.getX(), initialCords.getY(), 0);
        simulationMap.changeMapCoordinates(simulationMap.getSquadMap()[initialCords.getX()][initialCords.getY()], newCoordinates, initialCords);
    }
}

