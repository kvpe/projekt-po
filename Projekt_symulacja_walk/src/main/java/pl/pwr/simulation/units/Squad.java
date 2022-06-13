package pl.pwr.simulation.units;

import pl.pwr.simulation.map.Coordinates;

public class Squad {
    private boolean exists;
    private boolean hasMoved;
    private Coordinates squadCoordinates;

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

    public Squad(int archersQuantity, int soldiersQuantity, int horsemanQuantity, int foodQuantity, String name, int squadId, int mapSize, Coordinates coordinates, boolean hasMoved) {

        exists = true;
        this.hasMoved = hasMoved;
        squadCoordinates = coordinates;

        archer = new Unit(50, 50, (double) mapSize / 5, mapSize / 5) {
            @Override
            public void setMapSpeed(double mapSpeed) {
                super.setMapSpeed(mapSpeed);
            }

            @Override
            public void setDamage(int damage) {
                super.setDamage(damage);
            }

            @Override
            public int getHealth() {
                return super.getHealth();
            }

            @Override
            public int getDamage() {
                return super.getDamage();
            }

            @Override
            public double getMapSpeed() {
                return super.getMapSpeed();
            }

            @Override
            public int getRange() {
                return super.getRange();
            }

            @Override
            public void setHealth(int health) {
                super.setHealth(health);
            }
        };
        soldier = new Unit(100, 45, (double) mapSize / 6, 0) {
            @Override
            public void setMapSpeed(double mapSpeed) {
                super.setMapSpeed(mapSpeed);
            }

            @Override
            public void setDamage(int damage) {
                super.setDamage(damage);
            }

            @Override
            public int getHealth() {
                return super.getHealth();
            }

            @Override
            public int getDamage() {
                return super.getDamage();
            }

            @Override
            public double getMapSpeed() {
                return super.getMapSpeed();
            }

            @Override
            public void setHealth(int health) {
                super.setHealth(health);
            }
        };
        horseman = new Unit(360, 35, (double) mapSize / 2, 0) {
            @Override
            public void setMapSpeed(double mapSpeed) {
                super.setMapSpeed(mapSpeed);
            }

            @Override
            public void setDamage(int damage) {
                super.setDamage(damage);
            }

            @Override
            public int getHealth() {
                return super.getHealth();
            }

            @Override
            public int getDamage() {
                return super.getDamage();
            }

            @Override
            public double getMapSpeed() {
                return super.getMapSpeed();
            }

            @Override
            public void setHealth(int health) {
                super.setHealth(health);
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

        squadMapSpeed = (archersQuantity * archer.getMapSpeed() + soldiersQuantity * soldier.getMapSpeed()
                + horsemanQuantity * horseman.getMapSpeed()) / (archersQuantity + soldiersQuantity + horsemanQuantity);

        this.foodQuantity = foodQuantity;
        this.name = name;
        this.squadId = squadId;
    }

    public Unit getSoldier() {
        return soldier;
    }

    public Unit getHorseman() {
        return horseman;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public String getName() {
        return name;
    }

    public Unit getArcher() {
        return archer;
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

    public void setSquadCoordinates(Coordinates squadCoordinates) {
        this.squadCoordinates = squadCoordinates;
    }

    public Coordinates getSquadCoordinates() {
        return squadCoordinates;
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
        squadMapSpeed = (archersQuantity * archer.getMapSpeed() + soldiersQuantity * soldier.getMapSpeed() + horsemanQuantity * horseman.getMapSpeed()) / squadQuantity;
    }
}
