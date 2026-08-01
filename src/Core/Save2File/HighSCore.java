package Core.Save2File;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.Vector;

public class HighSCore extends JFrame {

    private JTable table;

    public HighSCore() {

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton back2menu= new JButton("Back 2 menu");

        back2menu.setBackground(Color.BLACK);
        back2menu.setForeground(Color.YELLOW);

        back2menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        table = new JTable();
        try {
            loadTableData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        table.setBackground(Color.BLACK);
        table.setForeground(Color.YELLOW);




        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(back2menu, BorderLayout.PAGE_END);
        panel.add(scrollPane, BorderLayout.CENTER);

        panel.setBackground(Color.BLACK);
        panel.setForeground(Color.YELLOW);

        setContentPane(panel);

        add(new JScrollPane(table), BorderLayout.CENTER);

        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadTableData() throws IOException {
        String filePath = "1231.txt";
        File file = new File(filePath);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = null;
        Vector<Vector<Object>> data = new Vector<>();
        Vector<String> headers = new Vector<>();

        headers.add("Name");
        headers.add("Score");

        while ((line = br.readLine()) != null) {
            StringTokenizer st1 = new StringTokenizer(line, ";");
            Vector<Object> row = new Vector<>();
            while (st1.hasMoreTokens()) {
                row.add(st1.nextToken());
                if (st1.hasMoreTokens()) {
                    row.add(Integer.parseInt(st1.nextToken()));
                }
            }
            data.add(row);
        }
        DefaultTableModel model = new DefaultTableModel(data, headers) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    default:
                        return super.getColumnClass(columnIndex);
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        sorter.setComparator(1, Comparator.comparingInt(a -> (Integer) a));
        table.setRowSorter(sorter);

        table.setAutoCreateRowSorter(true);

        table.setModel(model);
        table.setAutoCreateRowSorter(true);

        br.close();
    }
}
