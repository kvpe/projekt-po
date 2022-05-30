package Units;

import MapGeneration.Coordinates;
import MapGeneration.Field;

import java.util.Random;
import java.util.UUID;

public class Squad implements FightResolver, RandomEventsInterface{
    private boolean exists;
    private Coordinates squadCoordinates;
    private Archer archer;
    private Soldier soldier;
    private Horseman horseman;

    private int squadQuantity; //mysle ze obliczane dynamicznie

    private int archersQuantity;
    private int soldiersQuantity;
    private int horsemanQuantity;

    private int archersHealth;
    private int soldiersHealth; // dodalem na potrzeby metod
    private int horsemanHealth;

    private int archersDamage; //te 3 powinny byc chyba obliczane na biezaco
    private int meleeDamage; //w sensie tutaj np: = soldiersQuantity * soldier.getDamage() + horsemanQuanity * horseman.getDamage();
    private double squadMapSpeed;

    private int squadHealth;
    private int foodQuantity;
    private String name;
    private UUID squadId; //nie uzywalem tego id poki co wgl bo w sumie nie wiem jak xd

    public Squad(UUID squadId){
        exists = false;
        name =" ";
        this.squadId = squadId;

    }
    public Squad(int archersQuantity, int soldiersQuantity, int horsemanQuantity, int foodQuantity, String name, UUID squadId, int mapSize){

        exists = true;
        this.foodQuantity = foodQuantity;
        this.name = name;
        this.squadId = squadId;
        this.archersQuantity = archersQuantity;
        this.soldiersQuantity = soldiersQuantity;
        this.horsemanQuantity = horsemanQuantity;


        Archer archer = new Archer(mapSize);
        Soldier soldier = new Soldier(mapSize);
        Horseman horseman = new Horseman(mapSize);

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

    public String getName() {
        return name;
    }

    public UUID getSquadId() {
        return squadId;
    }

    public int getFoodQuantity() {
        return foodQuantity;
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
        exists = false;
        name = " ";
    }

    @Override
    public int attack() {
        return archersDamage + meleeDamage;
    }

    @Override
    public Squad deathFromStarvation(Squad squad) { //jeśli starvationCheck zwraca liczbe ujemna to wykonaj
        for(int i = squad.foodQuantity; i >= 0; i++){
            if(squad.horsemanQuantity > 0){
                squad.horsemanQuantity--;
                squad.horsemanHealth = squad.horsemanQuantity * (horseman.getHealth() + horseman.getHorseHealth());
            }
            else if(soldiersQuantity > 0){
                squad.soldiersQuantity--;
                squad.soldiersHealth = squad.soldiersQuantity * soldier.getHealth(); //zdrowie obliczane w ten sposob bo po smierci
            }   //jednego zolnierza z glodu, reszta bedzie miala full hp, bo nie ucierpiala wczesniej w walce
            else if(archersQuantity > 0){
                squad.archersQuantity--;
                squad.archersHealth = squad.archersQuantity * archer.getHealth();
            }
            else {
                squad.exists = false;
                break;
            }
        }
        return squad;
    } //tez powinno byc git

    @Override
    public Coordinates move(Field field, Squad squad) {
            //tu jakos randomowo wybrac komorke gdzie sie poruszyc
        //uwzglednic to, zeby nie wyszedl za mape;
        Coordinates squadPosition = new Coordinates(); //nowa klasa, po prostu wspolrzedna x i y;
        Random random = new Random();
        int x = 0, y = 0;
        int moveRange = (int) Math.floor(squad.squadMapSpeed);//x^2+y^2 <= moveRange ^2
        do{
            x = random.nextInt(moveRange + moveRange) - moveRange; //formuła losowania z neta
            y = random.nextInt(moveRange + moveRange) - moveRange; //losuje w teorii z przedzialu -moveRAnge do moveRange
        }while((x^2+y^2) > (moveRange^2) && (x > 0 && y > 0)); //mysle ze spoko ale do przetestowania
        squadPosition.setX(x);
        squadPosition.setY(y);
//        field.getTab()[squad.squadCoordinates.getX()][squad.squadCoordinates.getY()] = 0;
//        field.getTab()[x][y] = 3; imo to nie tutaj bo moze napisac pozycje innego skladu, raej powinno byc tam gdzie sie wywoluje te metode
        return squadPosition;
    }

    @Override
    public Squad defence(Squad squad, int damage, boolean firstStrike) { //jako dmg przekazujemy attack(squad)
        if(firstStrike) damage = damage/2;
        squad.chooseUnitsToDie(squad, damage);
        return  squad;
        //squad broniący porzed pierwszym atakiem powinien mieć jakiś booster bo inaczej prawie zawsze przegra
    }

    @Override
    public int starvationCheck(Squad squad) {
        if(squad.foodQuantity < squad.squadQuantity){
            return -squad.foodQuantity;
        }
        else squad.foodQuantity -= squad.squadQuantity;
        return squad.foodQuantity;
    }

    @Override
    public Squad chooseUnitsToDie(Squad squad, int damage) { //chyba jest poprawnie, mozesz przeanalizowac
         //mysle ze ma sens zeby umieraly w tej kolejnosci, bo z losowaniem bylaby masa pierdolenia
        if(squad.horsemanQuantity > 0){
            squad.horsemanHealth -= damage;
            if(squad.horsemanHealth <= 0){
                damage = squad.horsemanHealth * (-1);
                squad.horsemanHealth = 0;
                squad.horsemanQuantity = 0;
            }
            else{
                squad.horsemanQuantity = (int)Math.ceil((float)squad.horsemanHealth/(horseman.getHorseHealth()+squad.horseman.getHealth())); //chyba git
                damage = 0;
            }
        }
        if(squad.soldiersQuantity > 0 && damage > 0){
            squad.soldiersHealth -= damage;
            if(squad.soldiersHealth <= 0){
                damage = squad.soldiersHealth * (-1);
                squad.soldiersHealth = 0;
                squad.soldiersQuantity = 0;
            }
            else{
                squad.soldiersQuantity = (int)Math.ceil((float)squad.soldiersHealth/soldier.getHealth());
                damage = 0;
            }
        }
        if (damage > 0) {
            squad.archersHealth -= damage;
            if (squad.archersHealth <= 0) {
                squad.removeSquad();
            }
            squad.archersQuantity = (int)Math.ceil((float)squad.archersHealth/archer.getHealth());
        }
        return squad;
    }

    @Override
    public Squad connectSquads(Squad squad1, Squad squad2) { //powinno byc dosc rzadkie imo
            squad1.archersQuantity += squad2.archersQuantity;
            squad1.archersHealth += squad2.archersHealth;

            squad1.soldiersQuantity += squad2.soldiersQuantity;
            squad1.soldiersQuantity += squad2.soldiersHealth;

            squad1.horsemanQuantity += squad2.horsemanQuantity;
            squad1.horsemanHealth += squad2.horsemanHealth;

            squad1.foodQuantity += squad2.foodQuantity;

            squad1.name = squad1.name + " and " + squad2.name;

            squad1.squadHealth += squad2.squadHealth;

            squad2.removeSquad();

            return squad1;
    }

    @Override
    public void bearAttack(Squad squad) { //moze trzeba bedzie dodac first strike'a, jesli niedzwiedz zbyt potezny
        System.out.println("The '" + squad.name + "' have been attacked by a powerful Bear!");
        Bear bear = new Bear();
        while(bear.getHealth() > 0){
            squad.chooseUnitsToDie(squad, bear.getDamage()); //ew tutaj odwrócic kolejnosc
            bear.setHealth(bear.getHealth() - squad.archersDamage - squad.meleeDamage);
        }
        if(squad.getSquadHealth() > 0){
            squad.foodQuantity += squad.squadQuantity * 6;
            System.out.println(" '" + squad.name + "' have bested the bear! As their reward they receive " + squad.squadQuantity * 6 + " food units!");
        }
        else{
            System.out.println("'" + squad.name + "' have been wiped by the bear!");
        }
    }

    @Override
    public void banditsTheft(Squad squad) {
        Random random = new Random();
        int foodStolen = random.nextInt((int) (squad.foodQuantity/squad.squadQuantity*0.5));
        System.out.println("At night bandits sneaked into the '" + squad.name + "'s camp and they took "
        + foodStolen + " units of food!");
        squad.foodQuantity -= foodStolen;
    }

    @Override
    public void lostRecruits(Squad squad) {
        Random random = new Random();
        int numberOfRecruits = random.nextInt((int) (squad.squadQuantity * 0.1));
        System.out.println("'" + squad.name + "' have come across " + numberOfRecruits +" lost recruits!"
        + "\nThey joined the'" + squad.name + "' and provided them " + numberOfRecruits * 10 + " units of food.");
        squad.foodQuantity += numberOfRecruits * 10;
        squad.soldiersQuantity += numberOfRecruits;
    }
    @Override
    public void herdOfCattle(Squad squad) {
        Random random = new Random();
        int foodFound = random.nextInt(squad.squadQuantity * 10);;
        System.out.println("Squad '" + squad.name + "' came across a herd of cattle, their food increases by " + random + ".");
        squad.foodQuantity += foodFound;
    }

    @Override
    public void banditsAttack(Squad squad) {
        Random random = new Random();
        int numberOfBandits = 1 + random.nextInt((int) (squadQuantity * 0.9));
        Bandit bandit = new Bandit();
        squad.chooseUnitsToDie(squad, bandit.getDamage() * numberOfBandits);
        if(squad.getSquadHealth() == 0){
            System.out.println("Squad '" + squad.name + "' was attacked by " + numberOfBandits + " bandits, and they lost!");
        }
        System.out.println("Squad '" + squad.name + "' was attacked by " + numberOfBandits + " bandits!" +
                " They lose some units as a result, but after killing bandits they gain " + numberOfBandits*4 + " food.");
    } //ogarnac zeby czytalo ile jednostek zmarlo (nie jakos wazne ale fajnie by bylo)

    @Override
    public void muddyGround(Squad squad) {
        System.out.println("Squad '" + squad.name + "' came across muddy ground, their movement speed is reduced for next turn.");
        squad.squadMapSpeed /= 2;
        //trzeba bedzie ogarnac zeby im mapspeed wrocil do normy potem
    }

    @Override
    public void foodField(Squad squad) {
        Random random = new Random();
        int amountOfFoodFound = random.nextInt(squad.squadQuantity * 3);
        System.out.println("'" + squad.name + "' have came across a food field, they yield "
                + amountOfFoodFound + " food units.");
        squad.foodQuantity += amountOfFoodFound;
    }
}

