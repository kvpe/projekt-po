package pl.pwr.simulation.units;

import pl.pwr.simulation.map.Coordinates;


public class Squad {
    private boolean exists;
    private boolean hasMoved;

    private Unit archer;
    private Unit soldier;
    private Unit horseman;

    private int squadQuantity;

    private int archersQuantity;
    private int soldiersQuantity;
    private int horsemanQuantity;

    private int archersHealth;
    private int soldiersHealth;
    private int horsemanHealth;

    private int archersDamage;
    private int meleeDamage;

    private double soldiersSpeed;
    private double archersSpeed;
    private double horsemenSpeed;
    private double squadMapSpeed;

    private int squadHealth;
    private int foodQuantity;
    private String name;
    private int squadId;

    public Squad(int squadId) {
        exists = false;
        name = " ";
        this.squadId = squadId;

    }

    public Squad(int archersQuantity, int soldiersQuantity, int horsemanQuantity, int foodQuantity, String name, int squadId, int mapSize, boolean hasMoved) {

        exists = true;
        this.hasMoved = hasMoved;
        
        soldier = new Unit() {
            @Override
            public int getDamage() {
                return 45;
            }
            @Override
            public int getHealth() {
                return 100;
            }
        };
        archer = new Unit() {
            @Override
            public int getDamage() {
                return 50;
            }

            @Override
            public int getHealth() {
                return 50;
            }
        };
        horseman = new Unit() {
            @Override
            public int getDamage() {
                return 35;
            }

            @Override
            public int getHealth() {
                return 360;
            }
        };

        this.archersQuantity = archersQuantity;
        this.soldiersQuantity = soldiersQuantity;
        this.horsemanQuantity = horsemanQuantity;
        squadQuantity = archersQuantity + soldiersQuantity + horsemanQuantity;

        archersHealth = archer.getHealth() * archersQuantity;
        soldiersHealth = soldier.getHealth() * soldiersQuantity;
        horsemanHealth = horseman.getHealth() * horsemanQuantity;

        squadHealth = archersQuantity * archer.getHealth() + soldiersQuantity * soldier.getHealth()
                + horsemanQuantity * horseman.getHealth();

        archersDamage = archersQuantity * archer.getDamage();
        meleeDamage = soldiersQuantity * soldier.getDamage() + horsemanQuantity * horseman.getDamage();

        archersSpeed = (double) mapSize/5;
        soldiersSpeed = (double) mapSize/6;
        horsemenSpeed = (double) mapSize/2;
        squadMapSpeed =  (archersQuantity * archersSpeed + soldiersQuantity * soldiersSpeed
                + horsemanQuantity * horsemenSpeed) / (archersQuantity + soldiersQuantity + horsemanQuantity);

        this.foodQuantity = foodQuantity;
        this.name = name;
        this.squadId = squadId;
    }
    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public Unit getArcher() {
        return archer;
    }

    public void setArcher(Unit archer) {
        this.archer = archer;
    }

    public Unit getSoldier() {
        return soldier;
    }

    public void setSoldier(Unit soldier) {
        this.soldier = soldier;
    }

    public Unit getHorseman() {
        return horseman;
    }

    public void setHorseman(Unit horseman) {
        this.horseman = horseman;
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

    public boolean ifExists() {
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

    public int getArchersQuantity() {
        return archersQuantity;
    }

    public void setArchersQuantity(int archersQuantity) {
        this.archersQuantity = archersQuantity;
    }

    public int getSoldiersQuantity() {
        return soldiersQuantity;
    }

    public void setSoldiersQuantity(int soldiersQuantity) {
        this.soldiersQuantity = soldiersQuantity;
    }

    public int getHorsemanQuantity() {
        return horsemanQuantity;
    }

    public void setHorsemanQuantity(int horsemanQuantity) {
        this.horsemanQuantity = horsemanQuantity;
    }

    public int getArchersHealth() {
        return archersHealth;
    }

    public void setArchersHealth(int archersHealth) {
        this.archersHealth = archersHealth;
    }

    public int getSoldiersHealth() {
        return soldiersHealth;
    }

    public void setSoldiersHealth(int soldiersHealth) {
        this.soldiersHealth = soldiersHealth;
    }

    public int getHorsemanHealth() {
        return horsemanHealth;
    }

    public void setHorsemanHealth(int horsemanHealth) {
        this.horsemanHealth = horsemanHealth;
    }

    public void setSquadMapSpeed(double squadMapSpeed) {
        this.squadMapSpeed = squadMapSpeed;
    }

    public int getDamage() {
        return archersDamage + meleeDamage;
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

    public void removeSquad() {
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

    public void setStats() {
        squadQuantity = archersQuantity + soldiersQuantity + horsemanQuantity;

        archersHealth = archersQuantity * archer.getHealth();
        soldiersHealth = soldiersQuantity * soldier.getHealth();
        horsemanHealth = horsemanQuantity * (horseman.getHealth());
        squadHealth = archersHealth + soldiersHealth + horsemanHealth;

        archersDamage = archersQuantity * archer.getDamage();
        meleeDamage = soldiersQuantity * soldier.getDamage() + horsemanQuantity * horseman.getDamage();
        squadMapSpeed = ((double)archersQuantity * archersSpeed + (double)soldiersQuantity * soldiersSpeed
                + (double)horsemanQuantity * horsemenSpeed) / squadQuantity;
    }
}