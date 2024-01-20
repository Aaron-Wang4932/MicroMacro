package org.aaronwang;

import org.aaronwang.panels.HomePanel;
import org.aaronwang.panels.MacroPanel;
import org.aaronwang.panels.SettingsPanel;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Frame extends JFrame implements ActionListener {
    JPanel homePanel = new HomePanel(this);
    JPanel macroPanel = new MacroPanel(this);
    JPanel settingsPanel = new SettingsPanel(this);

    public Frame() {
        this.setTitle("MicroMacro");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("resources/title.png").getImage());
        this.add(homePanel);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Show the only implemented startup hint if required.
        String temp;
        boolean showHints;
        try {
            BufferedReader br = new BufferedReader(new FileReader("resources/config.txt"));
            while((temp = br.readLine()) != null) if(temp.startsWith("show-startup-hints: ")) break;
            temp = temp.replace("show-startup-hints: ", "");
            showHints = Boolean.parseBoolean(temp);
            if(showHints) JOptionPane.showMessageDialog(this, "When a macro is running, there is currently no way to stop it without waiting for it to naturally end.", "Note: ", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ignored) {}
    }

    // Set panels.
    @Override
    public void actionPerformed(ActionEvent e) {
        // Main Panel buttons:
        if(e.getSource() == homePanel.getComponent(1)) {
            setPanel(homePanel, macroPanel);
            this.addKeyListener((KeyListener) macroPanel);
            try {
                MacroPanel.setStuff();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if(e.getSource() == homePanel.getComponent(2)) setPanel(homePanel, settingsPanel);
        // Settings Panel buttons:
        else if(e.getSource() == settingsPanel.getComponent(1)) setPanel(settingsPanel, homePanel);
        // Macro Panel buttons:
        else if (e.getSource() == macroPanel.getComponent(5)) {
            this.removeKeyListener((KeyListener) macroPanel);
            setPanel(macroPanel, homePanel);
        }
    }

    public void setPanel(JPanel oldPanel, JPanel newPanel) {
        this.remove(oldPanel);
        this.add(newPanel);
        this.repaint();
        this.pack();
        this.setLocationRelativeTo(null);
    }
}
