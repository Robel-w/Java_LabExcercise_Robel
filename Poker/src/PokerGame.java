//package mypoker;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class PokerGame extends JFrame {

    enum Phase { IDLE, BETTING, DRAW, DONE }

    private Deck deck;
    private Card[] playerHand = new Card[5];
    private Card[] cpuHand = new Card[5];

    private int playerChips = 200, cpuChips = 200, pot = 0, currentBet = 0;
    private Phase phase = Phase.IDLE;

    private final CardButton[] playerButtons = new CardButton[5];
    private final CardButton[] cpuButtons = new CardButton[5];

    private JLabel playerResultLabel, cpuResultLabel, potLabel, playerChipsLabel, cpuChipsLabel;
    private JLabel currentBetLabel, messageLabel;

    private JButton btnDeal, btnDraw, btnFold, btnBet;
    private JPanel betPanel;

    public PokerGame() {
        super("5-Card Draw Poker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBackground(new Color(0, 100, 50));

        root.add(buildTopBar(), BorderLayout.NORTH);
        root.add(buildTable(), BorderLayout.CENTER);
        root.add(buildControls(), BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        updateUI();
    }
    private JPanel buildTopBar() {
        JPanel p = new JPanel(new BorderLayout(10, 0));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(10, 10, 5, 10));

        playerChipsLabel = new JLabel("Your chips: 200");
        cpuChipsLabel = new JLabel("CPU chips: 200");
        potLabel = new JLabel("Pot: 0");

        styleLabel(playerChipsLabel, 14, Color.WHITE);
        styleLabel(cpuChipsLabel, 14, Color.WHITE);
        styleLabel(potLabel, 16, new Color(255, 215, 0));

        potLabel.setHorizontalAlignment(SwingConstants.CENTER);

        p.add(playerChipsLabel, BorderLayout.WEST);
        p.add(potLabel, BorderLayout.CENTER);
        p.add(cpuChipsLabel, BorderLayout.EAST);
        return p;
    }
    private JPanel buildTable() {
        JPanel table = new JPanel(new GridBagLayout());
        table.setBackground(new Color(0, 120, 60));
        table.setBorder(new EmptyBorder(15, 15, 15, 15));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(8, 0, 8, 0);

        c.gridy = 0; table.add(new JLabel("CPU HAND"), c);
        c.gridy = 1; table.add(buildHandPanel(cpuButtons, true), c);
        c.gridy = 2; table.add(cpuResultLabel = new JLabel(""), c);

        c.gridy = 3; table.add(new JSeparator(), c);

        c.gridy = 4; table.add(new JLabel("YOUR HAND (click to swap)"), c);
        c.gridy = 5; table.add(buildHandPanel(playerButtons, false), c);
        c.gridy = 6; table.add(playerResultLabel = new JLabel(""), c);

        styleResultLabel(cpuResultLabel);
        styleResultLabel(playerResultLabel);

        return table;
    }
    private JPanel buildHandPanel(CardButton[] buttons, boolean isCpu) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        p.setOpaque(false);
        for (int i = 0; i < 5; i++) {
            buttons[i] = new CardButton();
            if (isCpu) buttons[i].setEnabled(false);
            p.add(buttons[i]);
        }
        return p;
    }

    private JPanel buildControls() {
        JPanel outer = new JPanel(new BorderLayout(0, 10));
        outer.setOpaque(false);
        outer.setBorder(new EmptyBorder(10, 0, 10, 0));

        betPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        betPanel.setOpaque(false);

        betPanel.add(new JLabel("Bet: "));
        for (int val : new int[]{5, 10, 25, 50}) {
            JButton b = new JButton("+" + val);
            b.setPreferredSize(new Dimension(60, 35));
            int value = val;
            b.addActionListener(e -> {
                if (currentBet + value <= playerChips) {
                    currentBet += value;
                    updateBetLabel();
                }
            });
            betPanel.add(b);
        }

        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> { currentBet = 0; updateBetLabel(); });
        betPanel.add(clear);
        betPanel.add(currentBetLabel = new JLabel("→ 0"));

        outer.add(betPanel, BorderLayout.NORTH);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        actions.setOpaque(false);

        btnDeal = new JButton("Deal New Hand");
        btnBet = new JButton("Place Bet");
        btnDraw = new JButton("Draw Cards");
        btnFold = new JButton("Fold");

        styleButton(btnDeal, new Color(34, 139, 34));
        styleButton(btnBet, new Color(233, 72, 90));
        styleButton(btnDraw, new Color(0, 100, 200));
        styleButton(btnFold, new Color(180, 40, 40));

        btnDeal.addActionListener(e -> startRound());
        btnBet.addActionListener(e -> placeBet());
        btnDraw.addActionListener(e -> drawPhase());
        btnFold.addActionListener(e -> fold());

        actions.add(btnDeal);
        actions.add(btnBet);
        actions.add(btnDraw);
        actions.add(btnFold);

        outer.add(actions, BorderLayout.CENTER);

        messageLabel = new JLabel("Welcome! Click Deal New Hand to start.");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        messageLabel.setForeground(Color.WHITE);
        outer.add(messageLabel, BorderLayout.SOUTH);

        return outer;
    }

    private void styleLabel(JLabel label, int size, Color color) {
        label.setFont(new Font("SansSerif", Font.BOLD, size));
        label.setForeground(color);
    }

    private void styleResultLabel(JLabel label) {
        label.setFont(new Font("SansSerif", Font.BOLD, 15));
        label.setForeground(new Color(255, 215, 0));
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
    }

    private void updateBetLabel() {
        currentBetLabel.setText("→ " + currentBet);
    }

    private void message(String text) {
        messageLabel.setText(text);
    }

    // the logic

    private void startRound() {
        if (playerChips <= 0 || cpuChips <= 0) {
            message("Game Over! Restart the program.");
            return;
        }

        deck = new Deck();
        for (int i = 0; i < 5; i++) {
            playerHand[i] = deck.deal();
            cpuHand[i] = deck.deal();
        }

        pot = 0;
        currentBet = 0;
        phase = Phase.BETTING;

        renderHands(false);
        playerResultLabel.setText(HandEvaluator.evaluate(playerHand).name);
        cpuResultLabel.setText("");

        message("Place your bet then click Place Bet.");
        updateUI();
    }

    private void placeBet() {
        pot += currentBet;
        playerChips -= currentBet;

        int cpuBet = currentBet == 0 ?
                (int)(Math.random() * 15) + 5 :
                Math.min(currentBet, cpuChips);

        pot += cpuBet;
        cpuChips -= cpuBet;

        currentBet = 0;
        phase = Phase.DRAW;

        renderHands(false);
        message("Click cards you want to swap, then click Draw Cards.");
        updateUI();
    }

    private void drawPhase() {
        for (int i = 0; i < 5; i++) {
            if (playerButtons[i].isSelected()) {
                playerHand[i] = deck.deal();
            }
        }
        cpuDraw();

        phase = Phase.DONE;
        renderHands(true);
        HandResult playerResult = HandEvaluator.evaluate(playerHand);
        HandResult cpuResult = HandEvaluator.evaluate(cpuHand);
        playerResultLabel.setText(playerResult.name);
        cpuResultLabel.setText(cpuResult.name);
        int cmp = playerResult.compareTo(cpuResult);

        if (cmp > 0) {
            playerChips += pot;
            message("🎉 You won " + pot + " chips!");
        } else if (cmp < 0) {
            cpuChips += pot;
            message("CPU wins this round.");
        } else {
            int split = pot / 2;
            playerChips += split;
            cpuChips += split;
            message("Tie! Pot split.");
        }

        pot = 0;
        updateUI();
    }
    private void cpuDraw() {
        HandResult result = HandEvaluator.evaluate(cpuHand);
        int swaps = switch (result.tier) {
            case 0 -> 3;
            case 1 -> 2;
            case 2 -> 1;
            case 3 -> 2;
            default -> 0;
        };

        if (swaps > 0) {
            for (int i = 0; i < swaps; i++) {
                cpuHand[i] = deck.deal();
            }
        }
    }
    private void fold() {
        cpuChips += pot;
        pot = 0;
        phase = Phase.DONE;
        renderHands(true);
        message("You folded. Machine takes the pot.");
        updateUI();
    }

    private void renderHands(boolean revealCpu) {
        for (int i = 0; i < 5; i++) {
            playerButtons[i].setCard(playerHand[i], false);
            cpuButtons[i].setCard(cpuHand[i], !revealCpu);
        }
    }

    private void updateUI() {
        playerChipsLabel.setText("Your chips: " + playerChips);
        cpuChipsLabel.setText("CPU chips: " + cpuChips);
        potLabel.setText("Pot: " + pot);
        updateBetLabel();

        boolean betting = phase == Phase.BETTING;
        boolean draw = phase == Phase.DRAW;
        boolean done = phase == Phase.DONE;

        btnDeal.setEnabled(phase == Phase.IDLE || done);
        btnBet.setEnabled(betting);
        btnDraw.setEnabled(draw);
        btnFold.setEnabled(betting || draw);
        betPanel.setVisible(betting);

        for (CardButton b : playerButtons) {
            b.setEnabled(draw);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(PokerGame::new);
    }
}