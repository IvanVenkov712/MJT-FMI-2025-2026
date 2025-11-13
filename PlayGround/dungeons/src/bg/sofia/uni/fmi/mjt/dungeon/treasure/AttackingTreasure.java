package bg.sofia.uni.fmi.mjt.dungeon.treasure;

public abstract class AttackingTreasure implements Treasure{
    public AttackingTreasure(String name, int damage){
        this.name = name;
        this.damage = damage;
    }

    public String getName(){
        return name;
    }

    public int getDamage(){
        return damage;
    }

    private String name;
    private int damage;
}
