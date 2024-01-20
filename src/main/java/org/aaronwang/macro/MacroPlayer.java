package org.aaronwang.macro;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MacroPlayer extends JPanel {
    // Get the scale factor --> 100% scaling is 96.0 DPI
    private final double scaleFactor = Toolkit.getDefaultToolkit().getScreenResolution() / 96.0;
    BufferedReader br;
    Robot robot;
    int curLine = 1;
    boolean shouldStopPlayback = false;
    File fileToPlay;
    // Boolean to track whether the buffered reader is closed, such that it can be reopened in the future.
    boolean isClosed = false;
    // Each instantiation of MacroPlayer is only suitable to run one file.
    // Create necessary objects to run the macro file upon instantiation.
    public MacroPlayer(File file) throws FileNotFoundException, AWTException {
        this.fileToPlay = file;
        br = new BufferedReader(new FileReader(fileToPlay));
        robot = new Robot();
    }
    public void playFile() throws IOException {
        // If the buffered reader is close, instantiate it.
        if(isClosed) {
            br = new BufferedReader(new FileReader(fileToPlay));
            isClosed = false;
        }
        // If improper scaling information is found and the user does not wish to proceed:
        if(!validateScale()) return;
        String temp;

        // Continuously validate each new command, whilst ensuring that we have not yet reached the end of the file.
        while(!shouldStopPlayback && ((temp = br.readLine()) != null)) {
            curLine++;
            // Execute specified inputs.
            if(temp.startsWith("PRESS_K ")) pressKey(temp);
            else if (temp.startsWith("RELEASE_K ")) releaseKey(temp);
            else if (temp.startsWith("PRESS_M ")) pressMouse(temp);
            else if (temp.startsWith("RELEASE_M ")) releaseMouse(temp);
            else if (temp.startsWith("MOVE_M ")) moveMouse(temp);
            else if (temp.startsWith("SCROLL ")) scrollMouse(temp);
            else if (temp.startsWith("WAIT ")) pause(temp);

        }
        br.close();
        isClosed = true;
    }

    private boolean validateScale() throws IOException {
        String temp;
        int choice;
        double scale;
        // Read the first line only, which SHOULD be scaling information
        temp = br.readLine();
        // If scaling information is not present:
        if(!temp.contains("SYSTEM-SCALE-FACTOR ")) {
            choice = JOptionPane.showConfirmDialog(null,
                    "The loaded file has no system scaling information. Proceed anyway?",
                    "Warning!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            return choice == 0;
        }

        temp = temp.replace("SYSTEM-SCALE-FACTOR ", "");
        // Try to read scaling info
        try {
            scale = Double.parseDouble(temp);
        } catch(NumberFormatException ignored) {
            choice = JOptionPane.showConfirmDialog(null,
                    "The system scaling information is invalid. Proceed anyway?",
                    "Warning!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            return choice == 0;
        }

        // If current scaling does not match with what is recorded:
        if(scale != scaleFactor) {
            choice = JOptionPane.showConfirmDialog(null,
                    "The scale factor of " + scale + "x does not match the current scale factor. Proceed anyway?",
                    "Warning!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            return choice == 0;
        }
        return true;
    }

    private void pressKey(String s) {
        // Get the command and parse it, stripping away identifier.
        String cmd = s.replace("PRESS_K ", "");
        int keyCode;
        // Try to parse the integer as a valid keycode.
        // If error, let the user know which line is invalid, then stop playback.
        try {
            keyCode = Integer.parseInt(cmd);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null,
                    "Invalid keycode on line " + curLine + ". Playback will cease.",
                    "Warning!",
                    JOptionPane.WARNING_MESSAGE);
            shouldStopPlayback = true;
            return;
        }
        robot.keyPress(keyCode);
    }
    private void releaseKey(String s) {
        String cmd = s.replace("RELEASE_K ", "");
        int keyCode;
        try {
            keyCode = Integer.parseInt(cmd);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null,
                    "Invalid keycode on line " + curLine + ". Playback will cease.",
                    "Warning!",
                    JOptionPane.WARNING_MESSAGE);
            shouldStopPlayback = true;
            return;
        }
        robot.keyRelease(keyCode);
    }
    private void pressMouse(String s) {
        String cmd = s.replace("PRESS_M ", "");
        int mouseCode;
        try {
            mouseCode = Integer.parseInt(cmd);
            mouseCode = switch (mouseCode) {
                case 1 -> InputEvent.BUTTON1_DOWN_MASK;
                case 2 -> InputEvent.BUTTON2_DOWN_MASK;
                case 3 -> InputEvent.BUTTON3_DOWN_MASK;
                default -> mouseCode;
            };
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null,
                    "Invalid mousecode on line " + curLine + ". Playback will cease.",
                    "Warning!",
                    JOptionPane.WARNING_MESSAGE);
            shouldStopPlayback = true;
            return;
        }
        robot.mousePress(mouseCode);
    }
    private void releaseMouse(String s) {
        String cmd = s.replace("RELEASE_M ", "");
        int mouseCode;
        try {
            mouseCode = Integer.parseInt(cmd);
            mouseCode = switch (mouseCode) {
                case 1 -> InputEvent.BUTTON1_DOWN_MASK;
                case 2 -> InputEvent.BUTTON2_DOWN_MASK;
                case 3 -> InputEvent.BUTTON3_DOWN_MASK;
                default -> mouseCode;
            };
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null,
                    "Invalid mousecode on line " + curLine + ". Playback will cease.",
                    "Warning!",
                    JOptionPane.WARNING_MESSAGE);
            shouldStopPlayback = true;
            return;
        }
        robot.mouseRelease(mouseCode);
    }
    private void moveMouse(String s) {
        String cmd = s.replace("MOVE_M ", "");
        String[] coordsString;
        int[] coords = new int[2];
        try {
            coordsString = cmd.split(", ");
            coords[0] = Integer.parseInt(coordsString[0]);
            coords[1] = Integer.parseInt(coordsString[1]);
        } catch(NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null,
                    "Invalid coordinates on line " + curLine + ". Playback will cease.",
                    "Warning!",
                    JOptionPane.WARNING_MESSAGE);
            shouldStopPlayback = true;
            return;
        }
        robot.mouseMove(coords[0], coords[1]);
    }
    private void scrollMouse(String s) {
        String cmd = s.replace("SCROLL ", "");
        int scrollAmt;
        try {
            scrollAmt = Integer.parseInt(cmd);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null,
                    "Invalid scroll amount on line " + curLine + ". Playback will cease.",
                    "Warning!",
                    JOptionPane.WARNING_MESSAGE);
            shouldStopPlayback = true;
            return;
        }
        robot.mouseWheel(scrollAmt);
    }
    private void pause(String s) {
        String cmd = s.replace("WAIT ", "");
        int waitTimeMS;
        try {
            waitTimeMS = Integer.parseInt(cmd);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null,
                    "Invalid wait time on line " + curLine + ". Playback will cease.",
                    "Warning!",
                    JOptionPane.WARNING_MESSAGE);
            shouldStopPlayback = true;
            return;
        }
        robot.delay(waitTimeMS);
    }
}