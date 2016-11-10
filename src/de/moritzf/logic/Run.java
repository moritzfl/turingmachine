package de.moritzf.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moritz on 10.11.16.
 */
public class Run {

    public static void main(String[] args){
        ArrayList<String> bands = new ArrayList<>();
        bands.add("*****");
        bands.add("*****");
        TuringTask task = new TuringTask("z_0", "**", "11", "RR", "z_0");
        ArrayList<TuringTask> taskList = new ArrayList<TuringTask>();
        taskList.add(task);
        TuringMachine tm = new TuringMachine( taskList, bands);
        List<TuringStep> stepsDone = tm.executeTuringMachine(10);

        stepsDone.get(stepsDone.size()-1).getBands().forEach(System.out::println);
    }

}
