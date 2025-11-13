package bg.sofia.uni.fmi.mjt.dungeon.treasure;

import bg.sofia.uni.fmi.mjt.dungeon.actor.Hero;

import java.text.MessageFormat;

public class HealthPotion extends AbstractPotion{

    public HealthPotion(int healingPoints) {
        super(healingPoints);
    }

    @Override
    public String collect(Hero hero) {
        hero.takeHealing(heal());
        return MessageFormat.format("Health potion found! {0} health points added to your hero!", heal());
    }
}
