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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The class TuringMachine. This class contains the main part of the code logic for executing and working with
 * TuringMachines.
 * <p>
 * Created by moritz on 10.11.16.
 */
public class TuringMachine {
    private List<TuringTask> tasks = new ArrayList<>();
    private List<String> input = new ArrayList<>();
    private static final char EMPTY_FIELD = '*';


    /**
     * Instantiates a new Turing machine.
     *
     * @param tasks the tasks
     * @param input the input
     */
    public TuringMachine(List<TuringTask> tasks, List<String> input){
        this.tasks = tasks;
        this.input = input;
    }

    /**
     * Execute turing machine. Returns a list of the steps performed while its execution.
     * Will use the inputState of the first task in the tasks as starting state.
     *
     * @param maxNumberOfSteps the max number of steps
     * @return the list
     */
    public List<TuringStep> executeTuringMachine(int maxNumberOfSteps){
        return executeTuringMachine(maxNumberOfSteps, tasks.get(0).getInputState());
    }

    /**
     * Execute turing machine. Returns a list of the steps performed while its execution.
     * Allows the definition of a custom starting state.
     *
     * @param maxNumberOfSteps the max number of steps
     * @param startingState    the starting state
     * @return the list
     */
    public List<TuringStep> executeTuringMachine(int maxNumberOfSteps, String startingState){
        List<TuringStep> steps = new ArrayList<>();
        int[] startPosition = new int[input.size()];

        for (int i = 0; i < input.size(); i++) {
            input.set(i, EMPTY_FIELD + input.get(i) + EMPTY_FIELD);
            startPosition[i] = 1;
            System.out.println(input.get(i));
        }


        steps.add(new TuringStep(input, startingState,  startPosition , false));

        boolean stepPerformed = true;

        for (int i = 0; i < maxNumberOfSteps && stepPerformed; i++){
            stepPerformed = doStepAndAddToList(steps);
        }

        return steps;
    }

    /**
     * Do step on the preconditions of the last element in the steps list and add the new step to the list.
     *
     * @param steps the steps
     * @return the boolean
     */
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


    /**
     * Find tasks for the current step and return possible tasks as a list.
     *
     * @param step the step
     * @return the list
     */
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
