package de.moritzf.logic;

/**
 * Created by moritz on 10.11.16.
 */
public class TuringTask {

    private String inputState;

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

    public String getInputSymbols() {
        return inputSymbols;
    }


    public String getOutputSymbols() {
        return outputSymbols;
    }

    public String getDirections() {
        return directions;
    }


    public String getOutputState() {
        return outputState;
    }


    public String getInputState() {
        return inputState;
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
