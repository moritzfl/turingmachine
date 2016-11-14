package de.moritzf.turingmachine.gui.util;

import sun.awt.OSInfo;

import javax.swing.*;


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
        OSInfo.OSType osType = OSInfo.getOSType();

        // Set System look and feel according to the current OS
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            if (osType.equals(OSInfo.OSType.MACOSX)) {
                activateMacMenu();
            }
        } catch (Exception e) {
            //no "system like" style will be available
        }
    }

    /**
     * Activate mac menu. This enables the native mac menu bar instead of the "Windows-like" menu inside of frames.
     */
    public static void activateMacMenu() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
    }


}
