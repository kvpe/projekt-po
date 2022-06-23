package pl.pwr.simulation.map;

import pl.pwr.simulation.units.Squad;
import pl.pwr.simulation.units.generator.RandomSquadGeneration;
import pl.pwr.simulation.writer.SimulationStats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationMap
{
    private List<Squad> squadList = new ArrayList<>();
    private Map <Squad, Coordinates> squadLocations;
    private final Squad[][] squadMap;
    
    public SimulationMap(Field field, int maxSquadSize) throws IOException {

        int squadId = 0;
        RandomSquadGeneration squadGeneration = new RandomSquadGeneration();
        squadLocations = new HashMap<>();
        squadMap = new Squad[field.size][field.size];
        
        for(int x = 0; x < field.size; x++){
            for(int y = 0; y < field.size; y++){
                if(field.fieldProperties[x][y] == 3){
                    
                    Coordinates coordinates = new Coordinates(x, y);

                    squadList.add(squadGeneration.randomSquad(maxSquadSize,field.size, squadId));
                    squadLocations.put(squadList.get(squadId), coordinates);
                    squadMap[x][y] = squadList.get(squadId);

                    SimulationStats.unitsQuantity += squadList.get(squadId).getSquadQuantity();
                
                    squadId++;
                }
                else{
                    Squad squad = new Squad(-1);
                    squadMap[x][y] = squad;
                }
            }
        }
    }
    
    public List<Squad> getSquadList() {
        return squadList;
    }

    public Map<Squad, Coordinates> getSquadLocations() {
        return squadLocations;
    }

    public Squad[][] getSquadMap() {
        return squadMap;
    }

    public void changeMapCoordinates(Squad squad, Coordinates newCords, Coordinates initialCords) {
        squadMap[initialCords.getX()][initialCords.getY()] = squadMap[newCords.getX()][newCords.getY()];
        squadMap[newCords.getX()][newCords.getY()] = squad;
    }

    public Squad checkSquadsInRange(Squad squad) {

        int x, y;
        for (x = getRangeIterators(squad, true)[0]; x < getRangeIterators(squad, true)[1]; x++) {

            y = getRangeIterators(squad, false)[0];

            for (; y < getRangeIterators(squad, false)[1]; y++) {
                if(squadMap[x][y].ifExists() && squadMap[x][y].getSquadId() != squad.getSquadId()) return squadMap[x][y];
            }
        }
        return null;
    }

    private int[] getRangeIterators(Squad squad, boolean isX){
        int x;
        int[] iterators = new int[2];
        if(isX) x = squadLocations.get(squad).getX();
        else  x = squadLocations.get(squad).getY();

        iterators[0] = x - squadMap.length/5;
        iterators[1] = x + squadMap.length/5;

        if (x - squadMap.length/5 < 0) iterators[0] = 0;
        if (x + squadMap.length/5 > squadMap.length) iterators[1] = squadMap.length;

        return iterators;
    }


}
