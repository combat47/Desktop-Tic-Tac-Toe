package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;
import javax.swing.Timer;

public class TicTacToe extends JFrame {

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(450, 450);
        initComponents();
        setVisible(true);
        setLocationRelativeTo(null);
        //setLayout(null); // sets absolute positioning of components
    }

    public static boolean isEmpty(String[][] matrix) {
        for (String[] array : matrix
        ) {
            for (String variable : array
            ) {
                if (Objects.equals(variable, " ")) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean Tester(String[][] matrix, String var) {
        return Objects.equals(matrix[0][0], var) && Objects.equals(matrix[0][1], var) && Objects.equals(matrix[0][2], var) ||
                Objects.equals(matrix[1][0], var) && Objects.equals(matrix[1][1], var) && Objects.equals(matrix[1][2], var)
                || Objects.equals(matrix[2][0], var) && Objects.equals(matrix[2][1], var) && Objects.equals(matrix[2][2], var)
                || Objects.equals(matrix[0][0], var) && Objects.equals(matrix[1][0], var) && Objects.equals(matrix[2][0], var)
                || Objects.equals(matrix[0][1], var) && Objects.equals(matrix[1][1], var) && Objects.equals(matrix[2][1], var)
                || Objects.equals(matrix[0][2], var) && Objects.equals(matrix[1][2], var) && Objects.equals(matrix[2][2], var)
                || Objects.equals(matrix[0][0], var) && Objects.equals(matrix[1][1], var) && Objects.equals(matrix[2][2], var)
                || Objects.equals(matrix[0][2], var) && Objects.equals(matrix[1][1], var) && Objects.equals(matrix[2][0], var);
    }
    public static int[] decider(List<JButton> array) {
        int[] list = new int[2];
        int counterX = 0;
        int counterO = 0;
        for (JButton s : array
        ) {
            if (s.getText().equals("X")) {
                counterX++;
            } else if (s.getText().equals("O")) {
                counterO++;
            }
        }
        list[0] = counterX;
        list[1] = counterO;
        return list;
    }
    public static void ArrayUpdater(List<JButton> array, int line, int column, String var) {
        array.get(column + line * 3).setText(var);
    }
    public static void SelectorOfPlayingModes(JButton ButtonPlayer1, JButton ButtonPlayer2, List<JButton> array, JLabel LabelStatus) {
        if (ButtonPlayer1.getText().equals("Robot") && ButtonPlayer2.getText().equals("Robot")) {
            int delay=1000;// wait for second
            Timer timer = new Timer(delay, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ae) {

                    if (decider(array)[0] <= decider(array)[1] && isEmpty(ArrayToMatrixConverter(array))) {
                        Move move = FinderBestMove(ArrayToMatrixConverter(array), "X", "O");
                        ArrayUpdater(array, move.row, move.column, "X");
                        LabelStatus.setText(StatusProvider(array, ButtonPlayer1, ButtonPlayer2));
                    } else if (decider(array)[0] > decider(array)[1] && isEmpty(ArrayToMatrixConverter(array))) {
                        Move move = FinderBestMove(ArrayToMatrixConverter(array), "O", "X");
                        ArrayUpdater(array, move.row, move.column, "O");
                        LabelStatus.setText(StatusProvider(array, ButtonPlayer1, ButtonPlayer2));
                    }
                }
            });
            // timer.setRepeats(false);
            timer.start();

        } else if (ButtonPlayer1.getText().equals("Robot") && ButtonPlayer2.getText().equals("Human")) {
            Move move = FinderBestMove(ArrayToMatrixConverter(array), "X", "O");
            ArrayUpdater(array, move.row, move.column, "X");
            LabelStatus.setText(StatusProvider(array, ButtonPlayer1, ButtonPlayer2));
            ButtonsActiver(array, LabelStatus, "O", ButtonPlayer1, ButtonPlayer2);

        } else if (ButtonPlayer1.getText().equals("Human") && ButtonPlayer2.getText().equals("Robot")) {
            ButtonsActiver(array, LabelStatus, "X", ButtonPlayer1, ButtonPlayer2);
        } else {
            ButtonsActiver(array, LabelStatus, ButtonPlayer1, ButtonPlayer2);
        }
    }

    public static void ButtonsActiver(List<JButton> array, JLabel LabelStatus, String var, JButton ButtonPlayer1, JButton ButtonPlayer2) {
        for (JButton button : array
        ) {
            button.addActionListener(e -> {
                button.setText(var);
                LabelStatus.setText(StatusProvider(array, ButtonPlayer1, ButtonPlayer2));

                int delay=1000;// wait for second
                Timer timer;// wait for second
                if (var.equals("O")) {
                    timer = new Timer(delay, new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            Move move = FinderBestMove(ArrayToMatrixConverter(array), "X", "O");
                            ArrayUpdater(array, move.row, move.column, "X");

                        }
                    });

                } else {
                    timer = new Timer(delay, new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            Move move = FinderBestMove(ArrayToMatrixConverter(array), "O", "X");
                            ArrayUpdater(array, move.row, move.column, "O");
                            LabelStatus.setText(StatusProvider(array, ButtonPlayer1, ButtonPlayer2));
                        }
                    });
                }

                timer.setRepeats(false);
                timer.start();

            });
        }


    }
    public static void ButtonsActiver(List<JButton> arrayOfButtons, JLabel LabelStatus, JButton ButtonPlayer1, JButton ButtonPlayer2) {
        for (JButton button : arrayOfButtons
        ) {
            button.addActionListener(e -> {
                if (button.getText().equals(" ")) {
                    XOrO(button, arrayOfButtons);
                    LabelStatus.setText(StatusProvider(arrayOfButtons, ButtonPlayer1, ButtonPlayer2));
                }});

        }
    }
    public static String[][] ArrayToMatrixConverter(List<JButton> array) {
        String[][] matrix = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = array.get(j + i * 3).getText();
            }
        }
        return matrix;
    }

    public static String StatusProvider(List<JButton> array,
                                        JButton ButtonPlayer1, JButton ButtonPlayer2) {
        String s;
        String[][] matrix = ArrayToMatrixConverter(array);
        if (Tester(matrix, "O")) {
            s = MessageFormat.format(
                    "The {0} Player ({1}) wins",
                    ButtonPlayer2.getText(), "O");
            for (JButton button : array
            ) {
                button.setEnabled(false);
            }
            ButtonPlayer1.setEnabled(true);
            ButtonPlayer2.setEnabled(true);
        } else if (Tester(matrix, "X")) {
            s = MessageFormat.format(
                    "The {0} Player ({1}) wins",
                    ButtonPlayer1.getText(), "X");
            for (JButton button : array
            ) {
                button.setEnabled(false);
            }
            ButtonPlayer1.setEnabled(true);
            ButtonPlayer2.setEnabled(true);
        } else {
            if (!isEmpty(matrix)) {
                s = "Draw";
                ButtonPlayer1.setEnabled(true);
                ButtonPlayer2.setEnabled(true);
            } else {
                if (decider(array)[0] <= decider(array)[1]) {
                    s = MessageFormat.format(
                            "The turn of {0} Player ({1})",
                            ButtonPlayer1.getText(), "X");
                } else {
                    s = MessageFormat.format(
                            "The turn of {0} Player ({1})",
                            ButtonPlayer2.getText(), "O");
                }

            }
        }
        return s;
    }
    public static void XOrO(JButton button, List<JButton> array) {
        int counterX = 0;
        int counterO = 0;
        for (JButton s : array
        ) {
            if (s.getText().equals("X")) {
                counterX++;
            } else if (s.getText().equals("O")) {
                counterO++;
            }
        }
        if (counterX <= counterO) {
            button.setText("X");
        } else {
            button.setText("O");
        }
    }
    /**
     * minimax algorithm
     *
     */
    static class Move {
        int column, row;
    }
    public static Move FinderBestMove (String[][] matrix, String player, String enemy) {
        int bestVal = -10;
        Move move = new Move();
        move.column = 0;
        move.row = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j].equals(" ")) {
                    matrix[i][j] = player;
                    int val = Math.max(minimax(matrix, player, enemy, false), bestVal);
                    matrix[i][j] = " ";
                    if (val > bestVal) {
                        bestVal = val;
                        move.row = i;
                        move.column = j;
                    }
                }
            }
        }
        return move;
    }
    public static int minimax(String[][] matrix, String player, String enemy, boolean max) {
        if (Tester(matrix, player)) {
            return 10;
        } else if (Tester(matrix, enemy)) {
            return -10;
        } else if (!isEmpty(matrix)) {
            return 0;
        }
        int best;
        if (max) {
            best = -10;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (matrix[i][j].equals(" ")) {
                        matrix[i][j] = player;
                        best = Math.max(minimax(matrix, player, enemy, false), best);
                        matrix[i][j] = " ";
                    }
                }
            }
        } else {
            best = 10;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (matrix[i][j].equals(" ")) {
                        matrix[i][j] = enemy;
                        best = Math.min(minimax(matrix, player, enemy, true), best);
                        matrix[i][j] = " ";
                    }
                }
            }
        }
        return best;

    }
    public void initComponents() {

        JPanel panel = new JPanel(new GridLayout(3, 3, 5, 5));

        JPanel panel2 = new JPanel(new BorderLayout());

        JPanel panel3 = new JPanel(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu MenuGame = new JMenu("Game");
        MenuGame.setName("MenuGame");
        MenuGame.setMnemonic(KeyEvent.VK_F);// to pop up the file menu: appears, use the key: Alt + f

        JMenuItem MenuHumanHuman = new JMenuItem("Human vs Human");
        MenuHumanHuman.setName("MenuHumanHuman");
        JMenuItem MenuHumanRobot = new JMenuItem("Human vs Robot");
        MenuHumanRobot.setName("MenuHumanRobot");
        JMenuItem MenuRobotHuman = new JMenuItem("Robot vs Human");
        MenuRobotHuman.setName("MenuRobotHuman");
        JMenuItem MenuRobotRobot = new JMenuItem("Robot vs Robot");
        MenuRobotRobot.setName("MenuRobotRobot");
        JMenuItem MenuExit = new JMenuItem("Exit");
        MenuExit.setName("MenuExit");
        // you can rewrite it with a lambda if you prefer this
        MenuExit.addActionListener(event -> System.exit(0));

        MenuGame.add(MenuHumanHuman);
        MenuGame.add(MenuHumanRobot);
        MenuGame.add(MenuRobotHuman);
        MenuGame.add(MenuRobotRobot);

        MenuGame.addSeparator();//draw a separator line
        MenuGame.add(MenuExit);

        menuBar.add(MenuGame);
        setJMenuBar(menuBar); // add menu to the window


        JLabel LabelStatus = new JLabel();
        LabelStatus.setName("LabelStatus");
        LabelStatus.setText("Game is not started");
        panel2.add(LabelStatus, BorderLayout.WEST);

        JButton ButtonPlayer1 = new JButton();
        ButtonPlayer1.setText("Human");
        ButtonPlayer1.setName("ButtonPlayer1");
        ButtonPlayer1.setFocusPainted(false);
        panel3.add(ButtonPlayer1, BorderLayout.WEST);

        JButton ButtonStartReset = new JButton();
        ButtonStartReset.setText("Start");
        ButtonStartReset.setName("ButtonStartReset");
        ButtonStartReset.setFocusPainted(false);
        panel3.add(ButtonStartReset, BorderLayout.CENTER);


        JButton ButtonPlayer2 = new JButton();
        ButtonPlayer2.setText("Human");
        ButtonPlayer2.setName("ButtonPlayer2");
        ButtonPlayer2.setFocusPainted(false);
        panel3.add(ButtonPlayer2, BorderLayout.EAST);


        this.getContentPane().add(panel3, BorderLayout.NORTH);
        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.getContentPane().add(panel2, BorderLayout.SOUTH);

        JButton ButtonA3 = new JButton(" ");
        ButtonA3.setFont(ButtonA3. getFont(). deriveFont(40f));
        ButtonA3.setName("ButtonA3");
        ButtonA3.setBackground(Color.orange);
        ButtonA3.setFocusPainted(false);
        panel.add(ButtonA3);

        JButton ButtonB3 = new JButton(" ");
        ButtonB3.setFont(ButtonB3. getFont(). deriveFont(40f));
        ButtonB3.setName("ButtonB3");
        ButtonB3.setBackground(Color.orange);
        ButtonB3.setFocusPainted(false);
        panel.add(ButtonB3);

        JButton ButtonC3 = new JButton(" ");
        ButtonC3.setFont(ButtonC3. getFont(). deriveFont(40f));
        ButtonC3.setName("ButtonC3");
        ButtonC3.setBackground(Color.orange);
        ButtonC3.setFocusPainted(false);
        panel.add(ButtonC3);

        JButton ButtonA2 = new JButton(" ");
        ButtonA2.setFont(ButtonA2. getFont(). deriveFont(40f));
        ButtonA2.setName("ButtonA2");
        ButtonA2.setBackground(Color.orange);
        ButtonA2.setFocusPainted(false);
        panel.add(ButtonA2);

        JButton ButtonB2 = new JButton(" ");
        ButtonB2.setFont(ButtonB2. getFont(). deriveFont(40f));
        ButtonB2.setName("ButtonB2");
        ButtonB2.setBackground(Color.orange);
        ButtonB2.setFocusPainted(false);
        panel.add(ButtonB2);

        JButton ButtonC2 = new JButton(" ");
        ButtonC2.setFont(ButtonC2. getFont(). deriveFont(40f));
        ButtonC2.setName("ButtonC2");
        ButtonC2.setBackground(Color.orange);
        ButtonC2.setFocusPainted(false);
        panel.add(ButtonC2);

        JButton ButtonA1 = new JButton(" ");
        ButtonA1.setFont(ButtonA1. getFont(). deriveFont(40f));
        ButtonA1.setName("ButtonA1");
        ButtonA1.setBackground(Color.orange);
        ButtonA1.setFocusPainted(false);
        panel.add(ButtonA1);
        ButtonA1.setOpaque(true);

        JButton ButtonB1 = new JButton(" ");
        ButtonB1.setFont(ButtonB1. getFont(). deriveFont(40f));
        ButtonB1.setName("ButtonB1");
        ButtonB1.setBackground(Color.orange);
        ButtonB1.setFocusPainted(false);
        panel.add(ButtonB1);


        JButton ButtonC1 = new JButton(" ");
        ButtonC1.setFont(ButtonC1. getFont(). deriveFont(40f));
        ButtonC1.setName("ButtonC1");
        ButtonC1.setBackground(Color.orange);
        ButtonC1.setFocusPainted(false);
        panel.add(ButtonC1);

        List<JButton> arrayOfButtons = List.of(ButtonA1, ButtonA2, ButtonA3, ButtonB1, ButtonB2, ButtonB3, ButtonC1,
                ButtonC2, ButtonC3);
        List<JMenuItem> menuItems = List.of(MenuRobotHuman, MenuRobotRobot, MenuHumanRobot, MenuHumanHuman);
        for (JMenuItem menuItem : menuItems
        ) {
            menuItem.addActionListener(e -> {
                ButtonPlayer1.setText(Arrays.stream(menuItem.getText().split("\\s+")).toList().get(0));
                ButtonPlayer2.setText(Arrays.stream(menuItem.getText().split("\\s+")).toList().get(2));
                ButtonPlayer1.setEnabled(false);
                ButtonPlayer2.setEnabled(false);
                for (JButton button : arrayOfButtons
                ) {
                    button.setText(" ");
                    button.setEnabled(true);
                }
                ButtonStartReset.setText("Reset");
                LabelStatus.setText(MessageFormat.format(
                        "The turn of {0} Player ({1})",
                        ButtonPlayer1.getText(), "X"));

                SelectorOfPlayingModes(ButtonPlayer1, ButtonPlayer2, arrayOfButtons, LabelStatus);
            });
        }
        ButtonPlayer1.addActionListener(e -> {
            ButtonPlayer1.setText(ButtonPlayer1.getText().equals("Human") ? "Robot" : "Human");
            for (JButton button : arrayOfButtons
            ) {
                button.setText(" ");
                button.setEnabled(true);
            }
            LabelStatus.setText("Game is not started");
        });

        ButtonPlayer2.addActionListener(e -> {
            ButtonPlayer2.setText(ButtonPlayer2.getText().equals("Human") ? "Robot" : "Human");
            for (JButton button : arrayOfButtons
            ) {
                button.setText(" ");
                button.setEnabled(true);
            }
            LabelStatus.setText("Game is not started");
        });
        for (JButton button : arrayOfButtons
        ) {
            button.setEnabled(false);
        }
        ButtonStartReset.addActionListener(e -> {
            ButtonStartReset.setText(ButtonStartReset.getText().equals("Start") ? "Reset" : "Start");
            if (ButtonStartReset.getText().equals("Start")) {
                ButtonPlayer2.setText("Human");
                ButtonPlayer1.setText("Human");
                for (JButton button : arrayOfButtons
                ) {
                    button.setText(" ");
                    button.setEnabled(true);
                }
                ButtonPlayer1.setEnabled(true);
                ButtonPlayer2.setEnabled(true);
                LabelStatus.setText("Game is not started");
            } else {
                for (JButton button : arrayOfButtons
                ) {
                    button.setText(" ");
                    button.setEnabled(true);
                }
                ButtonPlayer1.setEnabled(false);
                ButtonPlayer2.setEnabled(false);
                LabelStatus.setText(MessageFormat.format(
                        "The turn of {0} Player ({1})",
                        ButtonPlayer1.getText(), "X"));

                SelectorOfPlayingModes(ButtonPlayer1, ButtonPlayer2, arrayOfButtons, LabelStatus);
            }

        });
    }

}
