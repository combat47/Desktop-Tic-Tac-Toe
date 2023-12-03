package tictactoe;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;


public class TicTacToe extends JFrame {
    JButton buttonA1;
    JButton buttonB1;
    JButton buttonC1;
    JButton buttonA2;
    JButton buttonB2;
    JButton buttonC2;
    JButton buttonA3;
    JButton buttonB3;
    JButton buttonC3;
    JButton resetStartButton;
    JPanel playTable;
    JPanel toolbar;
    JPanel statusBar;
    JLabel gameStatus;
    boolean isEnd = false;
    String currentSymbol = "O";
    public TicTacToe() {
        super("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        createComponents();
        setVisible(true);
    }
    public void createComponents() {
        playTable = new JPanel();
        playTable.setLayout(new GridLayout(3, 3, 10, 10));
        playTable.setBounds(0, 0, 300, 300);
        add(playTable, BorderLayout.CENTER);
        buttonA1 = new JButton(" ");
        buttonA1.setBackground(Color.orange);
        buttonA1.setBorder(border());
        buttonA1.setName("ButtonA1");
        buttonA1.setBounds(0, 0, 100, 100);
        setTableButtonListener(buttonA1);
        buttonB1 = new JButton(" ");
        buttonB1.setBackground(Color.orange);
        buttonB1.setBorder(border());
        buttonB1.setName("ButtonB1");
        buttonB1.setBounds(0, 0, 100, 100);
        setTableButtonListener(buttonB1);
        buttonC1 = new JButton(" ");
        buttonC1.setBackground(Color.orange);
        buttonC1.setBorder(border());
        buttonC1.setName("ButtonC1");
        buttonC1.setBounds(0, 0, 100, 100);
        setTableButtonListener(buttonC1);
        buttonA2 = new JButton(" ");
        buttonA2.setBackground(Color.orange);
        buttonA2.setBorder(border());
        buttonA2.setName("ButtonA2");
        buttonA2.setBounds(0, 0, 100, 100);
        setTableButtonListener(buttonA2);
        buttonB2 = new JButton(" ");
        buttonB2.setBackground(Color.orange);
        buttonB2.setBorder(border());
        buttonB2.setName("ButtonB2");
        buttonB2.setBounds(0, 0, 100, 100);
        setTableButtonListener(buttonB2);
        buttonC2 = new JButton(" ");
        buttonC2.setBackground(Color.orange);
        buttonC2.setBorder(border());
        buttonC2.setName("ButtonC2");
        buttonC2.setBounds(0, 0, 100, 100);
        setTableButtonListener(buttonC2);
        buttonA3 = new JButton(" ");
        buttonA3.setBackground(Color.orange);
        buttonA3.setBorder(border());
        buttonA3.setName("ButtonA3");
        buttonA3.setBounds(0, 0, 100, 100);
        setTableButtonListener(buttonA3);
        buttonB3 = new JButton(" ");
        buttonB3.setBackground(Color.orange);
        buttonB3.setBorder(border());
        buttonB3.setName("ButtonB3");
        buttonB3.setBounds(0, 0, 100, 100);
        setTableButtonListener(buttonB3);
        buttonC3 = new JButton(" ");
        buttonC3.setBackground(Color.orange);
        buttonC3.setBorder(border());
        buttonC3.setName("ButtonC3");
        buttonC3.setBounds(0, 0, 100, 100);
        setTableButtonListener(buttonC3);
        playTable.add(buttonA3);
        playTable.add(buttonB3);
        playTable.add(buttonC3);
        playTable.add(buttonA2);
        playTable.add(buttonB2);
        playTable.add(buttonC2);
        playTable.add(buttonA1);
        playTable.add(buttonB1);
        playTable.add(buttonC1);

        statusBar = new JPanel();
        statusBar.setBounds(0, 0, 300, 100);
        statusBar.setLayout(new BorderLayout());
        gameStatus = new JLabel("Game is not started");
        gameStatus.setName("LabelStatus");
        statusBar.add(gameStatus, BorderLayout.WEST);

        toolbar = new JPanel();
        toolbar.setLayout(new GridLayout(1, 3, 0, 0));
        resetStartButton = new JButton("Reset");
        resetStartButton.setName("ButtonReset");
        resetStartButton.setFocusPainted(false);
        resetStartButton.setBackground(Color.black);
        resetStartButton.setForeground(Color.white);
        setResetListener();
        statusBar.add(resetStartButton, BorderLayout.EAST);
        add(statusBar, BorderLayout.SOUTH);
    }

    private Border border() {
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        return new CompoundBorder(line, margin);
    }

    public void setResetListener() {
        resetStartButton.addActionListener(e -> {
            buttonA1.setText(" ");
            buttonB1.setText(" ");
            buttonC1.setText(" ");
            buttonA2.setText(" ");
            buttonB2.setText(" ");
            buttonC2.setText(" ");
            buttonA3.setText(" ");
            buttonB3.setText(" ");
            buttonC3.setText(" ");
            isEnd = false;
            currentSymbol = "O";
            gameStatus.setText("Game is not started");
        });
    }
    public void setTableButtonListener(JButton button) {
        button.addActionListener(e -> {
            if (!isEnd) {
                if (button.getText().equals(" ")) {
                    if (currentSymbol.equals("X"))
                        currentSymbol = "O";
                    else
                        currentSymbol = "X";
                    button.setText(currentSymbol);
                    System.out.println(button.getText());
                    gameStatus.setText("Game in progress");
                    if (checkEndGame())
                        gameStatus.setText(currentSymbol + " wins");
                    if (checkNotEmpty())
                        gameStatus.setText("Draw");
                }
            }
        });
    }
    public boolean checkEndGame() {
        isEnd = checkRows() || checkColumns() || checkDiagonals();
        return isEnd;
    }
    public boolean checkRows() {
        return checkFirstRow() || checkSecondRow() || checkThirdRow();
    }
    public boolean checkFirstRow() {
        return buttonA1.getText().equals(buttonB1.getText()) && buttonA1.getText().equals(buttonC1.getText()) && !buttonA1.getText().equals(" ");
    }
    public boolean checkSecondRow() {
        return buttonA2.getText().equals(buttonB2.getText()) && buttonA2.getText().equals(buttonC2.getText()) && !buttonA2.getText().equals(" ");
    }
    public boolean checkThirdRow() {
        return buttonA3.getText().equals(buttonB3.getText()) && buttonA3.getText().equals(buttonC3.getText()) && !buttonA3.getText().equals(" ");
    }
    public boolean checkColumns () {
        return checkFirstColumn() || checkSecondColumn() || checkThirdColumn();
    }
    public boolean checkFirstColumn() {
        return buttonA1.getText().equals(buttonA2.getText()) && buttonA1.getText().equals(buttonA3.getText()) && !buttonA1.getText().equals(" ");
    }
    public boolean checkSecondColumn() {
        return buttonB1.getText().equals(buttonB2.getText()) && buttonB1.getText().equals(buttonB3.getText()) && !buttonB1.getText().equals(" ");
    }
    public boolean checkThirdColumn() {
        return buttonC1.getText().equals(buttonC2.getText()) && buttonC1.getText().equals(buttonC3.getText()) && !buttonC1.getText().equals(" ");
    }
    public boolean checkDiagonals () {
        return checkFirstDiagonal() || checkSecondDiagonal();
    }
    public boolean checkFirstDiagonal() {
        return buttonA1.getText().equals(buttonB2.getText()) && buttonA1.getText().equals(buttonC3.getText()) && !buttonA1.getText().equals(" ");
    }
    public boolean checkSecondDiagonal() {
        return buttonA3.getText().equals(buttonB2.getText()) && buttonA3.getText().equals(buttonC1.getText()) && !buttonA3.getText().equals(" ");
    }
    public boolean checkNotEmpty() {
        return !buttonA1.getText().equals(" ") && !buttonB1.getText().equals(" ") && !buttonC1.getText().equals(" ") && !buttonA2.getText().equals(" ") && !buttonB2.getText().equals(" ") && !buttonC2.getText().equals(" ") && !buttonA3.getText().equals(" ") && !buttonB3.getText().equals(" ") && !buttonC3.getText().equals(" ");
    }
}
