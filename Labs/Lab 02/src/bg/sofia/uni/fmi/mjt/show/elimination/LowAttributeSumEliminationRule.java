package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

import java.util.Arrays;

public class LowAttributeSumEliminationRule implements EliminationRule{

    public LowAttributeSumEliminationRule(int threshold){
        this.threshold = threshold;
    }

    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        if(ergenkas == null || ergenkas.length < 1){
            return new Ergenka[0];
        }
        int count = 0;
        for(Ergenka ergenka : ergenkas){
            if(ergenka.getHumorLevel() + ergenka.getRomanceLevel() < threshold){
                ++count;
            }
        }
        if(count == 0){
            return Arrays.copyOf(ergenkas, ergenkas.length);
        }
        Ergenka[] remaining = new Ergenka[ergenkas.length - count];
        int at = 0;
        for(Ergenka ergenka: ergenkas){
            if(ergenka.getHumorLevel() + ergenka.getRomanceLevel() >= threshold){
                remaining[at] = ergenka;
                ++at;
            }
        }
        return remaining;
    }
    private int threshold;
}
