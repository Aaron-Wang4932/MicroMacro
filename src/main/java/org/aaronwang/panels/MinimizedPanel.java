package org.aaronwang.panels;
import javax.swing.*;
import java.awt.*;

public class MinimizedPanel extends JPanel {
    private JButton play_pause;
    private JButton stopRecording;
    public MinimizedPanel() {
        this.setSize(new Dimension(100, 50));
        this.setLayout(new FlowLayout());

        play_pause = new JButton("Play");
        play_pause.setPreferredSize(new Dimension(50, 50));
        play_pause.setFont(new Font("Century Gothic", Font.PLAIN, 8));
        this.add(play_pause);

        stopRecording = new JButton("Stop");
        stopRecording.setPreferredSize(new Dimension(50, 50));
        stopRecording.setFont(new Font("Century Gothic", Font.PLAIN, 7));
        this.add(stopRecording);

    }
}
