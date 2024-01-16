package org.aaronwang.macro;

import com.github.kwhat.jnativehook.*;
import com.github.kwhat.jnativehook.mouse.*;
import com.github.kwhat.jnativehook.keyboard.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MacroRecorder extends SwingKeyAdapter implements NativeMouseMotionListener, NativeMouseWheelListener, NativeMouseInputListener, NativeKeyListener {
    // Gets screen pixels, accounting for system scaling
    private final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    // Get the scale factor --> 100% scaling is 96.0 DPI
    private final double scaleFactor = Toolkit.getDefaultToolkit().getScreenResolution() / 96.0;
    // Calculate the screen's X and Y based on scaling info - for Robot's usage.
    private final int screenX = (int)(Math.ceil(screen.width * scaleFactor));
    private final int screenY = (int)(Math.ceil(screen.height * scaleFactor));
    // Get system time to determine timings between input
    private long clock = System.currentTimeMillis();
    private BufferedWriter writer;
    private File macroFile = null;
    // Temporarily store all inputs in ArrayList
    private final ArrayList<String> temp = new ArrayList<>();
    private boolean isRecording;
    private final JTextArea output;
    public MacroRecorder(JTextArea output) throws NativeHookException {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeMouseMotionListener(this);
        GlobalScreen.addNativeMouseListener(this);
        GlobalScreen.addNativeMouseWheelListener(this);
        GlobalScreen.addNativeKeyListener(this);
        this.output = output;
    }
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if(!isRecording) return;
        if(getJavaKeyEvent(e).getKeyCode() == 20) return; // Disallow Caps Lock key due to unpredictable behaviour
        calcInterval();
        if(output.getText().isEmpty()) output.setText("PRESS_K " + getJavaKeyEvent(e).getKeyCode());
        else output.append("\nPRESS_K " + getJavaKeyEvent(e).getKeyCode());
        temp.add("PRESS_K " + getJavaKeyEvent(e).getKeyCode());
    }
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if(!isRecording) return;
        if(getJavaKeyEvent(e).getKeyCode() == 20) return; // Disallow Caps Lock key due to unpredictable behaviour
        calcInterval();
        if(output.getText().isEmpty()) output.setText("RELEASE_K " + getJavaKeyEvent(e).getKeyCode());
        else output.append("\nRELEASE_K " + getJavaKeyEvent(e).getKeyCode());
        temp.add("RELEASE_K " + getJavaKeyEvent(e).getKeyCode());
    }
    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        if(!isRecording) return;
        calcInterval();
        if(output.getText().isEmpty()) output.setText("PRESS_M " + e.getButton());
        else output.append("\nPRESS_M " + e.getButton());
        temp.add("PRESS_M " + e.getButton());
    }
    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        if(!isRecording) return;
        calcInterval();
        if(output.getText().isEmpty()) output.setText("RELEASE_M " + e.getButton());
        else output.append("\nRELEASE_M " + e.getButton());
        temp.add("RELEASE_M " + e.getButton());
    }
    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        if(!isRecording) return;
        calcInterval();
        int x = (int)(Math.ceil(e.getX() / scaleFactor));
        int y = (int)(Math.ceil(e.getY() / scaleFactor));

        if(x < 0) x = 0;
        else if (x > screenX) x = screenX;
        if(y < 0) y = 0;
        else if (y > screenX) y = screenY;

        if(output.getText().isEmpty()) output.setText("MOVE_M " + x + ", " + y);
        else output.append("\nMOVE_M " + x + ", " + y);
        temp.add("MOVE_M " + x + ", " + y);

    }
    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        // Mouse Listener does not detect mouse motion when a mouse button is held,
        // hence this acts as a secondary mouseMoved event listener.
        if(!isRecording) return;
        calcInterval();
        int x = (int)(Math.ceil(e.getX() / scaleFactor));
        int y = (int)(Math.ceil(e.getY() / scaleFactor));

        if(x < 0) x = 0;
        else if (x > screenX) x = screenX;
        if(y < 0) y = 0;
        else if (y > screenX) y = screenY;

        if(output.getText().isEmpty()) output.setText("MOVE_M " + x + ", " + y);
        else output.append("\nMOVE_M " + x + ", " + y);
        temp.add("MOVE_M " + x + ", " + y);
    }
    @Override
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
        if(!isRecording) return;
        if(output.getText().isEmpty()) output.setText("SCROLL " + e.getWheelRotation());
        else output.append("\nSCROLL " + e.getWheelRotation());
        temp.add("SCROLL " + e.getWheelRotation());
    }

    private void calcInterval() {
        long time = System.currentTimeMillis() - clock;
        if(output.getText().isEmpty()) output.setText("WAIT " + time);
        else output.append("\nWAIT " + time);
        temp.add("WAIT " + time);
        clock = System.currentTimeMillis();
        output.setCaretPosition(output.getDocument().getLength());
    }

    public void start() throws NativeHookException, IOException {
        // Temporary files get saved to this location.
        File macroFileDirectory = new File(new JFileChooser().getFileSystemView().getDefaultDirectory().getAbsolutePath() + "/MicroMacroTemp");
        // Create directory if it does not already exist.
        macroFileDirectory.mkdirs();
        // Instantiate the macro file
        macroFile = new File(macroFileDirectory.getAbsolutePath() + "\\temp-" + System.currentTimeMillis() + ".micromacro");
        // If the file was created, all listeners will become active.
        if (macroFile.createNewFile()) {
            writer = new BufferedWriter(new FileWriter(macroFile));
            temp.add("SYSTEM-SCALE-FACTOR " + scaleFactor);
            clock = System.currentTimeMillis();
            isRecording = true;
        } else {
            JOptionPane.showMessageDialog(null, "An error occurred when recording!", "Error!", JOptionPane.ERROR_MESSAGE);
        }

    }
    public void close() throws NativeHookException, IOException {
        if(!isRecording) return;
        for(String s : temp) writer.write(s + "\n");
        writer.close();
        temp.clear();
        isRecording = false;
    }

    public void clearFile() {
        output.setText("");
        macroFile = null;
    }
    public void save() {
        if(macroFile == null) {
            JOptionPane.showMessageDialog(null, "You have nothing recorded!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int userChoice = fileChooser.showSaveDialog(output.getParent().getParent().getParent());
        if(userChoice != JFileChooser.APPROVE_OPTION) return;

        File destination = (fileChooser.getSelectedFile().getAbsolutePath().endsWith(".micromacro"))
                           ? new File(fileChooser.getSelectedFile().getAbsolutePath())
                           : new File(fileChooser.getSelectedFile().getAbsolutePath() + ".micromacro");

        if(!macroFile.renameTo(destination)) {
            JOptionPane.showMessageDialog(null, "The file was not saved!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null, "Your file was saved.", "Note!", JOptionPane.INFORMATION_MESSAGE);

    }
}
