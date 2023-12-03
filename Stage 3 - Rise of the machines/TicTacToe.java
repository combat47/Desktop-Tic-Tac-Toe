package tictactoe;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TicTacToe extends JFrame {
    int count = 0;

    final String X = "X";
    final String O = "O";
    final String EMPTY = " ";
    final String GAME_IN_PROGRESS = "Game in progress";
    final String GAME_NOT_STARTED = "Game is not started";

    XOButton a1 = new XOButton("A", "1");
    XOButton a2 = new XOButton("A", "2");
    XOButton a3 = new XOButton("A", "3");
    XOButton b1 = new XOButton("B", "1");
    XOButton b2 = new XOButton("B", "2");
    XOButton b3 = new XOButton("B", "3");
    XOButton c1 = new XOButton("C", "1");
    XOButton c2 = new XOButton("C", "2");
    XOButton c3 = new XOButton("C", "3");
    XOButton[] XOButtons = {a1, a2, a3, b1, b2, b3, c1, c2, c3};
    JButton startResetButton = new JButton();
    JButton player1Button = new JButton("Human");
    JButton player2Button = new JButton("Human");
    JLabel labelStatus = new JLabel(GAME_NOT_STARTED);
    JPanel optionButtons = new JPanel(new GridLayout(1, 3));
    JPanel gameButtons = new JPanel();
    JPanel statusBar = new JPanel(new GridLayout(0, 1, 50, 50));


    public TicTacToe() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        gameButtons.setLayout(new GridLayout(3, 3));
        gameButtons.setSize(getWidth(), getHeight() - 50);
        statusBar.setSize(getWidth(), getHeight() / 8);
        statusBar.setMinimumSize(new Dimension(getWidth(), 25));
        statusBar.add(labelStatus);
        statusBar.setVisible(true);

        a1.setName("ButtonA1");
        a2.setName("ButtonA2");
        a3.setName("ButtonA3");
        b1.setName("ButtonB1");
        b2.setName("ButtonB2");
        b3.setName("ButtonB3");
        c1.setName("ButtonC1");
        c2.setName("ButtonC2");
        c3.setName("ButtonC3");

        a1.setBackground(Color.orange);
        a2.setBackground(Color.orange);
        a3.setBackground(Color.orange);
        b1.setBackground(Color.orange);
        b2.setBackground(Color.orange);
        b3.setBackground(Color.orange);
        c1.setBackground(Color.orange);
        c2.setBackground(Color.orange);
        c3.setBackground(Color.orange);

        a1.setBorder(border());
        a2.setBorder(border());
        a3.setBorder(border());
        b1.setBorder(border());
        b2.setBorder(border());
        b3.setBorder(border());
        c1.setBorder(border());
        c2.setBorder(border());
        c3.setBorder(border());

        startResetButton.setName("ButtonStartReset");
        startResetButton.setText("Start");
        startResetButton.setFocusable(false);
        player1Button.setName("ButtonPlayer1");
        player1Button.setFocusable(false);
        player1Button.addActionListener(selectHumanCpuPLayer());
        player2Button.addActionListener(selectHumanCpuPLayer());
        player2Button.setName("ButtonPlayer2");
        player2Button.setFocusable(false);
        labelStatus.setName("LabelStatus");
        labelStatus.setText(GAME_NOT_STARTED);

        optionButtons.add(player1Button);
        optionButtons.add(startResetButton);
        optionButtons.add(player2Button);

        gameButtons.add(a3);
        gameButtons.add(b3);
        gameButtons.add(c3);
        gameButtons.add(a2);
        gameButtons.add(b2);
        gameButtons.add(c2);
        gameButtons.add(a1);
        gameButtons.add(b1);
        gameButtons.add(c1);

        add(optionButtons, BorderLayout.NORTH);
        add(gameButtons, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        setName("TicTacToe");
        setTitle("Tic Tac Toe");
        setVisible(true);
        resetGame();
        startResetButton.addActionListener(actionEvent -> {
            if (labelStatus.getText().equals(GAME_NOT_STARTED)) {
                labelStatus.setText(GAME_IN_PROGRESS);
                startGame();
            } else {
                resetGame();
            }
        });
    }

    public class XOButton extends JButton {
        String column;
        String row;

        XOButton(String column, String row) {
            this.column = column;
            this.row = row;
            setText(EMPTY);
            addActionListener(setupXOButtonListener());
            setFont(new Font("Arial", Font.BOLD, 60));
            setFocusable(false);
            setFocusPainted(false);
        }

        @Override
        public String toString() {
            return getText();
        }
    }

    private void playGame() {
        boolean xTurn = count % 2 == 0;
        if (player1Button.getText().equals("Robot") && xTurn) {
            cpuPlay();
        } else if (player2Button.getText().equals("Robot") && !xTurn) {
            cpuPlay();
        }

    }

    public ActionListener setupXOButtonListener() {
        return actionEvent -> {
            JButton xo = (JButton) actionEvent.getSource();
            if (xo.getText().matches("[XO]")) {
                return;
            }
            if (count % 2 == 0) {
                xo.setText(X);
            } else {
                xo.setText(O);
            }
            count++;
            labelStatus.setText(checkGameProgress());
            if (labelStatus.getText().equals(GAME_IN_PROGRESS)) {
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                playGame();
            }
        };
    }

    private ActionListener selectHumanCpuPLayer() {
        return actionEvent -> {
            JButton humanCpu = (JButton) actionEvent.getSource();
            if (humanCpu.getText().equals("Human")) {
                humanCpu.setText("Robot");
            } else {
                humanCpu.setText("Human");
            }
        };
    }

    private String checkGameProgress() {
        String winner = checkRowColumnWin();
        if (!EMPTY.equals(winner)) {
            disableXOButtons();
            return String.format("%s wins", winner);
        }
        winner = checkDiagonalWin();
        if (!EMPTY.equals(winner)) {
            disableXOButtons();
            return String.format("%s wins", winner);
        }
        if (count == 9) {
            disableXOButtons();
            return "Draw";
        }
        return GAME_IN_PROGRESS;
    }


    private String checkRowColumnWin() {

        for (char row = '1'; row <= '3'; row++) {
            char finalRow = row;
            if (Arrays.stream(XOButtons).filter(button -> button.row.equals(String.valueOf(finalRow)))
                    .allMatch(button -> button.toString().matches(X))) {
                return X;
            } else if (Arrays.stream(XOButtons).filter(button -> button.row.equals(String.valueOf(finalRow)))
                    .allMatch(button -> button.toString().matches(O))) {
                return O;
            }
        }

        for (char column = 'A'; column <= 'C'; column++) {
            char finalColumn = column;
            if (Arrays.stream(XOButtons).filter(button -> button.column.equals(String.valueOf(finalColumn)))
                    .allMatch(button -> button.toString().matches(X))) {
                return X;
            } else if (Arrays.stream(XOButtons).filter(button -> button.column.equals(String.valueOf(finalColumn)))
                    .allMatch(button -> button.toString().matches(O))) {
                return O;
            }
        }
        return EMPTY;
    }


    private String checkDiagonalWin() {
        if ((!EMPTY.equals(b2.toString()) &&
                a1.toString().equals(b2.toString()) &&
                c3.toString().equals(b2.toString())) ||
                (!EMPTY.equals(b2.toString()) &&
                        a3.toString().equals(b2.toString()) &&
                        c1.toString().equals(b2.toString()))) {
            return b2.toString();
        }
        return EMPTY;
    }

    private void disableXOButtons() {
        for (XOButton xoButton : XOButtons) {
            xoButton.setEnabled(false);
        }
    }

    private void enableXOButtons() {
        for (XOButton xoButton : XOButtons) {
            xoButton.setEnabled(true);
        }
    }

    private void cpuPlay() {
        Random random = new Random();
        while (true) {
            int randomSpot = random.nextInt(8);
            if (XOButtons[randomSpot].getText().equals(EMPTY)) {
                XOButtons[randomSpot].doClick();
                break;
            }
        }
    }

    private void startGame() {
        enableXOButtons();
        startResetButton.setText("Reset");
        player1Button.setEnabled(false);
        player2Button.setEnabled(false);
        labelStatus.setText(GAME_IN_PROGRESS);
        playGame();
    }

    private void resetGame() {
        disableXOButtons();
        for (JButton xoButton : XOButtons) {
            xoButton.setText(EMPTY);
        }
        player1Button.setEnabled(true);
        player2Button.setEnabled(true);
        startResetButton.setText("Start");
        labelStatus.setText(GAME_NOT_STARTED);
        count = 0;
    }

    private Border border() {
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        return new CompoundBorder(line, margin);
    }

}
