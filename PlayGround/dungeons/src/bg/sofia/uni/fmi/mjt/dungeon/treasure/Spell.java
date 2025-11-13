package bg.sofia.uni.fmi.mjt.dungeon.treasure;

import bg.sofia.uni.fmi.mjt.dungeon.actor.Hero;

import java.text.MessageFormat;

public class Spell extends AttackingTreasure{
    public Spell(String name, int damage, int manaCost) {
        super(name, damage);
        this.manaCost = manaCost;
    }

    public int getManaCost(){
        return manaCost;
    }

    @Override
    public String collect(Hero hero) {
        hero.learn(this);
        return MessageFormat.format("Spell found! Damage points: {0}, Mana cost: {1}", getDamage(), getManaCost());
    }

    private int manaCost;
}
