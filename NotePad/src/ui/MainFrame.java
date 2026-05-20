package ui;
import javax.swing.*;
import java.awt.*;
public class MainFrame extends JFrame {
    private EditorPanel editorPanel;
    private MenuBar menuBar;

    public MainFrame(){
        setTitle("NotePad");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        editorPanel = new EditorPanel();
        menuBar = new MenuBar(editorPanel);

        setJMenuBar(menuBar);
        add(editorPanel, BorderLayout.CENTER);

        setVisible(true);

    }
}
