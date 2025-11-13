package bg.sofia.uni.fmi.mjt.show;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;
import bg.sofia.uni.fmi.mjt.show.elimination.EliminationRule;
import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

import java.util.Arrays;

public class ShowAPIImpl implements ShowAPI{
    public ShowAPIImpl(Ergenka[] ergenkas, EliminationRule[] defaultEliminationRules){
        this.ergenkas = Arrays.copyOf(ergenkas, ergenkas.length);
        this.defaultEliminationRules = Arrays.copyOf(defaultEliminationRules, defaultEliminationRules.length);
    }

    @Override
    public Ergenka[] getErgenkas() {
        return Arrays.copyOf(ergenkas, ergenkas.length);
    }

    @Override
    public void playRound(DateEvent dateEvent) {
        for(Ergenka ergenka : ergenkas){
            organizeDate(ergenka, dateEvent);
        }
    }

    @Override
    public void eliminateErgenkas(EliminationRule[] eliminationRules) {
        if(eliminationRules == null || eliminationRules.length == 0){
            eliminationRules = defaultEliminationRules;
        }
        for(EliminationRule rule : eliminationRules){
            ergenkas = rule.eliminateErgenkas(ergenkas);
        }
    }

    @Override
    public void organizeDate(Ergenka ergenka, DateEvent dateEvent) {
        ergenka.reactToDate(dateEvent);
    }

    private Ergenka[] ergenkas;
    private EliminationRule[] defaultEliminationRules;
}
