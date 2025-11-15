package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

import java.util.Arrays;

public class LowestRatingEliminationRule implements EliminationRule{
    public LowestRatingEliminationRule(){}

    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        if(ergenkas == null || ergenkas.length < 1){
            return new Ergenka[0];
        }
        int minRating = Integer.MAX_VALUE;
        for(Ergenka ergenka : ergenkas){
            if(ergenka != null) {
                minRating = Math.min(minRating, ergenka.getRating());
            }
        }
        int minCount = 0;
        for(Ergenka ergenka : ergenkas){
            if(ergenka != null && ergenka.getRating() == minRating){
                ++minCount;
            }
        }
        if(minCount == 0){
            return Arrays.copyOf(ergenkas, ergenkas.length);
        }
        Ergenka[] remaining = new Ergenka[ergenkas.length - minCount];
        int at = 0;
        for(Ergenka ergenka : ergenkas) {
            if (ergenka == null || ergenka.getRating() > minRating) {
                remaining[at] = ergenka;
                ++at;
            }
        }
        return remaining;
    }
}
