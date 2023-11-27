import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFour extends JFrame {

    private boolean playerOne = true;
    private final JButton[][] buttons = new JButton[6][7];
    private final int[][] boardArray = new int[6][7];

    public ConnectFour() {

        super("Desktop Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);


        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                buttons[i][j] = createButton(j, i);
                add(buttons[i][j]);
            }
        }

        JButton resetButton = new JButton("Reset");
        resetButton.setName("ButtonReset");
        resetButton.addActionListener(new ResetButtonClickListener());
        add(resetButton);


        setLayout(new GridLayout(7, 7, 0, 0));
        setVisible(true);
    }

    private JButton createButton(int col, int row) {
        JButton button = new JButton();
        button.setName("Button" + (char) ('A' + col) + (6 - row));
        button.setText(" ");
        button.setBackground(Color.GRAY);
        button.addActionListener(new ButtonClickListener(col, row));
        return button;
    }

    private class ButtonClickListener implements ActionListener {
        private final int col;

        public ButtonClickListener(int col, int row) {
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int targetRow = findEmptyRow(col);
            if (targetRow != -1) {
                if (playerOne) {
                    buttons[targetRow][col].setText("X");
                    boardArray[targetRow][col] = 1;
                } else {
                    buttons[targetRow][col].setText("O");
                    boardArray[targetRow][col] = 2;
                }
                playerOne = !playerOne;


                if (checkForWin(targetRow, col)) {
                    System.out.println("winner");
                    disableAllButtons();
                }
            }
        }
    }

    private class ResetButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            for (int i = 0; i < boardArray.length; i++) {
                for (int j = 0; j < boardArray[i].length; j++) {
                    buttons[i][j].setText(" ");
                    boardArray[i][j] = 0;
                    buttons[i][j].setEnabled(true);
                    buttons[i][j].setBackground(Color.GRAY);
                }
            }
            playerOne = true;
        }
    }

    private int findEmptyRow(int col) {
        for (int row = boardArray.length - 1; row >= 0; row--) {
            if (boardArray[row][col] == 0) {
                return row;
            }
        }
        return -1;
    }

    private boolean checkForWin(int row, int col) {
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {1, 1}, {1, -1}, {-1, 1}};

        for (int[] direction : directions) {
            int count = 1;
            int rowChange = direction[0];
            int colChange = direction[1];

            for (int i = 1; i < 4; i++) {
                int newRow = row + i * rowChange;
                int newCol = col + i * colChange;

                if (isValidPosition(newRow, newCol) && boardArray[newRow][newCol] == boardArray[row][col]) {
                    count++;
                } else {
                    break;
                }
            }

            if (count >= 4) {
                highlightWinningButtons(row, col, rowChange, colChange);
                return true;
            }
        }
        return false;
    }

    private void highlightWinningButtons(int row, int col, int rowChange, int colChange) {
        for (int i = 0; i < 4; i++) {
            int newRow = row + i * rowChange;
            int newCol = col + i * colChange;
            buttons[newRow][newCol].setBackground(Color.GREEN);
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 6 && col >= 0 && col < 7;
    }

    private void disableAllButtons() {
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray[i].length; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }
}