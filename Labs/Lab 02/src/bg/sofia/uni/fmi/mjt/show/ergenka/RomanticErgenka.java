package bg.sofia.uni.fmi.mjt.show.ergenka;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;

public class RomanticErgenka extends AbstractErgenka{

    public RomanticErgenka(String name, short age, int romanceLevel, int humorLevel, int rating, String favoriteDateLocation){
        super(name, age, romanceLevel, humorLevel, rating);
        this.favoriteDateLocation = favoriteDateLocation;
    }

    @Override
    public void reactToDate(DateEvent dateEvent) {
        if (dateEvent == null) {
            dateEvent = DateEvent.defaultDateEvent;
            addToRating(1);
        }
        int bonuses = 0;
        if(dateEvent.getLocation().equalsIgnoreCase(favoriteDateLocation)){
            bonuses = 5;
        }
        else if(dateEvent.getDuration() < 30){
            bonuses = -3;
        }
        else if(dateEvent.getDuration() > 120){
            bonuses = -2;
        }
        addToRating(
            (getRomanceLevel() * 7) / Math.max(1, dateEvent.getTensionLevel()) + Math.floorDiv(getHumorLevel(), 3) + bonuses
        );
    }

    private String favoriteDateLocation;
}
