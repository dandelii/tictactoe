package tictactoe;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Screen {

    static JFrame scrn = new JFrame("frame");
    static GridLayout grid = new GridLayout(3, 3);
    static JPanel panel = new JPanel(grid);

    static JButton one = new JButton();
    static JButton two = new JButton();
    static JButton three = new JButton();
    static JButton four = new JButton();
    static JButton five = new JButton();
    static JButton six = new JButton();
    static JButton seven = new JButton();
    static JButton eight = new JButton();
    static JButton nine = new JButton();
    static JButton[] boxes = {one, two, three, four, five, six, seven, eight, nine};

    static boolean win = false;

    public static void main(String[] args) {
        int i;
        for (i = 0; i < boxes.length; i++) {
            final int boxIndex = i;
            ActionListener generalButtonHandler = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!boxes[boxIndex].getText().equals("O") && !boxes[boxIndex].getText().equals("X")) {
                        boxes[boxIndex].setText("X");
                        panel.removeAll();
                        addBoxes();
                        panel.revalidate();
                        panel.repaint();
                        opponentTurn();
                        checkTeamWin();
                    }
                }
            };
            boxes[i].addActionListener(generalButtonHandler);

            boxes[i].setLayout(new GridLayout(1, 1));
            addBoxes();
        }

        panel.setPreferredSize(new Dimension(300, 300));
        scrn.add(panel);
        scrn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scrn.setLocationRelativeTo(null);
        scrn.pack();
        scrn.setVisible(true);
    }

    static void addBoxes() {
        int i;
        for (i = 0; i < boxes.length; i++) {
            panel.add(boxes[i]);
        }
    }

    static void opponentTurn() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) { System.err.print(e); }
        checkTeamWin(); 

        int i;
        boolean canTurn = false;
        for (i = 0; i < boxes.length; i++) {
            if (boxes[i].getText().equals("")) {
                canTurn = true;
                break;
            }
        }

        if (win) {
            JOptionPane.showMessageDialog(
                null, 
                "You win!", 
                "Victory", 
                JOptionPane.INFORMATION_MESSAGE
            );
            win = false;

        } else if (canTurn) {
            boolean isFree = false;
            int randomNum = 0;
            do {
                randomNum = (int)(Math.random() * 9);
                if (!boxes[randomNum].getText().equals("X") && !boxes[randomNum].getText().equals("O")) {
                    isFree = true;
                }
            } while (!isFree);
            boxes[randomNum].setText("O");
            panel.removeAll();
            addBoxes();
            panel.revalidate();
            panel.repaint();

        } else {
            JOptionPane.showMessageDialog(
                null, 
                "Nobody won!", 
                "Board Full", 
                JOptionPane.INFORMATION_MESSAGE
            );

        }
    }

    static void checkTeamWin() {
        boolean isWin = false;
        int i;
        for (i = 0; i < boxes.length; i++) {
            if (boxes[i].getText().equals("X")) {
                if ((i == 0 || i == 3 || i == 6) && boxes[i + 1].getText().equals("X") && boxes[i + 2].getText().equals("X")) {
                    isWin = true;
                    win = true;
                    break;
                }
                if (i == 2 && boxes[i + 2].getText().equals("X") && boxes[i + 4].getText().equals("X")) {
                    isWin = true;
                    win = true;
                    break;
                }
                if (i == 0 && boxes[i + 4].getText().equals("X") && boxes[i + 8].getText().equals("X")) {
                    isWin = true;
                    win = true;
                    break;
                }
                if (i < 3 && boxes[i + 3].getText().equals("X") && boxes[i + 6].getText().equals("X")) {
                    isWin = true;
                    win = true;
                    break;
                }
            }
        }
        if (isWin) {
            for (i = 0; i < boxes.length; i++) {
                boxes[i].setText("");
                panel.removeAll();
                addBoxes();
                panel.revalidate();
                panel.repaint();
            }
        }
    }


}
