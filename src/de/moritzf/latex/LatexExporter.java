package de.moritzf.latex;

import de.moritzf.logic.TuringStep;

import java.util.List;

/**
 * Created by moritz on 10.11.16.
 */
public class LatexExporter {

    public static final String MATRIX_SEPARATOR = "$\\\\$";

    public static String generateLatex(List<TuringStep> steps) {
        String protocol = "";
        for (int i = 0; i < steps.size(); i++) {
            TuringStep currentStep = steps.get(i);
            if (currentStep.getBands().size() == 1) {
                protocol += generateSegmentForSingleBandContext(steps, i);
            } else {
                protocol += generateSegmentForMultiBandContext(steps, i);
            }
        }

        return "$" + protocol + "$";

    }

    private static String generateSegmentForSingleBandContext(List<TuringStep> steps, int i) {
        String segment = "";
        TuringStep currentStep = steps.get(i);


        String currentBand = currentStep.getBands().get(0);
        int currentPosition = currentStep.getPosition()[0];
        String currentState = currentStep.getState();

        boolean equalContent = i > 0 && currentStep.getBands().get(0).equals(steps.get(i - 1).getBands().get(0));

        if (!equalContent) {
            segment += "\\begin{smallmatrix} \n";
            for (int j = 0; j < currentBand.length(); j++) {
                segment += currentBand.charAt(j);

                if (j < currentBand.length() - 1) {
                    segment += " & ";
                }
            }

            segment += "\\\\ \n";

        }


        //add arrow and State

        String arrowPart = "";
        String statePart = "";
        for (int j = 0; j < currentBand.length(); j++) {
            if (j == currentPosition) {
                arrowPart += " \\uparrow ";
                statePart += currentState;
            }

            if (j < currentBand.length() - 1) {
                arrowPart += " & ";
                statePart += " & ";
            }

        }

        segment += arrowPart + "\\\\ \n" + statePart;

        if (i == steps.size() - 1) {
            segment += " \n\\end{smallmatrix}  \n";
        } else if (!currentBand.equals(steps.get(i + 1).getBands().get(0))) {
            segment += " \n\\end{smallmatrix} \n" + MATRIX_SEPARATOR + "\n";

        } else {
            segment += "\\\\";
        }

        return segment;

    }

    private static String generateSegmentForMultiBandContext(List<TuringStep> steps, int i) {
        return "";
    }

}
