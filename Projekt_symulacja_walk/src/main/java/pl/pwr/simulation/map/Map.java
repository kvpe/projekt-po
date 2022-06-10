package pl.pwr.simulation.map;

import pl.pwr.simulation.map.generator.RandomEventsGenerator;
import pl.pwr.simulation.map.generator.RandomFoodGenerator;
import pl.pwr.simulation.map.generator.SquadSpawnPointGenerator;
import pl.pwr.simulation.units.Archer;
import pl.pwr.simulation.units.Squad;
import pl.pwr.simulation.units.generator.RandomSquadGeneration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map
{
    private List<Squad> squadList = new ArrayList<>();
    private Squad[][] squadMap;

    public Squad[][] getSquadMap() {
        return squadMap;
    }

    public void changeMapCoordinates(Squad squad, Coordinates newCords, int x, int y) {
        squadMap[x][y] = squadMap[newCords.getX()][newCords.getY()];
        squadMap[newCords.getX()][newCords.getY()] = squad;
    }

    public Map(Field field) throws IOException {
            int squadId = 0;
            RandomFoodGenerator randomFoodGenerator = new RandomFoodGenerator();
            RandomEventsGenerator randomEventsGenerator = new RandomEventsGenerator();
            SquadSpawnPointGenerator spawnPointGenerator = new SquadSpawnPointGenerator();
            RandomSquadGeneration squadGeneration = new RandomSquadGeneration();

            randomFoodGenerator.randomise(field);
            randomEventsGenerator.randomise(field);
            spawnPointGenerator.randomise(field);

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
                         //max size of a squad will be set by a user
                        squadList.add(squadGeneration.randomSquad(30,field.size, squadId, coordinates ));
                        squadList.get(squadId).setSquadCoordinates(coordinates);
                        squadMap[i][j] = squadList.get(squadId);
                        System.out.println(
                            "ID: " + squadList.get(squadId).getSquadId() +
                            "\nSquad quantity: " + squadList.get(squadId).getSquadQuantity() +
                            "\nArchers damage: " + squadList.get(squadId).getArchersDamage() +
                            "\nMelee damage: " +squadList.get(squadId).getMeleeDamage() +
                            "\nSquad health:" +squadList.get(squadId).getSquadHealth() +
                            "\nMap speed: " + squadList.get(squadId).getSquadMapSpeed() +
                                    "\nName: " +squadList.get(squadId).getName()
                    );
                        squadId++;
                    }
                }
            }
        }

        public Squad checkSquadsInRange(Squad squad) { //po ruchu sprawdza i atakuje squad w zasięgu (wybiera pierwszy inny kiedy kilka)
            int x = squad.getSquadCoordinates().getX();
            int y = squad.getSquadCoordinates().getY();
            int iStart, iEnd, jStart, jEnd;

            iStart = x - Archer.range;
            iEnd = x + Archer.range;

            if (x - Archer.range < 0) iStart = 0;
            if (x + Archer.range > squadMap.length) iEnd = squadMap.length;

            for (; iStart < iEnd; iStart++) {
                jStart = y - Archer.range;
                jEnd = y + Archer.range;
                if (y - Archer.range < 0) jStart = 0;
                if (y + Archer.range > squadMap.length) jEnd = squadMap.length;
                for (; jStart < jEnd; jStart++) {
                  if(squadMap[iStart][jStart].getSquadId() != -1 && squadMap[iStart][jStart].getSquadId() != squad.getSquadId()){
                    return squadMap[iStart][jStart];
                  }
                }
            }
            return null;
        }

}
