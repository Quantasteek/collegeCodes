import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[] buttons = new JButton[9];
    private boolean xTurn = true;
    private JLabel statusLabel, scoreLabel;
    private int xScore = 0, oScore = 0;
    private BoardPanel boardPanel;
    private int[] winLine = null; // stores winning positions for drawing

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        statusLabel = new JLabel("Turn: X", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(statusLabel, BorderLayout.NORTH);

        boardPanel = new BoardPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.WHITE);

        Font buttonFont = new Font("Arial", Font.BOLD, 60);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(buttonFont);
            buttons[i].setFocusPainted(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].addActionListener(this);
            boardPanel.add(buttons[i]);
        }

        add(boardPanel, BorderLayout.CENTER);

        scoreLabel = new JLabel("Score - X: 0 | O: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(scoreLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (!clicked.getText().equals("")) return;

        clicked.setText(xTurn ? "X" : "O");
        statusLabel.setText("Turn: " + (xTurn ? "O" : "X"));

        if (checkWin()) {
            String winner = xTurn ? "X" : "O";
            if (xTurn) xScore++; else oScore++;
            scoreLabel.setText("Score - X: " + xScore + " | O: " + oScore);

            statusLabel.setText("Winner: " + winner);
            boardPanel.repaint(); // draw line

            Timer timer = new Timer(1500, evt -> resetGame());
            timer.setRepeats(false);
            timer.start();
            return;
        }

        if (checkDraw()) {
            statusLabel.setText("It's a draw!");
            Timer timer = new Timer(1500, evt -> resetGame());
            timer.setRepeats(false);
            timer.start();
            return;
        }

        xTurn = !xTurn;
    }

    private boolean checkWin() {
        String[] b = new String[9];
        for (int i = 0; i < 9; i++) b[i] = buttons[i].getText();
        String p = xTurn ? "X" : "O";

        int[][] winPatterns = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // cols
            {0, 4, 8}, {2, 4, 6}             // diagonals
        };

        for (int[] pattern : winPatterns) {
            if (p.equals(b[pattern[0]]) && p.equals(b[pattern[1]]) && p.equals(b[pattern[2]])) {
                winLine = pattern;
                return true;
            }
        }

        return false;
    }

    private boolean checkDraw() {
        for (JButton btn : buttons)
            if (btn.getText().equals("")) return false;
        return true;
    }

    private void resetGame() {
        for (JButton btn : buttons)
            btn.setText("");
        xTurn = true;
        winLine = null;
        boardPanel.repaint();
        statusLabel.setText("Turn: X");
    }

    // Custom panel to draw win line
    class BoardPanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (winLine != null) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(5));
                g2.setColor(Color.RED);

                Rectangle r1 = buttons[winLine[0]].getBounds();
                Rectangle r2 = buttons[winLine[2]].getBounds();

                int x1 = r1.x + r1.width / 2;
                int y1 = r1.y + r1.height / 2;
                int x2 = r2.x + r2.width / 2;
                int y2 = r2.y + r2.height / 2;

                g2.drawLine(x1, y1, x2, y2);
            }
        }
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
