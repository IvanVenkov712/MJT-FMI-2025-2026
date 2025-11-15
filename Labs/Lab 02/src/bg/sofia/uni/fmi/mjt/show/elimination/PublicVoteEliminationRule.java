package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

import java.util.Arrays;

public class PublicVoteEliminationRule implements EliminationRule {
    public PublicVoteEliminationRule(String[] votes) {
        majority = findErgenka(votes);
        /*
        if(votes == null || votes.length < 1){
            majority = null;
            return;
        }
        int c = 0;
        for(String vote : votes){
            if(c == 0){
                majority = vote;
            } else if(majority.equals(vote)){
                ++c;
            } else{
                --c;
            }
        }
        int nOccurs = 0;
        for(String vote : votes){
            if(majority.equals(vote)){
                ++nOccurs;
            }
        }
        if(nOccurs < votes.length / 2 + 1){
            majority = null;
        }
        */
    }

    private String findErgenka(String[] votes) {
        String majority = null;
        if (votes == null || votes.length < 1) {
            return majority;
        }
        int c = 0;
        for (String vote : votes) {
            if(vote != null) {
                if (c == 0) {
                    majority = vote;
                    c = 1;
                } else if (majority.equals(vote)) {
                    ++c;
                } else {
                    --c;
                }
            }
        }
        if(majority != null) {
            int nOccurs = 0;
            for (String vote : votes) {
                if (majority.equals(vote)) {
                    ++nOccurs;
                }
            }
            if (nOccurs < votes.length / 2 + 1) {
                majority = null;
            }
        }
        return majority;
    }

    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        if (ergenkas == null || ergenkas.length < 1) {
            return new Ergenka[0];
        }
        if (majority == null) {
            return Arrays.copyOf(ergenkas, ergenkas.length);
        }

        boolean exists = false;
        for (Ergenka ergenka : ergenkas) {
            if (ergenka != null && ergenka.getName().equals(majority)) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            return Arrays.copyOf(ergenkas, ergenkas.length);
        }
        Ergenka[] remaining = new Ergenka[ergenkas.length - 1];
        int at = 0;
        boolean occured = false;
        for (Ergenka ergenka : ergenkas) {
            if (ergenka != null && !occured && ergenka.getName().equals(majority)) {
               occured = true;
            }
            else {
                remaining[at] = ergenka;
                ++at;
            }
        }
        return remaining;
    }

    private final String majority;
}
