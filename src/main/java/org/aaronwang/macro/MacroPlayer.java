package org.aaronwang.macro;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.InputEvent;
import java.io.*;
import java.awt.*;

public class MacroPlayer extends JPanel {
    // Get the scale factor --> 100% scaling is 96.0 DPI
    private final double scaleFactor = Toolkit.getDefaultToolkit().getScreenResolution() / 96.0;
    BufferedReader br;
    Robot robot;
    int curLine = 1;
    boolean shouldStopPlayback = false;
    File fileToPlay;
    boolean isClosed = false;
    public MacroPlayer(File file) throws FileNotFoundException, AWTException {
        this.fileToPlay = file;
        br = new BufferedReader(new FileReader(fileToPlay));
        robot = new Robot();
    }
    public void playFile() throws IOException {
        if(isClosed) {
            br = new BufferedReader(new FileReader(fileToPlay));
            isClosed = false;
        }
        if(!validateScale()) return;
        String temp;
        while(!shouldStopPlayback && ((temp = br.readLine()) != null)) {
            curLine++;

            if(temp.startsWith("PRESS_K ")) pressKey(temp);
            else if (temp.startsWith("RELEASE_K ")) releaseKey(temp);
            else if (temp.startsWith("PRESS_M ")) pressMouse(temp);
            else if (temp.startsWith("RELEASE_M ")) releaseMouse(temp);
            else if (temp.startsWith("MOVE_M ")) moveMouse(temp);
            else if (temp.startsWith("SCROLL ")) scrollMouse(temp);
            else if (temp.startsWith("WAIT ")) pause(temp);

        }
        // Returns true if entire playback has finished.
        br.close();
        isClosed = true;
    }

    private boolean validateScale() throws IOException {
        String temp;
        double scale = 0;
        // Read the first line only, which SHOULD be scaling information
        temp = br.readLine();
        // If scaling information is not present:
        if(!temp.contains("SYSTEM-SCALE-FACTOR ")) {
            int choice = JOptionPane.showConfirmDialog(null,
                    "The loaded file has no system scaling information. Proceed anyway?",
                    "Warning!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if(choice != 0) return false;
        }

        temp = temp.replace("SYSTEM-SCALE-FACTOR ", "");
        // Try to read scaling info
        try {
            scale = Double.parseDouble(temp);
        } catch(NumberFormatException ignored) {
            int choice = JOptionPane.showConfirmDialog(null,
                    "The system scaling information is invalid. Proceed anyway?",
                    "Warning!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if(choice != 0) return false;

            if(scale != scaleFactor) {
                int choice2 = JOptionPane.showConfirmDialog(null,
                        "The recorded scale factor of " + scale + " does not match the current scale factor. Proceed anyway?",
                        "Warning!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if(choice2 != 0) return false;
            }
        }
        return true;
    }

    private void pressKey(String s) {
        String cmd = s.replace("PRESS_K ", "");
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

    public void sendHelp() {
        shouldStopPlayback = true;
    }

}
