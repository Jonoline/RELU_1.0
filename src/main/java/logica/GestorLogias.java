package logica;

import datos.Logia;
import datos.Json;


import java.util.ArrayList;

public class GestorLogias {
    private final ArrayList<Logia> logias;
    private final Json json = new Json();

    public GestorLogias(){
        logias = json.cargarLogias();
    }

    public void mostrarLogias() {
        System.out.println("\nLogias disponibles:");
        for (Logia l : logias) {
            if (l.getHabilitada()) {
                System.out.println("[" + l.getID() + "] " + "Capacidad: " +
                        l.getCapacidad() + " - Piso: " + l.getPiso());
            }
        }
    }

    public Logia obtenerLogia(String ID) {
        for (Logia l : logias) {
            if (l.getID().equals(ID)) {
                return l;
            }
        }
        return null;
    }

}
