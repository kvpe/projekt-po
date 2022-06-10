package pl.pwr.simulation.units;

import pl.pwr.simulation.map.Coordinates;
import pl.pwr.simulation.map.Field;
import pl.pwr.simulation.map.RandomGenerator;


public class Squad implements FightResolver, RandomEventsInterface{
    private boolean exists;
    private boolean hasMoved;
    private Coordinates squadCoordinates;
    private Archer archer;
    private Soldier soldier;
    private Horseman horseman;

    private int squadQuantity;

    private int archersQuantity;
    private int soldiersQuantity;
    private int horsemanQuantity;

    private int archersHealth;
    private int soldiersHealth;
    private int horsemanHealth;

    private int archersDamage;
    private int meleeDamage;
    private double squadMapSpeed;

    private int squadHealth;
    private int foodQuantity;
    private String name;
    private int squadId;

    public Squad(int squadId){
        exists = false;
        name =" ";
        this.squadId = squadId;

    }
    public Squad(int archersQuantity, int soldiersQuantity, int horsemanQuantity, int foodQuantity, String name, int squadId, int mapSize, Coordinates coordinates){

        exists = true;
        this.foodQuantity = foodQuantity;
        this.name = name;
        this.squadId = squadId;
        this.archersQuantity = archersQuantity;
        this.soldiersQuantity = soldiersQuantity;
        this.horsemanQuantity = horsemanQuantity;
        this.squadCoordinates = coordinates;

        this.archer = new Archer(mapSize);
        this.soldier = new Soldier(mapSize);
        this.horseman = new Horseman(mapSize);

        archersHealth = archersQuantity * archer.getHealth();
        soldiersHealth = soldiersQuantity * soldier.getHealth();
        horsemanHealth = horsemanQuantity * horseman.getHealth();

        squadQuantity = archersQuantity + soldiersQuantity + horsemanQuantity;

        squadMapSpeed = (archersQuantity * archer.getMapSpeed() + soldiersQuantity * soldier.getMapSpeed()
         + horsemanQuantity * horseman.getMapSpeed())/(archersQuantity + soldiersQuantity +horsemanQuantity);

        squadHealth = archersQuantity * archer.getHealth() + soldiersQuantity * soldier.getHealth()
                + horsemanQuantity * horseman.getHealth();

        archersDamage = archersQuantity * archer.getDamage();

        meleeDamage = soldiersQuantity * soldier.getDamage() + horsemanQuantity * horseman.getDamage();


    }

