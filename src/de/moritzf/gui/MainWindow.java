package de.moritzf.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by moritz on 11.11.16.
 */
public class MainWindow extends JFrame {

    JButton start = new JButton("Start");

    public MainWindow(){
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

        left.add(start, BorderLayout.SOUTH);
        JPanel leftTop = new JPanel();
        JPanel leftBottom = new JPanel();

        JSplitPane verticalSplit =  new JSplitPane(JSplitPane.VERTICAL_SPLIT, leftTop, leftBottom);
        left.add(verticalSplit, BorderLayout.CENTER);

        JSplitPane split =  new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        mainpanel.add(split, BorderLayout.CENTER);
        this.setSize(500,500);


    }
}
