package Units;

public abstract class Unit{

    private int health;
    private int damage;
    private double mapSpeed;


    public Unit(int health, int damage, double mapSpeed) {
        this.health = health;
        this.damage = damage;
        this.mapSpeed = mapSpeed;
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
