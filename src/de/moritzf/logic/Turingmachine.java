package de.moritzf.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by moritz on 10.11.16.
 */
public class Turingmachine{
    private Collection<TuringTask> tasks = new ArrayList<>();
    private Collection<String> input = new ArrayList<>();



    public Turingmachine(Collection<TuringTask> tasks, Collection<String> input){
        this.tasks = tasks;
        this.input = input;
    }

    public Collection<TuringTask> findTasks(List<TuringStep> steps){
        TuringStep previousStep = steps.get(steps.size()-1);
        Collection<TuringTask> foundTasks = new ArrayList<TuringTask>();
        for (TuringTask task : tasks){
            if (previousStep.getState().equals(task.getInputSymbols())
                    && previousStep.getSymbolsAtCurrentPositions().equals(task.getInputSymbols())){

                foundTasks.add(task);
            }
        }
        return tasks;
    }


}
