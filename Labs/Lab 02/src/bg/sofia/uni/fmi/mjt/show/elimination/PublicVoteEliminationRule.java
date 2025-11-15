package bg.sofia.uni.fmi.mjt.show.elimination;

import bg.sofia.uni.fmi.mjt.show.ergenka.Ergenka;

import java.util.Arrays;

public class PublicVoteEliminationRule implements EliminationRule{
    public PublicVoteEliminationRule(String[] votes){
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
    private String findErgenka(String[] votes){
        String majority = null;
        if(votes == null || votes.length < 1){
            return majority;
        }
        int c = 0;
        for(String vote : votes){
            if(c == 0 || majority == null){
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
        return majority;
    }
    @Override
    public Ergenka[] eliminateErgenkas(Ergenka[] ergenkas) {
        if(ergenkas == null || ergenkas.length < 1){
            return new Ergenka[0];
        }
        int count = 0;
        for(Ergenka ergenka : ergenkas){
            if(ergenka != null && ergenka.getName().equals(majority)){
                ++count;
            }
        }
        if(count == 0){
            return Arrays.copyOf(ergenkas, ergenkas.length);
        }
        Ergenka[] remaining = new Ergenka[ergenkas.length - count];
        int at = 0;
        for(Ergenka ergenka : ergenkas){
            if(ergenka == null || !ergenka.getName().equals(majority)){
                remaining[at] = ergenka;
                ++at;
            }
        }
        return remaining;
    }

    private final String majority;
}
