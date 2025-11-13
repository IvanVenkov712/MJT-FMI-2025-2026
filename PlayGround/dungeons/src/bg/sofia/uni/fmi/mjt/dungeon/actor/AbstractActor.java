package bg.sofia.uni.fmi.mjt.dungeon.actor;

import bg.sofia.uni.fmi.mjt.dungeon.treasure.Spell;
import bg.sofia.uni.fmi.mjt.dungeon.treasure.Weapon;

public abstract class AbstractActor implements Actor{
    public AbstractActor(String name, int health, int mana, Weapon weapon, Spell spell){
        this.name = name;
        this.health = health;
        this.mana = mana;
        this.weapon = weapon;
        this.spell = spell;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public Spell getSpell() {
        return spell;
    }

    @Override
    public void takeDamage(int damagePoints) {
        setHealth(health - damagePoints);
    }

    @Override
    public int attack() {
        if(weapon == null && spell == null){
            return 0;
        }
        if(spell == null || mana < spell.getManaCost()){
            return weapon.getDamage();
        }
        if(spell.getDamage() > weapon.getDamage()){
            mana -= spell.getManaCost();
            return spell.getDamage();
        }
        return weapon.getDamage();
    }

    protected void setSpell(Spell spell){
        this.spell = spell;
    }

    protected void setWeapon(Weapon weapon){
        this.weapon = weapon;
    }

    protected void setHealth(int health){
        this.health = Math.max(0, health);
    }

    protected void setMana(int mana){
        this.mana = mana;
    }

    private String name;
    private int health;
    private int mana;
    Weapon weapon;
    Spell spell;
}
