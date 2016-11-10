package de.moritzf.logic;

import java.util.Collection;
import java.util.List;

/**
 * Created by moritz on 10.11.16.
 */
public class TuringStep {
    private List<String> bands;
    boolean ndtm = false;
    private String state;
    private int[] position;

    public String getState() {
        return state;
    }

    public int[] getPosition() {
        return position;
    }

    public List<String> getBands() {

        return bands;
    }

    public boolean isNdtmStep(){
        return ndtm;
    }

    public TuringStep(List<String> bands, String state, int[] position, boolean ndtm) {
        this.bands = bands;
        this.state = state;
        this.position = position;
        this.ndtm = ndtm;
    }

    public String getSymbolsAtCurrentPositions(){
        String symbols = "";
        for (int i = 0; i < bands.size() ; i++){
            symbols += bands.get(i).charAt(position[i]);
        }

        return symbols;

    }
}
