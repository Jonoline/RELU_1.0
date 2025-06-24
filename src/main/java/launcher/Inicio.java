package launcher;

import ui.InicioSesion;

public class Inicio {
    public static void main(String[] args) {
        try {
            InicioSesion inicioSesion = new InicioSesion();
            inicioSesion.menu();
        } catch (Exception e) {
            System.err.println("Error al iniciar el programa: " + e.getMessage());

        }
    }
}

