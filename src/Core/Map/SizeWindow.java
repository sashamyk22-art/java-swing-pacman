package Core.Map;

import Core.Menu.Menu;
import Core.PacmanMovement.PacmanKeyListener;
import Core.Save2File.ScoreSaver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static Core.Map.GameTableModel1.*;

public class SizeWindow extends JTable {

//    private int points = 0;

    public SizeWindow() {

        //prośba o wymiar
        JOptionPane.showMessageDialog(null, "Witamy w grze PACMAN, podaj rozmiary okna", "UWAGA", JOptionPane.WARNING_MESSAGE);
        String customWidth = JOptionPane.showInputDialog(null, "szerokość (10-100)", "Game Window Dimension", JOptionPane.QUESTION_MESSAGE);
        String customHeight = JOptionPane.showInputDialog(null, "Wysokość (10-100)", "Game Window Dimension", JOptionPane.QUESTION_MESSAGE);

        //warunki
        try {
            int width = Integer.parseInt(customWidth);
            int height = Integer.parseInt(customHeight);

            if (width >= 10 && width <= 100 && height >= 10 && height <= 100) {

                JFrame frame = new JFrame("That's the game");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                ImageIcon icon = new ImageIcon("wall.jpg");

                frame.setIconImage(icon.getImage());

                if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Taskbar.Feature.ICON_IMAGE)) {
                    ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH));

                    Taskbar.getTaskbar().setIconImage(scaledIcon.getImage());
                }


                GameTableModel1 model = new GameTableModel1(height, width);

                JLabel scoreLabel = new JLabel("score: 0");
                scoreLabel.setForeground(Color.YELLOW);
                scoreLabel.setOpaque(true);
                scoreLabel.setBackground(Color.BLACK);

                JTable table = new JTable(model) {
                    @Override
                    public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
                        if (model.MAP[rowIndex][columnIndex] != WALL) {
                            super.changeSelection(rowIndex, columnIndex, toggle, extend);
                        }
                    }
                };



                table.setShowGrid(false);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                table.setBackground(Color.BLACK);
                table.setFillsViewportHeight(true);
                table.setIntercellSpacing(new Dimension(0, 0));
                table.setTableHeader(null);

                JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane.getVerticalScrollBar().setValue(0);
                scrollPane.getHorizontalScrollBar().setValue(0);

                table.addKeyListener(new PacmanKeyListener(model, table, scoreLabel, scrollPane, frame));
                table.setSelectionBackground(table.getBackground());
                table.setSelectionForeground(table.getForeground());
                table.setCellSelectionEnabled(false);

                JPanel panelEND = new JPanel(new BorderLayout());
                panelEND.add(scoreLabel, BorderLayout.NORTH);
                panelEND.add(scrollPane, BorderLayout.CENTER);

                KeyStroke keyStroke = KeyStroke.getKeyStroke("control shift Q");
                frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "closeWindow");
                frame.getRootPane().getActionMap().put("closeWindow", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ScoreSaver scoreSaver = new ScoreSaver(scoreLabel);
                        scoreSaver.saveScore();
                        frame.dispose();
                        SwingUtilities.invokeLater(() -> new Menu());
                    }
                });


                InputMap scrollPaneInputMap = table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

                table.setRowSelectionAllowed(true);
                table.setColumnSelectionAllowed(true);
                table.setCellSelectionEnabled(true);
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                table.setDefaultRenderer(Object.class, new MyTableCellRenderer());

                table.setShowGrid(false);


                scrollPane.setBorder(null);

                frame.setContentPane(panelEND);
                frame.pack();
                frame.setVisible(true);
                setDoubleBuffered(true);

                frame.revalidate();
                frame.repaint();


            } else {
                JOptionPane.showMessageDialog(null, "Wymiary muszą być większe niż 10, kliknij ok, alby spróbować ponownie.", "Błąd", JOptionPane.ERROR_MESSAGE);
                SwingUtilities.invokeLater(() -> new SizeWindow());
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Wymiar musi być liczbą, kliknij ok, aby spróbować ponownie.", "Błąd", JOptionPane.ERROR_MESSAGE);
            SwingUtilities.invokeLater(() -> new SizeWindow());
        }
    }
}