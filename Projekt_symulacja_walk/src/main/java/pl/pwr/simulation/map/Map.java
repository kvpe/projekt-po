package pl.pwr.simulation.map;

import pl.pwr.simulation.units.Squad;
import pl.pwr.simulation.units.generator.RandomSquadGeneration;
import pl.pwr.simulation.writer.SimulationStats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map
{
    private final List<Squad> squadList = new ArrayList<>();
    private final Squad[][] squadMap;

    public Squad[][] getSquadMap() {
        return squadMap;
    }

    public void changeMapCoordinates(Squad squad, Coordinates newCords, Coordinates initialCords) {
        squadMap[initialCords.getX()][initialCords.getY()] = squadMap[newCords.getX()][newCords.getY()];
        squadMap[newCords.getX()][newCords.getY()] = squad;
    }

    public Map(Field field, int maxSquadSize, int amountOfSquads) throws IOException {
        int squadId = 0;
        RandomSquadGeneration squadGeneration = new RandomSquadGeneration();

        squadMap = new Squad[field.size][field.size];
        for(int i = 0; i < field.size; i++) {
            for (int j = 0; j < field.size; j++) {
                Squad squad = new Squad(-1);
                squadMap[i][j] = squad;
            }
        }

        Coordinates coordinates = new Coordinates();
        for(int i = 0; i < field.size; i++){
            for(int j = 0; j < field.size; j++){
                if(field.fieldProperties[i][j] == 3){
                    coordinates.setX(i);
                    coordinates.setY(j);

                    squadList.add(squadGeneration.randomSquad(maxSquadSize,field.size, squadId, coordinates ));
                    SimulationStats.unitsQuantity += squadList.get(squadId).getSquadQuantity();
                    squadMap[i][j] = squadList.get(squadId);
                    System.out.println(
                            "ID: " + squadList.get(squadId).getSquadId() +
                            "\nSquad quantity: " + squadList.get(squadId).getSquadQuantity() +
                            "\nArchers damage: " + squadList.get(squadId).getArchersDamage() +
                            "\nMelee damage: " +squadList.get(squadId).getMeleeDamage() +
                            "\nSquad health:" +squadList.get(squadId).getSquadHealth() +
                            "\nMap speed: " + squadList.get(squadId).getSquadMapSpeed()+
                                    "\nName: " +squadList.get(squadId).getName()
                    );
                    squadId++;
                }
            }
        }
    }

    public Squad checkSquadsInRange(Squad squad) {
        int x = squad.getSquadCoordinates().getX();
        int y = squad.getSquadCoordinates().getY();
        int iStart, iEnd, jStart, jEnd;

        iStart = x - squadMap.length/5;
        iEnd = x + squadMap.length/5;

        if (x - squadMap.length/5 < 0) iStart = 0;
        if (x + squadMap.length/5 > squadMap.length) iEnd = squadMap.length;

        for (; iStart < iEnd; iStart++) {
            jStart = y - squadMap.length/5;
            jEnd = y + squadMap.length/5;
            if (y - squadMap.length/5 < 0) jStart = 0;
            if (y + squadMap.length/5 > squadMap.length) jEnd = squadMap.length;
            for (; jStart < jEnd; jStart++) {
                if(squadMap[iStart][jStart].getSquadId() != -1 && squadMap[iStart][jStart].getSquadId() != squad.getSquadId()){
                    return squadMap[iStart][jStart];
                }
            }
        }
        return null;
    }

}
