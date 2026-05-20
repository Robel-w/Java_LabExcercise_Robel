import javax.swing.*;
import java.awt.*;

public class CardButton extends JButton {
    private Card card;
    private boolean selected = false;
    private boolean hidden = false;

    public CardButton() {
        setPreferredSize(new Dimension(80, 110));
        setFocusPainted(false);
        setFont(new Font("Serif", Font.BOLD, 22));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createRaisedBevelBorder());

        addActionListener(e -> {
            if (!hidden && isEnabled()) {
                selected = !selected;
                repaint();
            }
        });
    }
    public void setCard(Card card, boolean hide) {
        this.card = card;
        this.hidden = hide;
        this.selected = false;
        setEnabled(!hide);
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (hidden || card == null) {
            setBackground(new Color(0, 80, 140));
            setText("");
            return;
        }
        setBackground(selected ? new Color(255, 245, 180) : Color.WHITE);
        setForeground(card.isRed() ? Color.RED : Color.BLACK);
        setText(card.rank() + card.suit());

        if (selected) {
            setFont(new Font("SansSerif", Font.BOLD, 12));
            setText(card.rank() + card.suit() + "\nSWAP");
        } else {
            setFont(new Font("Serif", Font.BOLD, 22));
        }
    }
}