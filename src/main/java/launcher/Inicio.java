package launcher;

import ui.ViewInicioSesion;

import javax.swing.*;

public class Inicio {
    public static void main(String[] args) {
        try {
            SwingUtilities.invokeLater(() -> {
                ViewInicioSesion view = new ViewInicioSesion();
                view.setVisible(true);
            });

        } catch (Exception e) {
            System.err.println("Error al iniciar el programa: " + e.getMessage());

        }
    }
}

