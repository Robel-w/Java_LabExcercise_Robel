package ui;

import javax.swing.*;
import java.awt.*;

public class EditorPanel extends JPanel {

    private JTextPane textPane;

    public EditorPanel() {

        setLayout(new BorderLayout());

        textPane = new JTextPane();

        textPane.setFont(new Font("Arial", Font.PLAIN, 18));

        JScrollPane scrollPane = new JScrollPane(textPane);

        add(scrollPane, BorderLayout.CENTER);
    }

    public JTextPane getTextPane() {
        return textPane;
    }
}