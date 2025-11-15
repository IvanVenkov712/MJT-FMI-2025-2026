package bg.sofia.uni.fmi.mjt.show.ergenka;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;

public class HumorousErgenka extends AbstractErgenka{

    public HumorousErgenka(String name, short age, int romanceLevel, int humorLevel, int rating){
        super(name, age, romanceLevel, humorLevel, rating);
    }

    @Override
    public void reactToDate(DateEvent dateEvent) {
        int bonuses = 0;
        if(dateEvent.getDuration() >= 30 && dateEvent.getDuration() <= 90){
            bonuses = 4;
        }
        else if (dateEvent.getDuration() < 30){
            bonuses = -2;
        }
        else if(dateEvent.getDuration() > 90){
            bonuses = -3;
        }
        int y = dateEvent.getTensionLevel();
        if(y != 0){
            setRating((getHumorLevel() * 5) / y + Math.floorDiv(getRomanceLevel(), 3) + bonuses);
        }
    }
}
