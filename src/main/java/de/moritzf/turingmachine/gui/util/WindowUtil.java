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
package de.moritzf.turingmachine.gui.util;


import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * WindowUtil.
 * provides useful functions that can be applied to single windows.
 * Created by moritz on 31/07/16.
 */
public class WindowUtil {

    /**
     * Centers the window passed to it.
     *
     * @param window the window
     */
    public static final void centerWindow(final Window window) {
        GraphicsDevice screen = MouseInfo.getPointerInfo().getDevice();
        Rectangle r = screen.getDefaultConfiguration().getBounds();
        int x = (r.width - window.getWidth()) / 2 + r.x;
        int y = (r.height - window.getHeight()) / 2 + r.y;
        window.setLocation(x, y);
    }

    /**
     * Enables OSX fullscreen mode for window.
     *
     * @param window the window
     */
    public static void enableOSXFullscreen(JFrame window) {
        if (OsUtil.getOperatingSystemType().equals(OsUtil.OSType.MacOS)) {
            window.getRootPane().putClientProperty("apple.awt.fullscreenable", Boolean.valueOf(true));
        }
    }
}
