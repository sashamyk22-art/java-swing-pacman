package Core.PacmanMovement;

import Core.Map.GameTableModel1;
import Core.Menu.Menu;
import Core.Save2File.ScoreSaver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static Core.Map.GameTableModel1.*;

public class PacmanKeyListener extends KeyAdapter implements KeyListener {

    private final GameTableModel1 model;
    private final JTable table;
    private final JLabel scoreLabel;
    private final JScrollPane scrollPane;
    private int pacmanRow;
    private int pacmanColumn;
    private JFrame frame;

    public PacmanKeyListener(GameTableModel1 model, JTable table, JLabel scoreLabel, JScrollPane scrollPane, JFrame frame) {
        this.model = model;
        this.table = table;
        this.scoreLabel = scoreLabel;
        this.scrollPane = scrollPane;
        this.frame = frame;
        findPacman();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        findPacman();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                movePacman(pacmanRow - 1, pacmanColumn);
                break;
            case KeyEvent.VK_DOWN:
                movePacman(pacmanRow + 1, pacmanColumn);
                break;
            case KeyEvent.VK_LEFT:
                movePacman(pacmanRow, pacmanColumn - 1);
                break;
            case KeyEvent.VK_RIGHT:
                movePacman(pacmanRow, pacmanColumn + 1);
                break;
        }
        updateScrollPane();
    }

    private void findPacman() {
        for (int row = 0; row < model.MAP.length; row++) {
            for (int column = 0; column < model.MAP[row].length; column++) {
                if (model.MAP[row][column] == PACMAN) {
                    pacmanRow = row;
                    pacmanColumn = column;
                }
            }
        }
    }

    private void movePacman(int newRow, int newColumn) {
        if (newRow >= 0 && newRow < model.MAP.length && newColumn >= 0 && newColumn < model.MAP[0].length) {
            if (model.MAP[newRow][newColumn] != WALL) {
                if (model.MAP[newRow][newColumn] == POINT) {
                    model.points++;
                    System.out.println("Punkty: " + model.points);
                    scoreLabel.setText("Zebrane punkty: " + model.points);  // Aktualizacja JLabel
                    model.POINT_MAP[newRow][newColumn] = EMPTY;
                } else if (model.MAP[newRow][newColumn] == GHOST) {  // sprawdzamy czy duch jest w tej samej komórce co Pacman
                    ScoreSaver scoreSaver = new ScoreSaver(scoreLabel);
                    scoreSaver.saveScore(); // Zapisuje wynik do pliku
                    frame.dispose();
                    SwingUtilities.invokeLater(() -> new Menu());
                    return;
                }
                model.MAP[pacmanRow][pacmanColumn] = EMPTY;
                model.MAP[newRow][newColumn] = PACMAN;
                model.fireTableDataChanged();

                model.moveGhosts();

            }
        }
        table.clearSelection();


        boolean noPointsLeft = true;
        for (int[] row : model.MAP) {
            for (int cell : row) {
                if (cell == POINT) {
                    noPointsLeft = false;
                    break;
                }
            }
            if (!noPointsLeft) {
                break;
            }
        }


        if (noPointsLeft) {
            ScoreSaver scoreSaver = new ScoreSaver(scoreLabel);
            scoreSaver.saveScore(); // Zapisuje wynik do pliku
            frame.dispose();
            SwingUtilities.invokeLater(() -> new Menu());
        }
    }

    private void updateScrollPane() {
        Rectangle visibleArea = scrollPane.getViewport().getViewRect();
        int visibleRows = visibleArea.height / table.getRowHeight();
        int visibleCols = visibleArea.width / table.getColumnModel().getTotalColumnWidth();

        if (pacmanRow - visibleArea.y < 5) {
            scrollPane.getVerticalScrollBar().setValue(Math.max(0, pacmanRow - 5) * table.getRowHeight());
        } else if (visibleArea.y + visibleRows - pacmanRow < 5) {
            scrollPane.getVerticalScrollBar().setValue((pacmanRow - visibleRows + 6) * table.getRowHeight());
        }

        if (pacmanColumn - visibleArea.x < 5) {
            scrollPane.getHorizontalScrollBar().setValue(Math.max(0, pacmanColumn - 5) * table.getColumnModel().getTotalColumnWidth());
        } else if (visibleArea.x + visibleCols - pacmanColumn < 5) {
            scrollPane.getHorizontalScrollBar().setValue((pacmanColumn - visibleCols + 6) * table.getColumnModel().getTotalColumnWidth());
        }
    }

    private void showMenuWindow() {
        SwingUtilities.invokeLater(() -> {
            Menu menu = new Menu();
            menu.setVisible(true);
        });
    }
}