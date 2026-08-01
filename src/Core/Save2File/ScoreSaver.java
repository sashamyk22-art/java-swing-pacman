package Core.Save2File;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreSaver {

    private final JLabel scoreLabel;

    public ScoreSaver(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public void saveScore() {
        String scoreText = scoreLabel.getText();
        String score = scoreText.split(":")[1].trim();


        String fileName = "1231.txt";
        String playerName = JOptionPane.showInputDialog(null,"Podaj swoje imię:", "Zapisz wyniki",JOptionPane.QUESTION_MESSAGE); // proszenie gracza o podanie nazwy

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(playerName + ";" + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

