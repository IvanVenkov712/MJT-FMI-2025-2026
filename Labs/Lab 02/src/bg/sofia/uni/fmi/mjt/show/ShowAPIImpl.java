package bg.sofia.uni.fmi.mjt.show;

import bg.sofia.uni.fmi.mjt.show.date.DateEvent;
import bg.sofia.uni.fmi.mjt.show.elimination.EliminationRule;
import bg.sofia.uni.fmi.mjt.show.elimination.LowestRatingEliminationRule;
import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

import java.util.Arrays;

public class ShowAPIImpl implements ShowAPI {
    public ShowAPIImpl(Ergenka[] ergenkas, EliminationRule[] defaultEliminationRules) {
        if (defaultEliminationRules == null || defaultEliminationRules.length == 0) {
            defaultEliminationRules = new EliminationRule[] {new LowestRatingEliminationRule() };
        }
        this.ergenkas = ergenkas;
        this.defaultEliminationRules = defaultEliminationRules;
    }

    @Override
    public Ergenka[] getErgenkas() {
        return ergenkas;
    }

    @Override
    public void playRound(DateEvent dateEvent) {
        if (dateEvent == null) {
            dateEvent = new DateEvent("", 0, 0);
        }
        for (Ergenka ergenka : ergenkas) {
            if (ergenka != null) {
                organizeDate(ergenka, dateEvent);
            }
        }
    }

    @Override
    public void eliminateErgenkas(EliminationRule[] eliminationRules) {
        if (eliminationRules == null || eliminationRules.length == 0) {
            eliminationRules = defaultEliminationRules;
        }
        for (EliminationRule rule : eliminationRules) {
            ergenkas = rule.eliminateErgenkas(ergenkas);
        }
    }

    @Override
    public void organizeDate(Ergenka ergenka, DateEvent dateEvent) {
        if (ergenka == null) {
            return;
        }
        ergenka.reactToDate(dateEvent);
    }

    private Ergenka[] ergenkas;
    private EliminationRule[] defaultEliminationRules;
}
