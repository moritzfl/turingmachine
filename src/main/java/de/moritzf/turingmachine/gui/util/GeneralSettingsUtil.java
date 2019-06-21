package de.moritzf.turingmachine.gui.util;



import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * GeneralSettingsUtil.
 * Provides general settings for the gui that are valid vor all windows.
 * Created by moritz on 31/07/16.
 */
public class GeneralSettingsUtil {


    /**
     * Sets system look and feel.
     */
    public static void setSystemLookAndFeel() {

        // Set System look and feel according to the current OS
        try {
            if (OsCheck.getOperatingSystemType().equals(OsCheck.OSType.MacOS)) {
                try {
                    UIManager.setLookAndFeel("org.violetlib.aqua.AquaLookAndFeel");
                } catch (Exception e) {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                activateMacMenu();
            } else {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }

        } catch (Exception e) {
            //no "system like" style will be available
            e.printStackTrace();
        }
    }

    /**
     * Activate mac menu. This enables the native mac menu bar instead of the "Windows-like" menu inside of frames.
     */
    public static void activateMacMenu() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    public static boolean isDarkModeActive() {
        final Process p;
        boolean darkMode = false;
        if (OsCheck.getOperatingSystemType().equals(OsCheck.OSType.MacOS)) {
            StringBuilder output = new StringBuilder();
            try {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("defaults", "read", "-g", "AppleInterfaceStyle");
                Process process = processBuilder.start();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }

                int exitVal = process.waitFor();
                darkMode = exitVal == 0 && output.toString().contains("Dark");
            } catch (IOException | InterruptedException e) {
                //Nothing to do here
            }

            System.out.println("Mode" + output.toString() + "Mode");
        }

        return darkMode;

    }


}
