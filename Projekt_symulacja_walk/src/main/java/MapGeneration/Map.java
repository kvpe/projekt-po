package MapGeneration;

import Units.Squad;

import java.io.FileNotFoundException;
import java.util.UUID;

public class Map
{
    private Squad[][] squadMap;
        public Map(Field field) throws FileNotFoundException {
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
                    Squad squad = new Squad(UUID.randomUUID());
                    squadMap[i][j] = squad;
                }
            }
            for(int i = 0; i < field.size; i++){
                for(int j = 0; j < field.size; j++){
                    if(field.tab[i][j] == 3){
                        //UUID squadId = UUID.randomUUID(); te dwie linijki raczej juz sie nie przydadzą
                        //Squad squad = new Squad(5, 5, 5,70,"mocarze",squadId);
                        Squad squad = squadGeneration.randomSquad(30,field.size); //max size ma byc do wprowadzenia przez uzytkownika
                        squadMap[i][j] = squad;
                        System.out.println("wojownikow jest: " + squad.getSquadQuantity() +
                                "\nobrazenia poteznych lucznikow: " + squad.getArchersDamage()+
                                "\nobrazenia melasow: " +squad.getMeleeDamage()+
                                "\nzdrowie druzyny:" +squad.getSquadHealth()+
                                "\npopierdalaja z predkoscia: " + squad.getSquadMapSpeed()+
                                "\nich ID to: " + squad.getSquadId() +
                                "\nA nazwa to: " + squad.getName());
                    }
                }
            }
        }



}
