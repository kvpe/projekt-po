package pl.pwr.simulation.units;

import pl.pwr.simulation.map.Coordinates;
import pl.pwr.simulation.map.Field;
import pl.pwr.simulation.map.RandomGenerator;
import pl.pwr.simulation.writer.SimulationStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SquadOperations
 {
     private final List<Event> events = new ArrayList<>();

     public SquadOperations(){
         Event deathFromStarvation = squad -> {
             System.out.println("Food missing: " + -squad.getFoodQuantity());
             int i, dead = 0;
             if(squad.getFoodQuantity() == 0){
                 i = -1;
             }
             else i = squad.getFoodQuantity() + 1;
             for(; i <= 0; i++){
                 if(squad.getHorsemanQuantity() > 0){
                     squad.setHorsemanQuantity(squad.getHorsemanQuantity() - 1);
                     dead++;
                     squad.setHorsemanHealth(squad.getHorsemanQuantity() * (squad.getHorseman().getHealth()));
                 }
                 else if(squad.getSoldiersQuantity() > 0){
                     squad.setSoldiersQuantity(squad.getSoldiersQuantity() - 1);
                     dead++;
                     squad.setSoldiersHealth(squad.getSoldiersQuantity() * squad.getSoldier().getHealth());
                 }
                 else if(squad.getArchersQuantity() > 0){
                     squad.setArchersQuantity(squad.getArchersQuantity() - 1);
                     dead++;
                     squad.setArchersHealth(squad.getArchersQuantity() * squad.getArcher().getHealth());
                 }
                 squad.setStats();
                 if(squad.getSquadQuantity() == 0){
                     System.out.println(dead + " of " + squad.getName() + "'s soldiers have starved to death");
                     System.out.println("Removing " + squad.getName() + " as they starved to death.");
                     squad.removeSquad();
                     break;
                 }
             }
             SimulationStats.howManyStarved += dead;
             SimulationStats.unitsQuantity -= dead;
             if(squad.ifExists()) System.out.println(dead + " of " + squad.getName() + "'s soldiers have starved to death");
             squad.setFoodQuantity(0);
         };
         events.add(deathFromStarvation);

         Event foodField = squad -> {
             int amountOfFoodFound = RandomGenerator.random.nextInt(squad.getSquadQuantity() * 3) + 1;

             System.out.println(squad.getName() + " have come across a food field, they yield "
                     + amountOfFoodFound + " food units.");
             squad.setFoodQuantity(squad.getFoodQuantity() + amountOfFoodFound);
         };
         events.add(foodField);

         Event bearAttack = squad -> {
             System.out.println("The " + squad.getName() + " have been attacked by a powerful Bear!");
             Unit bear = new Unit() {
                 @Override
                 public int getDamage() {
                     return 200;
                 }

                 @Override
                 public int getHealth() {
                     return 1200;
                 }
             };
             int bearHealth = 1200;
             while(bearHealth > 0 && squad.getSquadHealth() > 0){
                 chooseUnitsToDie(bear.getDamage(), squad);
                 if(squad.getSquadHealth() <=0 ) break;
                 bearHealth -= squad.getArchersDamage() + squad.getMeleeDamage();
             }
             if(squad.getSquadHealth() > 0){
                 squad.setFoodQuantity(squad.getFoodQuantity() + squad.getSquadQuantity() * 6);
                 System.out.println(squad.getName() + " have bested the bear! As their reward they receive " + squad.getSquadQuantity() * 6 + " food units!");
             }
         };
         events.add(bearAttack);

         Event banditAttack = squad -> {
             Unit bandit = new Unit() {
                 @Override
                 public int getDamage() {
                     return 40;
                 }

                 @Override
                 public int getHealth() {
                     return 40;
                 }
             };
             int initialNumberOfBandits = RandomGenerator.random.nextInt((int)Math.ceil(squad.getSquadQuantity() * 0.9)) + 1 ;
             int numberOfBandits = initialNumberOfBandits;
             int banditsHealth = bandit.getHealth() * initialNumberOfBandits;

             System.out.println(squad.getName() + " have come across " + initialNumberOfBandits + " bandits!");
             do {
                 chooseUnitsToDie(bandit.getDamage() * numberOfBandits, squad);
                 if(squad.getSquadHealth() <=0 ) break;
                 banditsHealth -= attackAll(squad);
                 numberOfBandits =  (int)Math.ceil((float)banditsHealth/bandit.getHealth());
             }while(squad.getSquadHealth() > 0 && banditsHealth > 0);
             if(squad.getSquadHealth() <= 0){
                 System.out.println("They have lost to the bandits!");
             }
             else {
                 System.out.println(squad.getName() + " have been attacked by " + initialNumberOfBandits + " bandits!" +
                         " They lose some units as a result, but after killing bandits they gain " + initialNumberOfBandits * 4 + " food.");
                 squad.setFoodQuantity(squad.getFoodQuantity() + initialNumberOfBandits * 4);
             }
         };
         events.add(banditAttack);

         Event banditTheft = squad -> {
             if(squad.getFoodQuantity() > 0) {
                 int foodStolen = RandomGenerator.random.nextInt((int)Math.ceil (squad.getFoodQuantity() * 0.25)) + 1;
                 System.out.println("At night bandits sneaked into the " + squad.getName() + "'s camp and they took "
                         + foodStolen + " units of food!");
                 squad.setFoodQuantity(squad.getFoodQuantity() - foodStolen);
             }
             else System.out.println("Bandits came to steal some food, but they found nothing.");
         };
         events.add(banditTheft);

         Event bootsDisappearing = squad -> {
             System.out.println(squad.getName() + " woke up and found out their boots were stolen. Their velocity has been reduced.");
             squad.setSquadMapSpeed(squad.getSquadMapSpeed()/1.25);
             if(squad.getSquadMapSpeed() < 1) squad.setSquadMapSpeed(1);
         };
         events.add(bootsDisappearing);

         Event funnyLookingPlant = squad -> {
             int damagePowerUp = RandomGenerator.random.nextInt(10) +1;
             int healthPowerUp = RandomGenerator.random.nextInt(20) +1;

             System.out.println(squad.getName() + " have found a funny looking plant. After eating it, they have become faster and stronger.");

             squad.setArcher(new Unit() {
                 @Override
                 public int getDamage() {
                     return 50 + damagePowerUp;
                 }

                 @Override
                 public int getHealth() {
                     return 50 + healthPowerUp;
                 }
             });

             squad.setSoldier(new Unit() {
                 @Override
                 public int getDamage() {
                     return 45 + damagePowerUp;
                 }

                 @Override
                 public int getHealth() {
                     return 100 + healthPowerUp;
                 }
             });

             squad.setHorseman(new Unit() {
                 @Override
                 public int getDamage() {
                     return 35 + damagePowerUp;
                 }

                 @Override
                 public int getHealth() {
                     return 360 + damagePowerUp;
                 }
             });

             squad.setStats();
         };
         events.add(funnyLookingPlant);

         Event lostRecruits = squad -> {
             int numberOfRecruits = RandomGenerator.random.nextInt((int)Math.ceil(squad.getSquadQuantity() * 0.1)) + 1;
             System.out.println(squad.getName() + " have come across " + numberOfRecruits +" lost recruits!"
                     + "\nThey joined the " + squad.getName() + " and provided them " + numberOfRecruits * 10 + " units of food.");
             squad.setFoodQuantity(squad.getFoodQuantity() + numberOfRecruits * 10);
             squad.setSoldiersQuantity(squad.getSoldiersQuantity() + numberOfRecruits);
             SimulationStats.unitsQuantity += numberOfRecruits;
             squad.setStats();
         };
         events.add(lostRecruits);

         Event herdOfCattle = squad -> {
             int foodFound = RandomGenerator.random.nextInt(squad.getSquadQuantity() * 10);
             System.out.println("Squad " + squad.getName() + " came across a herd of cattle, their food increases by " + foodFound + ".");
             squad.setFoodQuantity(squad.getFoodQuantity() + foodFound);
         };
         events.add(herdOfCattle);
     }

     public int attackAll(Squad squad) {
        System.out.println(squad.getName() + " have attacked a different squad!");
        return squad.getArchersDamage() + squad.getMeleeDamage();
    }

     public int attackArchers(Squad squad){
        System.out.println(squad.getName() + "'s archers have shot at a different squad!");
        return squad.getArchersDamage();
    }

     public List<Event> getEvents() {
         return events;
     }

     public Coordinates move(Field field, Squad squad, Map<Squad, Coordinates> squadLocation) {
        Coordinates coordinates = new Coordinates();
        int x, y, i = 0;
        int moveRange = (int) Math.floor(squad.getSquadMapSpeed());
        if(moveRange < 1) moveRange = 1;
        do{
            x = (int) Math.round(RandomGenerator.random.nextDouble(moveRange) - RandomGenerator.random.nextDouble(moveRange));
            y = (int) Math.round(RandomGenerator.random.nextDouble(moveRange) - RandomGenerator.random.nextDouble(moveRange));
            i++;
            if(i == 10){
                x = 0;
                y = 0;
                break;
                }
        }while((x == 0 && y == 0) || ((Math.pow(x,2) + Math.pow(y, 2)) > (Math.pow(moveRange, 2))) || (squadLocation.get(squad).getX() + x < 0) || (squadLocation.get(squad).getX() + x > (field.getSize() - 1)) || (squadLocation.get(squad).getY() + y < 0) || (squadLocation.get(squad).getY() + y  > (field.getSize()) - 1));
        coordinates.setX(squadLocation.get(squad).getX() + x);
        coordinates.setY(squadLocation.get(squad).getY() + y);
        return coordinates;
    } 

     public void defence(int damage, Squad squad) {
        System.out.println(squad.getName() + " have been attacked by a different squad!\nThey receive " + damage + " damage!");
        chooseUnitsToDie(damage, squad);
    } 

     public int starvationCheck(Squad squad) {
        squad.setFoodQuantity(squad.getFoodQuantity() - squad.getSquadQuantity());
        return squad.getFoodQuantity();
    } 

     public void chooseUnitsToDie(int damage, Squad squad) {
        if(squad.getHorsemanQuantity() > 0){
            squad.setHorsemanHealth(squad.getHorsemanHealth() - damage);
            if(squad.getHorsemanHealth() <= 0){
                damage = squad.getHorsemanHealth() * (-1);
                squad.setHorsemanHealth(0);
                squad.setHorsemanQuantity(0);
            }
            else{
                squad.setHorsemanQuantity((int)Math.ceil((float)squad.getHorsemanHealth()/(squad.getHorseman().getHealth())));
                damage = 0;
            }
        }
        if(squad.getSoldiersQuantity() > 0 && damage > 0){
            squad.setSoldiersHealth(squad.getSoldiersHealth() - damage);
            if(squad.getSoldiersHealth() <= 0){
                damage = squad.getSoldiersHealth() * (-1);
                squad.setSoldiersHealth(0);
                squad.setSoldiersQuantity(0);
            }
            else{
                squad.setSoldiersQuantity((int)Math.ceil((float)squad.getSoldiersHealth()/squad.getSoldier().getHealth()));
                damage = 0;
            }
        }
        if (damage > 0) {
            squad.setArchersHealth(squad.getArchersHealth() - damage);
            if (squad.getArchersHealth() <= 0) {
                squad.setArchersQuantity(0);
            }
            else{
                squad.setArchersQuantity((int)Math.ceil((float)squad.getArchersHealth()/squad.getArcher().getHealth()));
            }
        }
        SimulationStats.howManyKilled += (squad.getSquadQuantity() - (squad.getArchersQuantity() + squad.getSoldiersQuantity() + squad.getHorsemanQuantity()));
        squad.setStats();
        if(!squad.ifExists() && squad.getSquadHealth() <= 0 ){
            System.out.println(squad.getName() + " have been defeated!");
            squad.removeSquad();
        }
        if(squad.ifExists() && squad.getSquadHealth() <= 0 ){
            System.out.println(squad.getName() + " have been defeated!");
            squad.removeSquad();
        }
    } 

     public Squad connectSquads(Squad squad1, Squad squad2, int mapSize) {
        System.out.println(squad1.getName() + " and " + squad2.getName() + " decided to merge!");

        Squad squad = new Squad(
                squad1.getArchersQuantity() + squad2.getArchersQuantity(),
                squad1.getSoldiersQuantity() + squad2.getSoldiersQuantity(),
                squad1.getHorsemanQuantity() + squad2.getHorsemanQuantity(),
                squad1.getFoodQuantity() + squad2.getFoodQuantity(),
                squad1.getName() + " and " + squad2.getName(),
                squad1.getSquadId(),
                mapSize,
                false

        );
        squad1.removeSquad();
        squad2.removeSquad();
        squad.setStats();

        return squad;
    } 

}
