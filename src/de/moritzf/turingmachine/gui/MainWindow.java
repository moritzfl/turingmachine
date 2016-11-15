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
package de.moritzf.turingmachine.gui;

import de.moritzf.turingmachine.gui.util.WindowUtil;
import de.moritzf.turingmachine.io.SaveFile;
import de.moritzf.turingmachine.latex.LatexExporter;
import de.moritzf.turingmachine.logic.TuringMachine;
import de.moritzf.turingmachine.logic.TuringStep;
import de.moritzf.turingmachine.logic.TuringTask;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Class MainWindow. This is the class that constitutes the Main application window.
 * Created by moritz on 11.11.16.
 */
public class MainWindow extends JFrame implements ActionListener, FocusListener, ChangeListener {

    private JButton start = new JButton("Start");
    private static final Font TEXT_AREA_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 14);
    private JMenuItem saveProtocolMenuItem = new JMenuItem("Save Protocol");
    private JMenuItem saveTasksMenuItem = new JMenuItem("Save Tasks");
    private JMenuItem increaseFontSize = new JMenuItem("Increase Font Size");
    private JMenuItem decreaseFontSize = new JMenuItem("Decrease Font Size");
    private JMenuItem resetFontSize = new JMenuItem("Reset Font Size");
    private JTextArea bandsTxtArea = new JTextArea("IIIII");
    private JTextArea taskListTxtArea = new JTextArea("z0 I a R z0\nz0 I b R z0");
    private LatexPanel protocolPanel = new LatexPanel();
    private JSlider stepSlider = new JSlider(JSlider.HORIZONTAL, 10, 250, 100);
    private JLabel numberOfSteps = new JLabel("max. Steps = 100");
    private static final Highlighter.HighlightPainter ERROR_PAINTER =
            new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE);

    /**
     * Instantiates a new Main window.
     */
    public MainWindow() {
        super("Turing");
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        taskListTxtArea.setFont(TEXT_AREA_FONT);
        bandsTxtArea.setFont(TEXT_AREA_FONT);

        //Create the menu bar.
        JMenuBar menuBar = new JMenuBar();

        //Build the first menu.
        JMenu fileMenu = new JMenu("File");

        fileMenu.getAccessibleContext().setAccessibleDescription(
                "File menu");
        menuBar.add(fileMenu);

        JMenu viewMenu = new JMenu("View");

        fileMenu.getAccessibleContext().setAccessibleDescription(
                "View menu");
        menuBar.add(viewMenu);

        fileMenu.add(saveProtocolMenuItem);
        saveProtocolMenuItem.addActionListener(this);
        saveProtocolMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        fileMenu.addSeparator();
        fileMenu.add(saveTasksMenuItem);
        saveTasksMenuItem.addActionListener(this);
        saveTasksMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));


        viewMenu.add(resetFontSize);
        resetFontSize.addActionListener(this);
        resetFontSize.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_0, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        viewMenu.add(decreaseFontSize);
        decreaseFontSize.addActionListener(this);
        decreaseFontSize.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_MINUS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        viewMenu.add(increaseFontSize);
        increaseFontSize.addActionListener(this);
        increaseFontSize.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_PLUS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));


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

        JScrollPane protocolScrollPane = new JScrollPane(formattingPanel);
        protocolScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        protocolScrollPane.getHorizontalScrollBar().setUnitIncrement(20);

        right.add(protocolScrollPane, BorderLayout.CENTER);
        formattingPanel.setBackground(Color.white);

        JPanel startPanel = new JPanel();


        startPanel.setLayout(new GridLayout(0, 1));
        startPanel.add(numberOfSteps);
        startPanel.add(stepSlider);
        startPanel.add(start);
        left.add(startPanel, BorderLayout.SOUTH);


        start.addActionListener(this);
        stepSlider.addChangeListener(this);

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

        this.setJMenuBar(menuBar);
        this.setSize(500, 500);
        WindowUtil.centerWindow(this);
        WindowUtil.enableOSXFullscreen(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(start)) {

            List<String> bands = getBandsInput();
            List<TuringTask> tasks = getTasksInput();


            if (tasks != null && !tasks.isEmpty()) {

                System.out.println("Starting Turingmachine with Bands: ");
                for (String band : bands) {
                    System.out.println(band);
                }

                System.out.println("And Tasks: ");
                for (TuringTask task : tasks) {
                    System.out.println(task);
                }

                TuringMachine tm = new TuringMachine(tasks, bands);
                List<TuringStep> steps = tm.executeTuringMachine(stepSlider.getValue());

                protocolPanel.setExpression(LatexExporter.generateLatex(steps));
                this.validate();
                this.repaint();
            }
        } else if (e.getSource().equals(increaseFontSize)) {
            protocolPanel.increaseFontSize();
            this.validate();
            this.repaint();
        } else if (e.getSource().equals(decreaseFontSize)) {
            protocolPanel.decreaseFontSize();
            this.validate();
            this.repaint();
        } else if (e.getSource().equals(resetFontSize)) {
            protocolPanel.resetFontSize();
            this.validate();
            this.repaint();
        } else if (e.getSource().equals(saveProtocolMenuItem)) {
            SaveFile.saveLaTeX(this, protocolPanel.getExpression());
        } else if (e.getSource().equals(saveTasksMenuItem)) {
            SaveFile.saveFile(this, "txt", taskListTxtArea.getText());
        }

    }

    /**
     * Gets bands input.
     *
     * @return the bands input
     */
    private List<String> getBandsInput() {
        if (bandsTxtArea.getText().isEmpty()) {
            bandsTxtArea.setText("*");
        }
        String[] bands = bandsTxtArea.getText().replace(" ", "").replace("\t", "").split("\n");
        System.out.println("Bands: " + bands.length);
        return Arrays.asList(bands);
    }


    /**
     * Gets tasks input.
     *
     * @return the tasks input
     */
    private List<TuringTask> getTasksInput() {
        int numberOfBands = getBandsInput().size();
        ArrayList<TuringTask> tasks = new ArrayList<>();
        String textAreaContent = taskListTxtArea.getText();
        String[] lines = textAreaContent.split("\n");
        boolean error = false;


        for (String line : lines) {
            String[] lineParts = line.replace("\t", " ").split(" ");
            if (lineParts.length != 5) {
                highLightTextInTaskArea(line);
                error = true;
            } else if (!(lineParts[1].length() == lineParts[2].length()
                    && lineParts[2].length() == lineParts[3].length())) {
                highLightTextInTaskArea(line);
                error = true;
            } else {
                TuringTask task = new TuringTask(lineParts[0], lineParts[1], lineParts[2], lineParts[3], lineParts[4]);
                if (task.checkTaskForBandNumber(numberOfBands)) {
                    tasks.add(task);
                } else {
                    highLightTextInTaskArea(line);
                    error = true;
                }
            }
        }

        if (!error) {
            return tasks;
        } else {
            return null;
        }

    }

    private void highLightTextInTaskArea(String text) {
        int p0 = taskListTxtArea.getText().indexOf(text);
        int p1 = p0 + text.length();
        try {
            taskListTxtArea.getHighlighter().addHighlight(p0, p1, ERROR_PAINTER);
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

    @Override
    public void stateChanged(ChangeEvent e) {
        numberOfSteps.setText("max. Steps = " + stepSlider.getValue());
    }
}
