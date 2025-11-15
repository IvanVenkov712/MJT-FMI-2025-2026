package bg.sofia.uni.fmi.mjt.show.ergenka;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;

public class RomanticErgenka extends AbstractErgenka{

    public RomanticErgenka(String name, short age, int romanceLevel, int humorLevel, int rating, String favoriteDateLocation){
        super(name, age, romanceLevel, humorLevel, rating);
        this.favoriteDateLocation = favoriteDateLocation;
    }

    @Override
    public void reactToDate(DateEvent dateEvent) {
        int bonuses = 0;
        if(dateEvent.getLocation().equals(favoriteDateLocation)){
            bonuses += 5;
        }
        if(dateEvent.getDuration() < 30){
            bonuses -= 3;
        }
        if(dateEvent.getDuration() > 120){
            bonuses -= 2;
        }
        if(dateEvent.getTensionLevel() != 0) {
            setRating((getRomanceLevel() * 7) / dateEvent.getTensionLevel() + Math.floorDiv(getHumorLevel(), 3) + bonuses);
        }
    }

    private String favoriteDateLocation;
}
