package org.aaronwang.panels;

import com.github.kwhat.jnativehook.NativeHookException;
import org.aaronwang.UI.GradientPanel;
import org.aaronwang.macro.MacroPlayer;
import org.aaronwang.macro.MacroRecorder;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class MacroPanel extends GradientPanel implements ActionListener, KeyListener {
    private JButton loadPlayBtn;
    private JButton recBtn;
    private JButton stopBtn;
    private JButton saveBtn;
    private JButton backBtn;
    private JTextArea macroReadout = new JTextArea();
    private JScrollPane readoutScroller;
    private ActionListener gui;
    private JFrame frame;
    private final MacroRecorder macroRecorder; {
        try { macroRecorder = new MacroRecorder(macroReadout); }
        catch (NativeHookException e) { throw new RuntimeException(e); }
    }
    private MacroPlayer macroPlayer;
    private File loadedFile;
    private boolean loopPlayback = false;

    public MacroPanel(ActionListener gui) {
        super(new Color(0x1d1b1e), new Color(0x342d40), GradientPanel.DIAGONAL_FILL);
        this.setPreferredSize(new Dimension(1000, 624));
        this.setLayout(null);
        this.gui = gui;
        this.frame = (JFrame) gui;

        JLabel title = new JLabel("Macro Recorder", JLabel.CENTER);
        title.setForeground(new Color(0xcdc2db)); // Secondary
        title.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 48));
        title.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0x4b4358), 8, true), // Secondary container
                "MicroMacro",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Century Gothic", Font.BOLD | Font.ITALIC, 26),
                new Color(0xe9def8))); // On secondary container

        loadPlayBtn = new JButton("Load File");
        loadPlayBtn.setContentAreaFilled(false);
        loadPlayBtn.setForeground(new Color(0xcdc2db)); // Tertiary
        loadPlayBtn.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 24));
        loadPlayBtn.setFocusable(false);
        loadPlayBtn.addActionListener(this);

        recBtn = new JButton("Record File");
        recBtn.setContentAreaFilled(false);
        recBtn.setForeground(new Color(0xcdc2db)); // Tertiary
        recBtn.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 24));
        recBtn.setFocusable(false);
        recBtn.addActionListener(this);

        stopBtn = new JButton("Stop Recording");
        stopBtn.setContentAreaFilled(false);
        stopBtn.setForeground(new Color(0xcdc2db)); // Tertiary
        stopBtn.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 22));
        stopBtn.setFocusable(false);
        stopBtn.setEnabled(false);
        stopBtn.addActionListener(this);

        saveBtn = new JButton("Save File");
        saveBtn.setContentAreaFilled(false);
        saveBtn.setForeground(new Color(0xcdc2db)); // Tertiary
        saveBtn.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 24));
        saveBtn.setFocusable(false);
        saveBtn.addActionListener(this);

        backBtn = new JButton("Back");
        backBtn.setContentAreaFilled(false);
        backBtn.setForeground(new Color(0xcdc2db));
        backBtn.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 24));
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);

        macroReadout.setMargin(new Insets(10, 10, 10, 10));
        macroReadout.setLineWrap(true);
        macroReadout.setWrapStyleWord(true);
        macroReadout.setFont(new Font("Century Gothic", Font.BOLD, 24));
        macroReadout.setOpaque(false);
        macroReadout.setForeground(new Color(0xcdc2db));
        macroReadout.setCaretColor(new Color(0xe9def8));
        macroReadout.setEditable(false);

        readoutScroller = new JScrollPane(macroReadout, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        readoutScroller.getViewport().setOpaque(false);
        readoutScroller.setOpaque(false);

        this.add(title);
        title.setBounds(290, 20, 420, 110);

        this.add(loadPlayBtn);
        loadPlayBtn.setBounds(140, 178, 200, 64);

        this.add(recBtn);
        recBtn.setBounds(140, 290, 200, 64);

        this.add(stopBtn);
        stopBtn.setBounds(140, 402, 200, 64);

        this.add(saveBtn);
        saveBtn.setBounds(140, 514, 200, 64);

        this.add(backBtn);
        backBtn.setBounds(140, 55, 100, 50);

        this.add(readoutScroller);
        readoutScroller.setBounds(390, 178,470, 400);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == recBtn) record();
        else if(e.getSource() == stopBtn) stopRecord();
        else if(e.getSource() == saveBtn) macroRecorder.save();
        else if(e.getSource() == backBtn) back();
        else if(e.getSource() == loadPlayBtn && loadPlayBtn.getText().equals("Load File")) loadFile();
        else if(e.getSource() == loadPlayBtn && loadPlayBtn.getText().equals("Play File")) {
            try {
                playFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void back() {

        ActionEvent event = new ActionEvent(backBtn, 69, "Back");
        macroRecorder.clearFile();

        backBtn.removeActionListener(this);
        backBtn.addActionListener(gui);
        backBtn.getActionListeners()[0].actionPerformed(event);
        backBtn.removeActionListener(gui);
        backBtn.addActionListener(this);

        loadPlayBtn.setText("Load File");
    }

    private void record() {
        try {
            macroRecorder.start();
        } catch (NativeHookException | IOException e) {
            throw new RuntimeException(e);
        }
        recBtn.setEnabled(false);
        stopBtn.setEnabled(true);
        backBtn.setEnabled(false);
        saveBtn.setEnabled(false);
        loadPlayBtn.setEnabled(false);

    }

    private void stopRecord() {
        try {
            macroRecorder.close();
        } catch (NativeHookException | IOException e) {
            throw new RuntimeException(e);
        }
        recBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        backBtn.setEnabled(true);
        saveBtn.setEnabled(true);
        loadPlayBtn.setEnabled(true);
    }

    private void loadFile() {
        JFileChooser jfc = new JFileChooser();
        int userChoice = jfc.showOpenDialog(this.getParent());
        if(userChoice != JFileChooser.APPROVE_OPTION) return;

        loadedFile = jfc.getSelectedFile();
        if(!loadedFile.getAbsolutePath().endsWith(".micromacro")) {
            JOptionPane.showMessageDialog(this.getParent(), "Please ensure your file has the .micromacro extension.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        loadPlayBtn.setText("Play File");
    }

    private void playFile() throws IOException {
        backBtn.setEnabled(false);
        loadPlayBtn.setEnabled(false);
        recBtn.setEnabled(false);
        saveBtn.setEnabled(false);

        stopBtn.setEnabled(true);
        stopBtn.setText("Stop Playback");
        stopBtn.addActionListener(this);
        stopBtn.setFocusable(true);


        try {
            macroPlayer = new MacroPlayer(loadedFile);
        } catch (AWTException e) {
            JOptionPane.showMessageDialog(this.getParent(), "Something went wrong!!!!!! aaaaaaa", "help me", JOptionPane.ERROR_MESSAGE);
        }
        int loopPlaybackChoice = JOptionPane.showConfirmDialog(this.getParent(), "Loop macro playback?", "Note!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(loopPlaybackChoice == 2 || loopPlaybackChoice == -1) {
            return;
        }
        if(loopPlaybackChoice == 0) loopPlayback = true;

        if(loopPlayback) while(loopPlayback) macroPlayer.playFile();
        else macroPlayer.playFile();


        backBtn.setEnabled(true);
        loadPlayBtn.setEnabled(true);
        recBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        stopBtn.setText("Stop Recording");
        saveBtn.setEnabled(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
