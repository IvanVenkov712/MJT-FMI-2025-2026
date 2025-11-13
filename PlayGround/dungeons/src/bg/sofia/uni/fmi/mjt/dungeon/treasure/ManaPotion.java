package bg.sofia.uni.fmi.mjt.dungeon.treasure;

import bg.sofia.uni.fmi.mjt.dungeon.actor.Hero;

import java.text.MessageFormat;

public class ManaPotion extends AbstractPotion{
    public ManaPotion(int manaPoints) {
        super(manaPoints);
    }

    @Override
    public String collect(Hero hero) {
        hero.takeMana(heal());
        return MessageFormat.format("Mana potion found! {0} mana points added to your hero!", heal());
    }
}
