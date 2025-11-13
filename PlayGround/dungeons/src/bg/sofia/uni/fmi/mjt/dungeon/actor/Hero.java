package bg.sofia.uni.fmi.mjt.dungeon.actor;

import bg.sofia.uni.fmi.mjt.dungeon.treasure.Spell;
import bg.sofia.uni.fmi.mjt.dungeon.treasure.Treasure;
import bg.sofia.uni.fmi.mjt.dungeon.treasure.Weapon;

public class Hero extends AbstractActor{
    public Hero(String name, int health, int mana) {
        super(name, health, mana, null, null);
        startHealth = health;
        startMana = mana;
    }
    public void takeMana(int manaPoints){
        setMana(Math.min(getMana() + manaPoints, startMana));
    }

    public void takeHealing(int healingPoints){
        if(isAlive()){
            setHealth(Math.min(getHealth() + healingPoints, startHealth));
        }
    }

    public void learn(Spell spell){
        if(spell == null){
            return;
        }
        if(getSpell() == null){
            setSpell(spell);
        }else if(getSpell().getDamage() < spell.getDamage()){
            setSpell(spell);
        }
    }

    public void equip(Weapon weapon){
        if(weapon == null){
            return;
        }
        if(getWeapon() == null){
            setWeapon(weapon);
        } else if(getWeapon().getDamage() < weapon.getDamage()){
            setWeapon(weapon);
        }
    }

    public String collectTreasure(Treasure treasure){
        return treasure.collect(this);
    }

    private final int startHealth;
    private final int startMana;

}
