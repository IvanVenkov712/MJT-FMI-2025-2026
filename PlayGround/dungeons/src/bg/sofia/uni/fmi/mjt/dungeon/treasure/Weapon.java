package bg.sofia.uni.fmi.mjt.dungeon.treasure;

import bg.sofia.uni.fmi.mjt.dungeon.actor.Hero;

public class Weapon extends AttackingTreasure{
    public Weapon(String name, int damage) {
        super(name, damage);
    }

    @Override
    public String collect(Hero hero) {
        hero.equip(this);
        return "Weapon found! Damage points: " + getDamage();
    }
}
