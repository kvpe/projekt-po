package pl.pwr.simulation.units;

public class Unit{

    private int health;
    private int damage;
    private double mapSpeed;
    private final int range;
    
    public Unit(int health, int damage, double mapSpeed, int range) {
        this.health = health;
        this.damage = damage;
        this.mapSpeed = mapSpeed;
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    public void setMapSpeed(double mapSpeed) {
        this.mapSpeed = mapSpeed;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public double getMapSpeed() {
        return mapSpeed;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
