package ui;
import service.FileService;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.Font;
import javax.swing.text.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class MenuBar extends JMenuBar {
    public MenuBar(EditorPanel editorPanel){
        JMenu fileMenu = new JMenu("file");
        JMenu formatMenu = new JMenu("Format");
        JMenu editMenu = new JMenu("Edit");
        JMenu themeMenu =   new JMenu("Theme");

        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        JMenuItem boldItem = new JMenuItem("Bold");
        JMenuItem italicItem = new JMenuItem("Italic");
        JMenuItem underlineItem = new JMenuItem("Underline");
        JMenu fontMenu = new JMenu("Font Size");

        Integer[] sizes = {12, 14, 16, 18, 24};
        JComboBox<Integer> fontSizeBox = new JComboBox<>(sizes);

        JMenuItem copyItem = new JMenuItem("Copy");
        JMenuItem pasteItem = new JMenuItem("Paste");
        JMenuItem cutItem = new JMenuItem("Cut");
        JMenuItem selectAllItem = new JMenuItem("Select All");

        JMenuItem darkItem = new JMenuItem("Dark");
        JMenuItem lightItem = new JMenuItem("Light");


        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        formatMenu.add(boldItem);
        formatMenu.add(italicItem);
        formatMenu.add(underlineItem);

        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(cutItem);
        editMenu.add(exitItem);

        themeMenu.add(darkItem);
        themeMenu.add(lightItem);

        fontMenu.add(fontSizeBox);

        add(fileMenu);
        add(formatMenu);
        add(editMenu);
        add(themeMenu);
        add(fontMenu);
        //open
        openItem.addActionListener(e ->{
            String content = FileService.openFile((JFrame) SwingUtilities.getWindowAncestor(this));
            editorPanel.getTextPane().setText(content);
        });
        //save
        saveItem.addActionListener(e->{
            FileService.saveFile((JFrame) SwingUtilities.getWindowAncestor(this),
                    editorPanel.getTextPane().getText());
        });
        //exit
        exitItem.addActionListener(e-> System.exit(0));

        boldItem.addActionListener(e->{
                JTextPane textPane = editorPanel.getTextPane();
                StyledDocument doc = textPane.getStyledDocument();

                int start = textPane.getSelectionStart();
                int end = textPane.getSelectionEnd();

                Style  style = textPane.addStyle("BoldStyle", null);
                StyleConstants.setBold(style, true);
                doc.setCharacterAttributes(start, end - start ,style, false);
    });

        italicItem.addActionListener(e -> {

            JTextPane textPane = editorPanel.getTextPane();

            StyledDocument doc = textPane.getStyledDocument();

            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();

            Style style = textPane.addStyle("ItalicStyle", null);

            StyleConstants.setItalic(style, true);

            doc.setCharacterAttributes(start, end - start, style, false);
        });

        underlineItem.addActionListener(e -> {
            JTextPane textPane = editorPanel.getTextPane();
            StyledDocument doc = textPane.getStyledDocument();

            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();

            Style style = textPane.addStyle("UnderlineStyle", null);
            StyleConstants.setUnderline(style, true);
            doc.setCharacterAttributes(start, end - start, style, false);
        });

        copyItem.addActionListener(e->{
            editorPanel.getTextPane().copy();
        });
        pasteItem.addActionListener(e ->
                editorPanel.getTextPane().paste()
        );
        cutItem.addActionListener(e ->
                editorPanel.getTextPane().cut()
        );
        selectAllItem.addActionListener(e ->
                editorPanel.getTextPane().selectAll()
        );


        darkItem.addActionListener(e -> {
            editorPanel.getTextPane().setBackground(Color.BLACK);
            editorPanel.getTextPane().setForeground(Color.WHITE);
        });

        lightItem.addActionListener(e -> {
            editorPanel.getTextPane().setBackground(Color.WHITE);
            editorPanel.getTextPane().setForeground(Color.BLACK);
        });


        fontSizeBox.addActionListener(e -> {

            Integer selectedSize =
                    (Integer) fontSizeBox.getSelectedItem();

            JTextPane textPane =
                    editorPanel.getTextPane();

            int start = textPane.getSelectionStart();
            int end = textPane.getSelectionEnd();

            // IF TEXT IS SELECTED
            if(start != end){

                StyledDocument doc =
                        textPane.getStyledDocument();

                Style style =
                        textPane.addStyle("FontSizeStyle", null);

                StyleConstants.setFontSize(style, selectedSize);

                doc.setCharacterAttributes(
                        start,
                        end - start,
                        style,
                        false
                );

            } else {

                // NO TEXT SELECTED
                Font currentFont = textPane.getFont();

                textPane.setFont(
                        new Font(
                                currentFont.getFontName(),
                                currentFont.getStyle(),
                                selectedSize
                        )
                );
            }
        });
    }

}
