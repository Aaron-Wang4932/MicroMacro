package org.aaronwang.panels;

import com.github.kwhat.jnativehook.NativeHookException;
import org.aaronwang.UI.GradientPanel;
import org.aaronwang.macro.MacroRecorder;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MacroPanel extends GradientPanel implements ActionListener {
    private JButton loadPlayBtn;
    private JButton recBtn;
    private JButton stopBtn;
    private JButton saveBtn;
    private JButton backBtn;
    private JTextArea macroReadout = new JTextArea();
    private JScrollPane readoutScroller;
    private final MacroRecorder macroRecorder; {
        try { macroRecorder = new MacroRecorder(macroReadout); }
        catch (NativeHookException e) { throw new RuntimeException(e); }

    }

    public MacroPanel(ActionListener gui) {
        super(new Color(0x1d1b1e), new Color(0x342d40), GradientPanel.DIAGONAL_FILL);
        this.setPreferredSize(new Dimension(1000, 624));
        this.setLayout(null);

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

        backBtn = new JButton("Back");
        backBtn.setContentAreaFilled(false);
        backBtn.setForeground(new Color(0xcdc2db));
        backBtn.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 24));
        backBtn.setFocusable(false);
        backBtn.addActionListener(gui);

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
    }

    public void record() {
        try {
            macroRecorder.start();
        } catch (NativeHookException | IOException e) {
            throw new RuntimeException(e);
        }
        recBtn.setEnabled(false);
        stopBtn.setEnabled(true);
        backBtn.setEnabled(false);
        macroReadout.setText("");
    }

    public void stopRecord() {
        try {
            macroRecorder.close();
        } catch (NativeHookException | IOException e) {
            throw new RuntimeException(e);
        }
        recBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        backBtn.setEnabled(true);
    }
}
