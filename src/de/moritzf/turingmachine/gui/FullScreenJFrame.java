package de.moritzf.turingmachine.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FullScreenJFrame extends JFrame implements KeyListener{
    private JPanel contentPane = new JPanel();
    static GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices()[1];

    public static void main(String[] args) {
        FullScreenJFrame frame = new FullScreenJFrame();
    }

    public FullScreenJFrame() {
        super("Presentation Mode");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        device.setFullScreenWindow(this);
        this.setContentPane(contentPane);
        this.addKeyListener(this);

    }

    public void exitWindow() {
        setVisible(false);
        dispose();
    }


    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() ==  KeyEvent.VK_ESCAPE) {
            exitWindow();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() ==  KeyEvent.VK_ESCAPE) {
            exitWindow();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() ==  KeyEvent.VK_ESCAPE) {
            exitWindow();
        }
    }
}