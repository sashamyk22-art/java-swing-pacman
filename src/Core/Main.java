package Core;

import Core.Menu.Menu;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Menu());
    }

}
