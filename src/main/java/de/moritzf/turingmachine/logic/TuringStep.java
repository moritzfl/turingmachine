/*
 *
 *     Copyright (C) 2016  Moritz Flöter
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package de.moritzf.turingmachine.logic;

import java.util.List;

/**
 * The class TuringStep. Class for storing relevant parameters for the steps of a turingmachine.
 * Created by moritz on 10.11.16.
 */
public class TuringStep {

    /**
     * The Bands. Contains the bands contents for the according step.
     */
    private List<String> bands;

    /**
     * The Ndtm. Signals if there were multiple possibilities for applying a task when executing the step.
     */
    private boolean ndtm = false;

    /**
     * The state. State of the turingmachine in this step.
     */
    private String state;

    /**
     * The position. Contains the positions of the turingmachines reading/writing-head for this step.
     */
    private int[] position;

    /**
     * Gets state.
     *
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Get position int [ ].
     *
     * @return the int [ ]
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * Gets bands.
     *
     * @return the bands
     */
    public List<String> getBands() {

        return bands;
    }

    /**
     * true, if the step involved a ndtm-situation meaning that there were alternative possiblilites for performing
     * a step.
     *
     * @return the boolean
     */
    public boolean isNdtmStep(){
        return ndtm;
    }

    /**
     * Instantiates a new Turing step.
     *
     * @param bands    the bands
     * @param state    the state
     * @param position the position
     * @param ndtm     the ndtm
     */
    public TuringStep(List<String> bands, String state, int[] position, boolean ndtm) {
        this.bands = bands;
        this.state = state;
        this.position = position;
        this.ndtm = ndtm;
    }

    /**
     * Get symbols at current positions string.
     *
     * @return the string
     */
    public String getSymbolsAtCurrentPositions(){
        String symbols = "";
        for (int i = 0; i < bands.size() ; i++){
            symbols += bands.get(i).charAt(position[i]);
        }

        return symbols;

    }
}
