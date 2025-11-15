package bg.sofia.uni.fmi.mjt.show.ergenka;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;

public abstract class AbstractErgenka implements Ergenka{

    public AbstractErgenka(String name, short age, int romanceLevel, int humorLevel, int rating){
        this.name = name;
        this.age = age;
        this.romanceLevel = romanceLevel;
        this.humorLevel = humorLevel;
        this.rating = rating;
    }

    @Override
    public String getName() {
        return name == null? "" : name;
    }

    @Override
    public short getAge() {
        return age;
    }

    @Override
    public int getRomanceLevel() {
        return romanceLevel;
    }

    @Override
    public int getHumorLevel() {
        return humorLevel;
    }

    @Override
    public int getRating() {
        return rating;
    }

    protected void setRating(int rating){
        this.rating = rating;
    }

    protected void addToRating(int points) {
        this.rating += points;
    }

    private String name;
    private short age;
    private int romanceLevel;
    private int humorLevel;
    private int rating;

}
