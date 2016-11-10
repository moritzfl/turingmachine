package de.moritzf.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by moritz on 10.11.16.
 */
public class TuringMachine {
    private List<TuringTask> tasks = new ArrayList<>();
    private List<String> input = new ArrayList<>();
    private static final char EMPTY_FIELD = '*';



    public TuringMachine(List<TuringTask> tasks, List<String> input){
        this.tasks = tasks;
        this.input = input;
    }

    public List<TuringStep> executeTuringMachine(int maxNumberOfSteps){
        return executeTuringMachine(maxNumberOfSteps, tasks.get(0).getInputState());
    }

    public List<TuringStep> executeTuringMachine(int maxNumberOfSteps, String startingState){
        List<TuringStep> steps = new ArrayList<>();
        int[] startPosition = new int[input.size()];

        for (int i = 0; i < input.size(); i++) {
            input.set(i, EMPTY_FIELD + input.get(i));
            startPosition[i] = 1;
        }

        steps.add(new TuringStep(input, startingState,  startPosition , false));

        boolean stepPerformed = true;

        for (int i = 0; i < maxNumberOfSteps && stepPerformed; i++){
            stepPerformed = doStepAndAddToList(steps);
        }

        return steps;
    }

    private boolean doStepAndAddToList(List<TuringStep> steps) {
        TuringStep previousStep = steps.get(steps.size()-1);
        List<TuringTask> foundTasks = findTasks(previousStep);

        boolean stepDone = false;

        boolean ndtm = foundTasks.size() > 1;

        if (foundTasks.size() > 0){
            //Get a random task for ndtm-situations where more than one task was found.
            Collections.shuffle(foundTasks);
            TuringTask chosenTask = foundTasks.get(0);

            List<String> nextBands = new ArrayList<>();
            int[] nextPosition = new int[previousStep.getPosition().length];

            //assign the following state according to the one defined in the chosen task
            String nextState = chosenTask.getOutputState();

            //handle content of the bands
            for (int i = 0; i < nextPosition.length; i++){
                StringBuilder stringBuilder = new StringBuilder(previousStep.getBands().get(i));
                stringBuilder.setCharAt(previousStep.getPosition()[i], chosenTask.getOutputSymbols().charAt(i));
                String currentBand = stringBuilder.toString();
                nextBands.add(currentBand);
            }

            //handle positions
            for (int i = 0; i < nextPosition.length; i++){
                char currentDirection = chosenTask.getDirections().toLowerCase().charAt(i);
                if (currentDirection == 'r'){
                    nextPosition[i] = previousStep.getPosition()[i] + 1;
                } else if (currentDirection == 'l'){
                    nextPosition[i] = previousStep.getPosition()[i] - 1;
                } else {
                    nextPosition[i] = previousStep.getPosition()[i];
                }
            }

            //if the position reached the corner of a band, append an empty_field-Symbol to it
            for (int i = 0; i < nextPosition.length; i++){
                if (nextPosition[i] == 0 ){
                    nextBands.set(i, EMPTY_FIELD + nextBands.get(i));
                    nextPosition[i] =  1;
                } else if (nextPosition[i] ==  nextBands.get(i).length()-1) {
                    nextBands.set(i, nextBands.get(i) + EMPTY_FIELD);
                }
            }

            steps.add(new TuringStep(nextBands, nextState, nextPosition,ndtm));
            stepDone = true;
        }

        return stepDone;

    }


    private List<TuringTask> findTasks(TuringStep step){
        List<TuringTask> foundTasks = new ArrayList<TuringTask>();
        for (TuringTask task : tasks){
            if (step.getState().equals(task.getInputState())
                    && step.getSymbolsAtCurrentPositions().equals(task.getInputSymbols())){
                foundTasks.add(task);
            }
        }
        return foundTasks;
    }


}
