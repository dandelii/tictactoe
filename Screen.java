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
    static boolean lose = false;

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
                        checkTeamWin();
                        if (!win) {
                            opponentTurn();
                        } else {
                            JOptionPane.showMessageDialog(
                                null, 
                                "You win!", 
                                "Victory", 
                                JOptionPane.INFORMATION_MESSAGE
                            );
                            win = false;
                        }
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

        } else if (lose) {
            JOptionPane.showMessageDialog(
                null,
                "You lose!",
                "Loss",
                JOptionPane.INFORMATION_MESSAGE
            );
            lose = false;

        } else if (canTurn) {
            int finalMoveIndex = -1;
            boolean playsSmart = Math.random() < 0.80; 

            if (playsSmart) {
                finalMoveIndex = findWinningMove("O");

                if (finalMoveIndex == -1) {
                    finalMoveIndex = findWinningMove("X");
                }

                if (finalMoveIndex == -1 && boxes[4].getText().equals("")) {
                    finalMoveIndex = 4;
                }

                if (finalMoveIndex == -1) {
                    int[] corners = {0, 2, 6, 8};
                    for (int j = corners.length - 1; j > 0; j--) {
                        int index = (int)(Math.random() * (j + 1));
                        int temp = corners[index];
                        corners[index] = corners[j];
                        corners[j] = temp;
                    }
                    
                    for (int corner : corners) {
                        if (boxes[corner].getText().equals("")) {
                            finalMoveIndex = corner;
                            break;
                        }
                    }
                }
            }

            if (finalMoveIndex == -1) {
                boolean isFree = false;
                do {
                    int randomNum = (int)(Math.random() * 9);
                    if (!boxes[randomNum].getText().equals("X") && !boxes[randomNum].getText().equals("O")) {
                        finalMoveIndex = randomNum;
                        isFree = true;
                    }
                } while (!isFree);
            }

            boxes[finalMoveIndex].setText("O");
            panel.removeAll();
            addBoxes();
            panel.revalidate();
            panel.repaint();

            checkOpponentWin();
            if (lose) {
                JOptionPane.showMessageDialog(null, "You lose!", "Loss", JOptionPane.INFORMATION_MESSAGE);
                lose = false;
            }
        } else {
            JOptionPane.showMessageDialog(
                null, 
                "Nobody won!", 
                "Board Full", 
                JOptionPane.INFORMATION_MESSAGE
            );
            for (i = 0; i < boxes.length; i++) {
                boxes[i].setText("");
                panel.removeAll();
                addBoxes();
                panel.revalidate();
                panel.repaint();
            }
        }
    }

    static int findWinningMove(String s) {
        int i;
        int targetIndex = -1;

        for (i = 0; i < boxes.length; i++) {
            // Horizontal Checks
            if (i == 0 || i == 3 || i == 6) {
                if (boxes[i].getText().equals(s) && boxes[i + 1].getText().equals(s) && boxes[i + 2].getText().equals("")) {
                    targetIndex = i + 2; break;
                }
                if (boxes[i].getText().equals(s) && boxes[i + 2].getText().equals(s) && boxes[i + 1].getText().equals("")) {
                    targetIndex = i + 1; break;
                }
                if (boxes[i + 1].getText().equals(s) && boxes[i + 2].getText().equals(s) && boxes[i].getText().equals("")) {
                    targetIndex = i; break;
                }
            }
            // Vertical Checks
            if (i == 0 || i == 1 || i == 2) {
                if (boxes[i].getText().equals(s) && boxes[i + 3].getText().equals(s) && boxes[i + 6].getText().equals("")) {
                    targetIndex = i + 6; break;
                }
                if (boxes[i].getText().equals(s) && boxes[i + 6].getText().equals(s) && boxes[i + 3].getText().equals("")) {
                    targetIndex = i + 3; break;
                }
                if (boxes[i + 3].getText().equals(s) && boxes[i + 6].getText().equals(s) && boxes[i].getText().equals("")) {
                    targetIndex = i; break;
                }
            }
            // Diagonal 1 Check (Top-Left to Bottom-Right)
            if (i == 0) {
                if (boxes[0].getText().equals(s) && boxes[4].getText().equals(s) && boxes[8].getText().equals("")) {
                    targetIndex = 8; break;
                }
                if (boxes[0].getText().equals(s) && boxes[8].getText().equals(s) && boxes[4].getText().equals("")) {
                    targetIndex = 4; break;
                }
                if (boxes[4].getText().equals(s) && boxes[8].getText().equals(s) && boxes[0].getText().equals("")) {
                    targetIndex = 0; break;
                }
            }
            // Diagonal 2 Check (Bottom-Left to Top-Right)
            if (i == 2) {
                if (boxes[2].getText().equals(s) && boxes[4].getText().equals(s) && boxes[6].getText().equals("")) {
                    targetIndex = 6; break;
                }
                if (boxes[2].getText().equals(s) && boxes[6].getText().equals(s) && boxes[4].getText().equals("")) {
                    targetIndex = 4; break;
                }
                if (boxes[4].getText().equals(s) && boxes[6].getText().equals(s) && boxes[2].getText().equals("")) {
                    targetIndex = 2; break;
                }
            }
        }
        return targetIndex;
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

    static void checkOpponentWin() {
        boolean isWin = false;
        int i;
        for (i = 0; i < boxes.length; i++) {
            if (boxes[i].getText().equals("O")) {
                if ((i == 0 || i == 3 || i == 6) && boxes[i + 1].getText().equals("O") && boxes[i + 2].getText().equals("O")) {
                isWin = true;
                lose = true;
                break;
                }
                if (i == 2 && boxes[i + 2].getText().equals("O") && boxes[i + 4].getText().equals("O")) {
                isWin = true;
                lose = true;
                break;
                }
                if (i == 0 && boxes[i + 4].getText().equals("O") && boxes[i + 8].getText().equals("O")) {
                isWin = true;
                lose = true;
                break;
                }
                if (i < 3 && boxes[i + 3].getText().equals("O") && boxes[i + 6].getText().equals("O")) {
                isWin = true;
                lose = true;
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