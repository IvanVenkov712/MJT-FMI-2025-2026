package bg.sofia.uni.fmi.mjt.dungeon.treasure;

import bg.sofia.uni.fmi.mjt.dungeon.actor.AbstractActor;

public abstract class AbstractPotion implements Treasure{
    public AbstractPotion(int points){
        this.points = points;
    }

    public int heal(){
        return points;
    }

    private int points;
}
