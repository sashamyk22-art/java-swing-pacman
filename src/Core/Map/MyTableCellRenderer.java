package Core.Map;

import Core.Map.GameTableModel1;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.net.URL;

import static Core.Map.GameTableModel1.*;

/*
Ikony są zaimplementowane + pliki graficzne znajdują się w Folderze,
Nie mogłem ich dodać, ponieważ wtedy na moim komputerze render działa bardzo powoli
Dlatego użytkownik w tej wersji doświadcza tylko kolorowych kwadratów :)
Tam, gdzie Ikona wyłączamy komentarz
Tam, gdzie jej nie ma ! o ile w innych będą dajemy null
 */

public  class MyTableCellRenderer extends DefaultTableCellRenderer {

    URL wallIco = getClass().getResource("/Rescources/wall.jpg");
    ImageIcon wallIcon = new ImageIcon(wallIco);
    URL coinIco = getClass().getResource("/Rescources/coin.png");
    ImageIcon pointIcon = new ImageIcon(coinIco);
    URL urlIco = getClass().getResource("/Rescources/225231.png");
    ImageIcon pacmanIcon = new ImageIcon(urlIco);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        GameTableModel1 model = (GameTableModel1) table.getModel();
        setBorder(noFocusBorder);
        setBorder(BorderFactory.createEmptyBorder());
        if (model.MAP[row][column] == WALL) {
//            setIcon(new ImageIcon(wallIcon.getImage().getScaledInstance(table.getColumnModel().getColumn(column).getWidth(), table.getRowHeight(row), Image.SCALE_DEFAULT)));
//            setIcon(wallIcon);
//            setIcon(null);
            setBackground(Color.BLACK);
            setForeground(Color.BLACK);
        } else if (model.MAP[row][column] == EMPTY) {
//            setIcon(null);
            setBackground(Color.BLUE);
            setForeground(Color.BLUE);
        } else if (model.MAP[row][column] == PACMAN) {
//            setIcon(null);
//            setIcon(new ImageIcon(pacmanIcon.getImage().getScaledInstance(table.getColumnModel().getColumn(column).getWidth(), table.getRowHeight(row), Image.SCALE_DEFAULT)));
//            setIcon(pacmanIcon);
            setBackground(Color.PINK);
            setForeground(Color.PINK);
        } else if (model.MAP[row][column] == POINT) {
//            setIcon(null);
//            setIcon(new ImageIcon(pointIcon.getImage().getScaledInstance(table.getColumnModel().getColumn(column).getWidth(), table.getRowHeight(row), Image.SCALE_DEFAULT)));
//            setIcon(pointIcon);
            setBackground(Color.GREEN);
            setForeground(Color.GREEN);
        } else if (model.MAP[row][column] == GHOST) {
            setBackground(Color.RED);
            setForeground(Color.RED);
        }
        return this;
    }
}