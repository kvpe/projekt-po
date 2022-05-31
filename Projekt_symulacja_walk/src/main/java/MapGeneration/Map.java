package MapGeneration;

import Units.Squad;

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
            CurrentId squadId = new CurrentId();
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
                    if(field.tab[i][j] == 3){
                        coordinates.setX(i);
                        coordinates.setY(j);
                         //max size of a squad will be set by a user
                        squadList.add(squadGeneration.randomSquad(30,field.size, squadId.getId(), coordinates ));
                        squadList.get(squadId.getId()).setSquadCoordinates(coordinates);
                    squadMap[i][j] = squadList.get(squadId.getId());
                    System.out.println(
                            "\nID: " + squadList.get(squadId.getId()).getSquadId() +
                            "\nSquad quantity: " + squadList.get(squadId.getId()).getSquadQuantity() +
                            "\nArchers damage: " + squadList.get(squadId.getId()).getArchersDamage()+
                            "\nMelee damage: " +squadList.get(squadId.getId()).getMeleeDamage()+
                            "\nSquad health:" +squadList.get(squadId.getId()).getSquadHealth()+
                            "\nMap speed: " + squadList.get(squadId.getId()).getSquadMapSpeed()
                    );
                        squadId.increaseId();
                    }
                }
            }

        }
}
