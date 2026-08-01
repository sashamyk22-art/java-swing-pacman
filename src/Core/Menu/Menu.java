package Core.Menu;

import Core.Map.SizeWindow;
import Core.Save2File.HighSCore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Menu extends JFrame {

    public Menu() {

        JPanel jPanel = new JPanel();
        jPanel.setBackground(Color.BLACK);

        jPanel.setLayout(new GridLayout(1, 3));

        JButton newGame = new JButton("New Game");
        newGame.setPreferredSize(new Dimension(100,100));
        newGame.setBackground(Color.BLACK);
        newGame.setForeground(Color.YELLOW);
        JButton highScore = new JButton("High Score");
        highScore.setPreferredSize(new Dimension(100,100));
        highScore.setBackground(Color.BLACK);
        highScore.setForeground(Color.YELLOW);
        JButton exit = new JButton("EXIT");
        exit.setPreferredSize(new Dimension(100,100));
        exit.setBackground(Color.BLACK);
        exit.setForeground(Color.YELLOW);

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("New Game Clicked");
                SwingUtilities.invokeLater(()->new SizeWindow());
                //dispose zamyka stare okno
                dispose();
            }
        });

        highScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(()->new HighSCore());
            }
        });

        URL icon = getClass().getResource("/Rescources/2780137.png");
        ImageIcon exitIcon = new ImageIcon(icon);

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Czy chcesz nas opuścić?",
                        "Potwierdź lub zaprzecz",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        exitIcon);
                if (result == JOptionPane.YES_OPTION) {
                    System.out.println("Użytkownik wybrał 'Tak'");
                    dispose();
                } else {
                    System.out.println("Użytkownik wybrał 'Nie'");
                    dispose();
                    SwingUtilities.invokeLater(()->new Menu());
                }
            }
        });

        jPanel.add(newGame);
        jPanel.add(highScore);
        jPanel.add(exit);

        setContentPane(jPanel);

        pack();
        setTitle("Game Menu");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
