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

/**
 *
 * Class TuringTask. This class stores values for tasks of a turingmachine.
 * Created by moritz on 10.11.16.
 */
public class TuringTask {

    private String inputState;

    /**
     * Instantiates a new Turing task.
     *
     * @param inputState    the input state
     * @param inputSymbols  the input symbols
     * @param outputSymbols the output symbols
     * @param directions    the directions
     * @param outputState   the output state
     */
    public TuringTask(String inputState, String inputSymbols, String outputSymbols, String directions, String outputState) {
        this.inputState = inputState;
        this.inputSymbols = inputSymbols;
        this.outputSymbols = outputSymbols;
        this.directions = directions;
        this.outputState = outputState;
    }

    private String inputSymbols;
    private String outputSymbols;
    private String directions;
    private String outputState;

    /**
     * Gets input symbols.
     *
     * @return the input symbols
     */
    public String getInputSymbols() {
        return inputSymbols;
    }


    /**
     * Gets output symbols.
     *
     * @return the output symbols
     */
    public String getOutputSymbols() {
        return outputSymbols;
    }

    /**
     * Gets directions.
     *
     * @return the directions
     */
    public String getDirections() {
        return directions;
    }


    /**
     * Gets output state.
     *
     * @return the output state
     */
    public String getOutputState() {
        return outputState;
    }


    /**
     * Gets input state.
     *
     * @return the input state
     */
    public String getInputState() {
        return inputState;
    }


    /**
     * Check task for band number boolean.
     *
     * @param numberOfBands the number of bands
     * @return the boolean
     */
    public boolean checkTaskForBandNumber(int numberOfBands){

        boolean bandNumberMatchesTaskValues = true;

        if (inputSymbols.length() !=  numberOfBands){
            bandNumberMatchesTaskValues = false;
        }

        if (outputSymbols.length() !=  numberOfBands){
            bandNumberMatchesTaskValues = false;
        }

        if (directions.length() !=  numberOfBands){
            bandNumberMatchesTaskValues = false;
        }

        return bandNumberMatchesTaskValues;

    }

    public String toString(){
        return this.getInputState() + " " + this.getInputSymbols() + " " + this.getOutputSymbols() + " "
                + this.getDirections() + " " + this.getOutputState();
    }
}