    public String getName() {
        return name;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean ifExists(){
        return exists;
    }
    public int getSquadQuantity() {
        return squadQuantity;
    }

    public int getArchersDamage() {
        return archersDamage;
    }

    public int getMeleeDamage() {
        return meleeDamage;
    }

    public int getSquadHealth() {
        return squadHealth;
    }

    public double getSquadMapSpeed() {
        return squadMapSpeed;
    }

    public int getSquadId() {
        return squadId;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setSquadCoordinates(Coordinates squadCoordinates) {
        this.squadCoordinates = squadCoordinates;
    }

    public Coordinates getSquadCoordinates() {
        return squadCoordinates;
    }
    public void removeSquad(){
        squadHealth = 0;
        squadQuantity = 0;
        soldiersQuantity = 0;
        soldiersHealth = 0;
        archersQuantity = 0;
        archersHealth = 0;
        horsemanQuantity = 0;
        horsemanHealth = 0;
        squadId = -1;
        exists = false;
        name = " ";
    }
 //uproscic
    private void setStats(){
        squadQuantity = archersQuantity + soldiersQuantity + horsemanQuantity;
        squadHealth = archersHealth + soldiersHealth + horsemanHealth;
        archersDamage = archersQuantity * archer.getDamage();
        meleeDamage = soldiersQuantity * soldier.getDamage() + horsemanQuantity * horseman.getDamage();
        squadMapSpeed = (archersQuantity * archer.getMapSpeed() + soldiersQuantity * soldier.getMapSpeed() + horsemanQuantity * horseman.getMapSpeed()) / squadQuantity;
    }
    @Override
    public int attackAll() {
        System.out.println(name + " have attacked a different squad!");
        return archersDamage + meleeDamage;
    }

    public int attackArchers(){
        System.out.println(name + "'s archers have shot at a different squad!");
        return archersDamage;
    }

    @Override
    public void deathFromStarvation() {
        System.out.println("Food missing: " + -foodQuantity);
        int i;
        if(foodQuantity == 0){
            i = -1;
        }
        else i = foodQuantity + 1;
        for(; i <= 0; i++){
            if(horsemanQuantity > 0){
                horsemanQuantity--;
                System.out.println("Horseman died from hunger.");
                horsemanHealth = horsemanQuantity * (horseman.getHealth() + horseman.getHorseHealth());
            }
            else if(soldiersQuantity > 0){
                soldiersQuantity--;
                System.out.println("Soldier died from hunger.");
                soldiersHealth = soldiersQuantity * soldier.getHealth();
            }
            else if(archersQuantity > 0){
                archersQuantity--;
                System.out.println("Archer died from hunger.");
                archersHealth = archersQuantity * archer.getHealth();
            }
            setStats();
            if(squadQuantity == 0){
                System.out.println("Removing " + name + " as they starved to death.");
                removeSquad();
                break;
            }
        }
        foodQuantity = 0;
    }

    @Override
    public Coordinates move(Field field) {
        Coordinates squadPosition = squadCoordinates;
        int x, y, i = 0;
        int moveRange = (int) Math.floor(squadMapSpeed);
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
        }while((x == 0 && y == 0) || ((Math.pow(x,2) + Math.pow(y, 2)) > (Math.pow(moveRange, 2))) || (squadPosition.getX() + x < 0) || (squadPosition.getX() + x > (field.getSize() - 1)) || (squadPosition.getY() + y < 0) || (squadPosition.getY() + y  > (field.getSize()) - 1));
        squadPosition.setX(squadPosition.getX() + x);
        squadPosition.setY(squadPosition.getY() + y);
        return squadPosition;
    }

    @Override
    public Squad defence(Squad squad, int damage) {
        System.out.println(name + " have been attacked by a different squad!\nThey receive " + damage + " damage!");
        squad.chooseUnitsToDie(damage);
        return  squad;

    }

    @Override
    public int starvationCheck() {
        foodQuantity -= squadQuantity;
        return foodQuantity;
    }

    @Override
    public void chooseUnitsToDie(int damage) {
        if(horsemanQuantity > 0){
            horsemanHealth -= damage;
            if(horsemanHealth <= 0){
                damage = horsemanHealth * (-1);
                horsemanHealth = 0;
                horsemanQuantity = 0;
            }
            else{
                horsemanQuantity = (int)Math.ceil((float)horsemanHealth/(horseman.getHorseHealth()+horseman.getHealth())); //chyba git
                damage = 0;
            }
        }
        if(soldiersQuantity > 0 && damage > 0){
            soldiersHealth -= damage;
            if(soldiersHealth <= 0){
                damage = soldiersHealth * (-1);
                soldiersHealth = 0;
                soldiersQuantity = 0;
            }
            else{
                soldiersQuantity = (int)Math.ceil((float)soldiersHealth/soldier.getHealth());
                damage = 0;
            }
        }
        if (damage > 0) {
            archersHealth -= damage;
            if (archersHealth <= 0) {
                System.out.println(name + " have been defeated!");
                removeSquad();
            }
            else{
                archersQuantity = (int)Math.ceil((float)archersHealth/archer.getHealth());
            }
        }
        setStats();
    }

    @Override
    public Squad connectSquads(Squad squad1, Squad squad2) { //NOT IMPLEMENTED YET
            squad1.archersQuantity += squad2.archersQuantity;
            squad1.archersHealth += squad2.archersHealth;

            squad1.soldiersQuantity += squad2.soldiersQuantity;
            squad1.soldiersQuantity += squad2.soldiersHealth;

            squad1.horsemanQuantity += squad2.horsemanQuantity;
            squad1.horsemanHealth += squad2.horsemanHealth;

            squad1.foodQuantity += squad2.foodQuantity;

            squad1.name = squad1.name + " and " + squad2.name;

            squad1.squadHealth += squad2.squadHealth;
            //wydzielic do osobnej klasy i uzyc hermetyzacji
        //towrzyc nowy sklad
            System.out.println(squad1.name + " and " + squad2.name + " decided to merge!");

            squad1.setStats();

            squad2.removeSquad();

            return squad1;
    }

    @Override
    public void bearAttack() {
        System.out.println("The '" + name + "' has been attacked by a powerful Bear!");
        Unit bear = new Unit(1200, 200, 0) {
        };
        while(bear.getHealth() > 0 && squadHealth > 0){
            chooseUnitsToDie(bear.getDamage());
            if(squadHealth <=0 ) break;
            bear.setHealth(bear.getHealth() - archersDamage - meleeDamage);
        }
        if(squadHealth > 0){
            foodQuantity += squadQuantity * 6;
            System.out.println(" '" + name + "' has bested the bear! As their reward they receive " + squadQuantity * 6 + " food units!");
        }
        else{
            System.out.println("They have been wiped by the bear!");
        }
    }

    @Override
    public void banditsTheft() {
        if(foodQuantity > 0) {
            int foodStolen = RandomGenerator.random.nextInt((int)Math.ceil (foodQuantity * 0.25)) + 1;
            System.out.println("At night bandits sneaked into the '" + name + "'s camp and they took "
                    + foodStolen + " units of food!");
            foodQuantity -= foodStolen;
        }
        else System.out.println("Bandits came to steal some food, but they found nothing");
    }

    @Override
    public void lostRecruits() {
        int numberOfRecruits = RandomGenerator.random.nextInt((int)Math.ceil(squadQuantity * 0.1)) + 1;
        System.out.println("'" + name + "' have come across " + numberOfRecruits +" lost recruits!"
        + "\nThey joined the'" + name + "' and provided them " + numberOfRecruits * 10 + " units of food.");
        foodQuantity += numberOfRecruits * 10;
        soldiersQuantity += numberOfRecruits;
        setStats();
    }
    @Override
    public void herdOfCattle() {
        int foodFound = RandomGenerator.random.nextInt(squadQuantity * 10);
        System.out.println("Squad '" + name + "' came across a herd of cattle, their food increases by " + foodFound + ".");
        foodQuantity += foodFound;
    }

    @Override
    public void banditsAttack() {
        Unit bandit = new Unit(40, 40, 0) {
        };
        int initialNumberOfBandits = RandomGenerator.random.nextInt((int)Math.ceil(squadQuantity * 0.9)) + 1 ;
        int numberOfBandits = initialNumberOfBandits;
        int banditsHealth = bandit.getHealth() * initialNumberOfBandits;

        System.out.println("'" + name + "' have come across " + initialNumberOfBandits + " bandits!");
        do {
            chooseUnitsToDie(bandit.getDamage() * numberOfBandits);
            if(squadHealth <=0 ) break;
            banditsHealth -= attackAll();
            numberOfBandits =  (int)Math.ceil((float)banditsHealth/bandit.getHealth());
        }while(squadHealth > 0 && banditsHealth > 0);
        if(squadHealth <= 0){
            System.out.println("They have lost to the bandits!");
        }
        else {
            System.out.println("Squad '" + name + "' was attacked by " + initialNumberOfBandits + " bandits!" +
                    " They lose some units as a result, but after killing bandits they gain " + initialNumberOfBandits * 4 + " food.");
            foodQuantity += initialNumberOfBandits * 4;
        }
    }

    @Override
    public void bootsDisappearing() {

        System.out.println("Squad '" + name + "' woke up and found out their boots were stolen. Their velocity has been reduced.");
        squadMapSpeed /= 1.25;
        if(squadMapSpeed < 1) squadMapSpeed = 1;
    }

    @Override
    public void foodField() {

        int amountOfFoodFound = RandomGenerator.random.nextInt(squadQuantity * 3) + 1;

        System.out.println("'" + name + "' has come across a food field, they yield "
                + amountOfFoodFound + " food units.");
        foodQuantity += amountOfFoodFound;
    }

    @Override
   public void funnyLookingPlant() {

        int damagePowerUp = RandomGenerator.random.nextInt(5) +1;
        int speedPowerUp = RandomGenerator.random.nextInt((int)Math.floor(1)) +1;

        System.out.println("'" + name + "' has found a funny looking plant. After eating it, they have become faster and stronger.");

        archer.setDamage(archer.getDamage() + damagePowerUp);
        soldier.setDamage(soldier.getDamage() + damagePowerUp);
        horseman.setDamage(horseman.getDamage() + damagePowerUp);

        archer.setMapSpeed(archer.getMapSpeed() + speedPowerUp);
        soldier.setMapSpeed(soldier.getMapSpeed() + speedPowerUp);
        horseman.setMapSpeed(horseman.getMapSpeed() + speedPowerUp);

        setStats();
   }
}

