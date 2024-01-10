package org.aaronwang.panels;

import org.aaronwang.UI.GradientPanel;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class SettingsPanel extends GradientPanel implements ActionListener, FocusListener, KeyListener {
    private ActionListener gui;
    private JButton backButton;
    private JTextField recordDelayField;
    private JTextField keybindField;
    private Border keybindFieldDefaultBorder;
    private JComboBox themeSelector;
    private JToggleButton hintsToggle;
    private ArrayList<Integer> enteredKeybinds = new ArrayList<>(List.of(0)); // Keeps track of keybinds in progress of being entered
    private int[] keybinds = new int[2]; // keeps track of keybind codes
    private String keybindString; // Keeps track of keybinds as a string representation
    private int recordingDelayMS;
    private String theme;
    private boolean showHints;
    public SettingsPanel(ActionListener gui) {
        super(new Color(0x3d137f), new Color(0x342d40), GradientPanel.VERTICAL_FILL);
        this.gui = gui;
        this.setPreferredSize(new Dimension(1000, 624));
        this.setLayout(null);
        readConfig();

        JLabel title = new JLabel("Settings", JLabel.CENTER);
        title.setForeground(new Color(0xcdc2db)); // Secondary
        title.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 48));
        title.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(0x4b4358), 8, true), // Secondary container
                "MicroMacro",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Century Gothic", Font.BOLD | Font.ITALIC, 26),
                new Color(0xe9def8))); // On secondary container

        JLabel delayLabel = new JLabel("<html><u>Recording delay (ms)</u></html>", JLabel.CENTER);
        delayLabel.setForeground(new Color(0xf0b7c5)); // Tertiary
        delayLabel.setBackground(new Color(0x643b46)); // Tertiary container
        delayLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));

        recordDelayField = new JTextField(recordingDelayMS + "");
        recordDelayField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        recordDelayField.setBackground(new Color(0x4b4358)); // Secondary container
        recordDelayField.setForeground(new Color(0xcdc2db)); // Secondary
        recordDelayField.setCaretColor(new Color(0xe9def8)); // On secondary container
        recordDelayField.setHorizontalAlignment(JTextField.RIGHT);

        JLabel keybindLabel = new JLabel("<html><u>Preferred Recording Keybind</u></html>", JLabel.CENTER);
        keybindLabel.setForeground(new Color(0xf0b7c5)); // Tertiary
        keybindLabel.setBackground(new Color(0x643b46)); // Tertiary container
        keybindLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));

        keybindField = new JTextField(keybindString);
        keybindField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        keybindField.setBackground(new Color(0x4b4358)); // Secondary container
        keybindField.setForeground(new Color(0xcdc2db)); // Secondary
        keybindField.setCaretColor(new Color(0xe9def8)); // On secondary container
        keybindFieldDefaultBorder = keybindField.getBorder();
        keybindField.setEditable(false);
        keybindField.setHorizontalAlignment(JTextField.RIGHT);
        keybindField.addFocusListener(this);

        JLabel themeLabel = new JLabel("<html><u>Preferred Theme</u></html>", JLabel.CENTER);
        themeLabel.setForeground(new Color(0xf0b7c5)); // Tertiary
        themeLabel.setBackground(new Color(0x643b46)); // Tertiary container
        themeLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));

        themeSelector = new JComboBox();
        themeSelector.addItem("Themes are not supported yet.");
        themeSelector.setBackground(new Color(0x4b4358)); // Secondary container
        themeSelector.setForeground(new Color(0xcdc2db)); // Secondary
        themeSelector.setFont(new Font("Century Gothic", Font.BOLD, 14));

        JLabel hintsLabel = new JLabel("<html><u>Show Startup Hints</u></html>", JLabel.CENTER);
        hintsLabel.setForeground(new Color(0xf0b7c5)); // Tertiary
        hintsLabel.setBackground(new Color(0x643b46)); // Tertiary container
        hintsLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));

        hintsToggle = new JToggleButton((showHints + "").substring(0, 1).toUpperCase() + (showHints + "").substring(1).toLowerCase(), showHints);
        hintsToggle.setFocusable(false);
        hintsToggle.setFont(new Font("Century Gothic", Font.BOLD, 14));
        hintsToggle.setBackground(new Color(0x4b4358)); // Secondary container
        if(hintsToggle.isSelected()) hintsToggle.setForeground(new Color(0x635b70)); // Secondary (light mode)
        else hintsToggle.setForeground(new Color(0xcdc2db)); // Secondary
        hintsToggle.addActionListener(e -> {
            if(hintsToggle.isSelected()) {
                hintsToggle.setForeground(new Color(0x635b70)); // Secondary (light mode)
                hintsToggle.setText("True");
            } else {
                hintsToggle.setForeground(new Color(0xcdc2db)); // Secondary
                hintsToggle.setText("False");
            }
        });

        backButton = new JButton("Back");
        backButton.setContentAreaFilled(false);
        backButton.setForeground(new Color(0xcdc2db));
        backButton.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 24));
        backButton.setFocusable(false);
        backButton.addActionListener(this);

        this.add(title);
        title.setBounds(370, 20, 260, 110);
        this.add(backButton);
        backButton.setBounds(110, 50, 100, 50);

        this.add(delayLabel);
        delayLabel.setBounds(110, 189, 212, 50);
        this.add(recordDelayField);
        recordDelayField.setBounds(790, 202, 100, 25);

        this.add(keybindLabel);
        keybindLabel.setBounds(110, 298, 282, 50);
        this.add(keybindField);
        keybindField.setBounds(690, 310, 200, 25);

        this.add(themeLabel);
        themeLabel.setBounds(110, 407, 160, 50);
        this.add(themeSelector);
        themeSelector.setBounds(640, 419, 250, 25);

        this.add(hintsLabel);
        hintsLabel.setBounds(110, 516, 177, 50);
        this.add(hintsToggle);
        hintsToggle.setBounds(790, 528, 100, 25);
    }

    private void readConfig() {
        try {
            String temp;
            BufferedReader configReader = new BufferedReader(new FileReader("resources/config.txt"));
            while((temp = configReader.readLine()) != null) {
                if(temp.startsWith("recording-delay: ")) {
                    temp = temp.replace("recording-delay: ", "");
                    recordingDelayMS = Integer.parseInt(temp);
                } else if(temp.startsWith("pref-keybind: ")) {
                    temp = temp.replace("pref-keybind: ", "");
                    keybinds[0] = Integer.parseInt(temp.split(", ")[0]);
                    keybinds[1] = Integer.parseInt(temp.split(", ")[1]);
                    for(int i = 0; i < keybinds.length; i++) {
                        if(i != keybinds.length - 1) keybindString += KeyEvent.getKeyText(keybinds[i]) + " & ";
                        else keybindString += KeyEvent.getKeyText(keybinds[i]);
                    }
                    keybindString = keybindString.replace("null", "");
                } else if(temp.startsWith("theme: ")) {
                    temp = temp.replace("theme: ", "");
                    theme = temp;
                } else if(temp.startsWith("show-startup-hints: ")) {
                    temp = temp.replace("show-startup-hints: ", "");
                    showHints = Boolean.parseBoolean(temp);
                }
            }
            configReader.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog((JFrame) gui, "Please ensure that config.txt exists within the resources directory of the project folder!\nDefault settings will be used.", "Error!", JOptionPane.ERROR_MESSAGE);
            try {
                new File("resources/config.txt").createNewFile();
            } catch (IOException ignored) {}
        } catch (IOException e) {
            JOptionPane.showMessageDialog((JFrame) gui, "Something went wrong! This sucks.", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        keybindField.addKeyListener(this);
        keybindField.setBorder(BorderFactory.createLineBorder(new Color(0xffb4ab), 3));
        keybindField.setToolTipText("Recording your key presses...Press ESC to save/cancel.");
    }

    @Override
    public void focusLost(FocusEvent e) {
        keybindField.removeKeyListener(this);
        keybindField.setBorder(keybindFieldDefaultBorder);
        keybindField.setToolTipText(null);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(KeyEvent.getKeyText(e.getKeyCode()).equals("Escape")) {
            this.requestFocus();
        }
        if(enteredKeybinds.get(enteredKeybinds.size() - 1) != e.getKeyCode()) {
            enteredKeybinds.add(e.getKeyCode());
        }
        if(enteredKeybinds.size() == 3) { // Max keybind size of 2
            this.requestFocus();
            keybinds[0] = enteredKeybinds.get(1);
            keybinds[1] = enteredKeybinds.get(2);
            keybindString = KeyEvent.getKeyText(enteredKeybinds.get(1)) + " & " + KeyEvent.getKeyText(enteredKeybinds.get(2));
            keybindField.setText(keybindString);
            enteredKeybinds.clear();
            enteredKeybinds.add(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        ActionEvent event = new ActionEvent(backButton, 69, "go back");
        boolean changedSaved = JOptionPane.showConfirmDialog((JFrame) gui, "Would you like to save your changes?", "Note: ", JOptionPane.YES_NO_OPTION) == 0;

        if(!changedSaved) return;

        try {
            Integer.parseInt(recordDelayField.getText());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog((JFrame) gui, "Please ensure your specified delay is a number!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String keyConfig  = keybinds[0] + ", " + keybinds[1];
        writeConfig(recordDelayField.getText(), keyConfig, themeSelector.getSelectedItem().toString().toLowerCase().replaceAll(" ", "-"), hintsToggle.getText());

        backButton.removeActionListener(this);
        backButton.addActionListener(gui);
        backButton.getActionListeners()[0].actionPerformed(event);
        backButton.removeActionListener(gui);
        backButton.addActionListener(this);
    }

    private void writeConfig(String rDelay, String prefKeybind, String theme, String showHints) {
        String[] configs = new String[4];
        String temp;
        try{
            BufferedReader configReader = new BufferedReader(new FileReader("resources/config.txt"));
            BufferedWriter configWriter = new BufferedWriter(new FileWriter("resources/config.txt"));
            int i = 0;
            while((temp = configReader.readLine()) != null) {
                configs[i] = temp;
                i++;
            }
            configReader.close();

            configs[0] = "recording-delay: " + rDelay;
            configs[1] = "pref-keybind: " + prefKeybind;
            configs[2] = "theme: " + theme;
            configs[3] = "show-startup-hints: " + showHints;

            for(String s : configs) {
                configWriter.write(s + "\n");
            }
            configWriter.close();
        } catch(IOException ignored){}
    }
}

