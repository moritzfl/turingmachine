package de.moritzf.gui;

import de.moritzf.latex.LatexExporter;
import de.moritzf.logic.TuringMachine;
import de.moritzf.logic.TuringStep;
import de.moritzf.logic.TuringTask;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by moritz on 11.11.16.
 */
public class MainWindow extends JFrame implements ActionListener , FocusListener {

    private JButton start = new JButton("Start");
    private JTextArea bandsTxtArea = new JTextArea();
    private JTextArea taskListTxtArea = new JTextArea();
    private LaTeXPanel protocolPanel = new LaTeXPanel();
    private Highlighter.HighlightPainter errorPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);

    public MainWindow() {
        super("Turing");
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container mainpane = this.getContentPane();
        JPanel mainpanel = new JPanel();
        mainpane.add(mainpanel);

        mainpanel.setLayout(new BorderLayout());

        JPanel left = new JPanel();
        JPanel right = new JPanel();

        left.setLayout(new BorderLayout());
        right.setLayout(new BorderLayout());

        JPanel formattingPanel = new JPanel();
        formattingPanel.setLayout(new BorderLayout());
        formattingPanel.add(protocolPanel, BorderLayout.WEST);

        right.add(new JScrollPane(formattingPanel), BorderLayout.CENTER);
        formattingPanel.setBackground(Color.white);

        left.add(start, BorderLayout.SOUTH);
        start.addActionListener(this);
        taskListTxtArea.addFocusListener(this);

        JPanel leftTop = new JPanel();
        leftTop.setLayout(new BorderLayout());
        JPanel leftBottom = new JPanel();
        leftBottom.setLayout(new BorderLayout());

        leftTop.add(new JScrollPane(bandsTxtArea), BorderLayout.CENTER);
        leftBottom.add(new JScrollPane(taskListTxtArea), BorderLayout.CENTER);

        JSplitPane verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, leftTop, leftBottom);
        left.add(verticalSplit, BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        mainpanel.add(split, BorderLayout.CENTER);
        this.setSize(500, 500);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        List<String> bands = getBandsInput();
        List<TuringTask> tasks = getTasksInput();


        if (tasks != null && !tasks.isEmpty()){

            System.out.println("Starting Turingmachine with Bands: ");
            for (String band: bands){
                System.out.println(band);
            }

            System.out.println("And Tasks: ");
            for (TuringTask task: tasks){
                System.out.println(task);
            }

            TuringMachine tm = new TuringMachine(tasks, bands);
            List<TuringStep> steps = tm.executeTuringMachine(100);
            protocolPanel.setExpression(LatexExporter.generateLatex(steps));
            this.validate();
            this.repaint();
        }

    }

    public List<String> getBandsInput() {
        if (bandsTxtArea.getText().isEmpty()) {
            bandsTxtArea.setText("*");
        }
        String[] bands = bandsTxtArea.getText().replace(" ", "").replace("\t", "").split("\n");
        System.out.println("Bands: " + bands.length);
        return Arrays.asList(bands);
    }




    public List<TuringTask> getTasksInput() {
        int numberOfBands = getBandsInput().size();
        ArrayList<TuringTask> tasks = new ArrayList<>();
        String textAreaContent = taskListTxtArea.getText();
        String[] lines = textAreaContent.split("\n");
        Highlighter highlighter = taskListTxtArea.getHighlighter();
        boolean error = false;


        for (String line : lines) {
            String[] lineParts = line.replace("\t", " ").split(" ");
            if (lineParts.length != 5) {
                highLightTextInTaskArea(line, errorPainter);
                error = true;
            } else if (!(lineParts[1].length() == lineParts[2].length()
                        && lineParts[2].length() == lineParts[3].length())) {
                highLightTextInTaskArea(line, errorPainter);
                error = true;
            } else {
                TuringTask task = new TuringTask(lineParts[0], lineParts[1], lineParts[2], lineParts[3], lineParts[4]);
                if (task.checkTaskForBandNumber(numberOfBands)){
                    tasks.add(task);
                } else {
                    highLightTextInTaskArea(line, errorPainter);
                    error = true;
                }
            }
        }

        if (!error){
            return tasks;
        } else {
            return null;
        }

    }

    private void highLightTextInTaskArea(String text, Highlighter.HighlightPainter painter){
        int p0 = taskListTxtArea.getText().indexOf(text);
        int p1 = p0 + text.length();
        try {
            taskListTxtArea.getHighlighter().addHighlight(p0, p1, errorPainter);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        taskListTxtArea.getHighlighter().removeAllHighlights();
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
