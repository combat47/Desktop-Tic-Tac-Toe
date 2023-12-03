package tictactoe;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TicTacToe extends JFrame {
    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setSize(300, 300);
        table();
        setLayout(new GridLayout(3, 3, 10, 10));
        setVisible(true);

    }

    private void table() {
        JButton a3 = new JButton("A3");
        a3.setBackground(Color.orange);
        a3.setName("ButtonA3");
        a3.setBorder(border());
        add(a3);

        JButton b3 = new JButton("B3");
        b3.setBackground(Color.orange);
        b3.setName("ButtonB3");
        b3.setBorder(border());
        add(b3);

        JButton c3 = new JButton("C3");
        c3.setBackground(Color.orange);
        c3.setName("ButtonC3");
        c3.setBorder(border());
        add(c3);

        JButton a2 = new JButton("A2");
        a2.setBackground(Color.orange);
        a2.setName("ButtonA2");
        a2.setBorder(border());
        add(a2);

        JButton b2 = new JButton("B2");
        b2.setBackground(Color.orange);
        b2.setName("ButtonB2");
        b2.setBorder(border());
        add(b2);

        JButton c2 = new JButton("C2");
        c2.setBackground(Color.orange);
        c2.setName("ButtonC2");
        c2.setBorder(border());
        add(c2);

        JButton a1 = new JButton("A1");
        a1.setBackground(Color.orange);
        a1.setName("ButtonA1");
        a1.setBorder(border());
        add(a1);

        JButton b1 = new JButton("B1");
        b1.setBackground(Color.orange);
        b1.setName("ButtonB1");
        b1.setBorder(border());
        add(b1);

        JButton c1 = new JButton("C1");
        c1.setBackground(Color.orange);
        c1.setName("ButtonC1");
        c1.setBorder(border());
        add(c1);
    }

    private Border border() {
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        return new CompoundBorder(line, margin);
    }

}
