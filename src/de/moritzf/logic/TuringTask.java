package de.moritzf.logic;

/**
 * Created by moritz on 10.11.16.
 */
public class TuringTask {

    private String inputState;
    private String inputSymbols;
    private String outputSymbols;
    private String directions;
    private String outputState;

    public String getInputSymbols() {
        return inputSymbols;
    }

    public void setInputSymbols(String inputSymbols) {
        this.inputSymbols = inputSymbols;
    }

    public String getOutputSymbols() {
        return outputSymbols;
    }

    public void setOutputSymbols(String outputSymbols) {
        this.outputSymbols = outputSymbols;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getOutputState() {
        return outputState;
    }

    public void setOutputState(String outputState) {
        this.outputState = outputState;
    }

    public String getInputState() {
        return inputState;
    }

    public void setInputState(String inputState) {
        this.inputState = inputState;
    }


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
}
