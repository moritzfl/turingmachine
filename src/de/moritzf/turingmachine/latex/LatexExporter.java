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
package de.moritzf.turingmachine.latex;

import de.moritzf.turingmachine.logic.TuringStep;

import java.util.List;

/**
 * Created by moritz on 10.11.16.
 */
public class LatexExporter {

    /**
     * The constant MATRIX_SEPARATOR. This separator will be written between between each matrix.
     */
    public static final String MATRIX_SEPARATOR = "$\\\\$";
    /**
     * The constant MULTIBAND_SEPARATOR. This separator will be written between steps of a turingmachine with more than
     * one band.
     */
    public static final String MULTIBAND_SEPARATOR = "\n$\\\\.....................\\\\$\n\n";

    /**
     * Generate latex string representing the steps of a list of TuringSteps.
     *
     * @param steps the steps
     * @return the string
     */
    public static String generateLatex(List<TuringStep> steps) {
        String protocol = "";
        for (int i = 0; i < steps.size(); i++) {
            TuringStep currentStep = steps.get(i);
            if (currentStep.getBands().size() == 1) {
                protocol += generateSegmentForSingleBandContext(steps, i);
            } else {
                protocol += generateSegmentForMultiBandContext(steps, i) + MULTIBAND_SEPARATOR;
            }
        }

        return protocol;

    }

    private static String generateSegmentForSingleBandContext(List<TuringStep> steps, int i) {
        String segment = "";
        TuringStep currentStep = steps.get(i);
        TuringStep previousStep = null;
        if (i > 0) {
            previousStep = steps.get(i - 1);

        }


        String currentBand = currentStep.getBands().get(0);
        int currentPosition = currentStep.getPosition()[0];
        String currentState = currentStep.getState();

        boolean equalContent = i > 0 && currentStep.getBands().get(0).equals(steps.get(i - 1).getBands().get(0));

        if (!equalContent) {
            segment += "$\n\\begin{smallmatrix} \n";
            for (int j = 0; j < currentBand.length(); j++) {
                segment += currentBand.charAt(j);
                if (previousStep != null && currentStep.isNdtmStep() && j == previousStep.getPosition()[0]) {
                    segment += "_\\boxdot";
                }

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
            segment += " \n\\end{smallmatrix}\n$  \n";
        } else if (!currentBand.equals(steps.get(i + 1).getBands().get(0))) {
            segment += " \n\\end{smallmatrix}\n$ \n" + MATRIX_SEPARATOR + "\n";

        } else {
            segment += "\\\\";
        }

        return segment;

    }

    private static String generateSegmentForMultiBandContext(List<TuringStep> steps, int i) {

        String segment = "";
        TuringStep currentStep = steps.get(i);
        List<String> currentBands = currentStep.getBands();
        String currentState = currentStep.getState();
        TuringStep previousStep = null;
        if (i > 0) {
            previousStep = steps.get(i - 1);

        }


        for (int j = 0; j < currentBands.size(); j++) {
            int currentPosition = currentStep.getPosition()[j];
            segment += "$\n \\begin{smallmatrix} \n";

            String currentBand = currentBands.get(j);


            segment += "[" + (j + 1) + "] & ";
            for (int k = 0; k < currentBand.length(); k++) {
                segment += currentBand.charAt(k);

                if (previousStep != null && currentStep.isNdtmStep() && k == previousStep.getPosition()[j]) {
                    segment += "_\\boxdot";
                }

                if (k < currentBand.length() - 1) {
                    segment += " & ";
                }
            }

            segment += "\\\\ \n";

            String arrowPart = " & ";
            String statePart = " & ";

            for (int k = 0; k < currentBand.length(); k++) {
                if (k == currentPosition) {
                    arrowPart += " \\uparrow  ";
                    statePart += currentState;
                }

                if (k < currentBand.length() - 1) {
                    arrowPart += " & ";
                    statePart += " & ";
                }

            }

            segment += arrowPart + "\\\\ \n" + statePart + " \n\\end{smallmatrix} \n$ \n";

            if (j != currentBands.size() - 1) {
                segment += "\n" + MATRIX_SEPARATOR + "\n\n";
            }


        }


        return segment;
    }

}