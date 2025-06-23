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

    private String generarSiguienteId() {
        int maxId = 0;
        for (Logia logia : logias) {
            // Extraemos el número del ID (quitando la 'L')
            int currentId = Integer.parseInt(logia.getID().substring(1));
            maxId = Math.max(maxId, currentId);
        }
        return "L" + (maxId + 1);
    }

    public void agregarLogiaADMIN( int capacidad, String piso) throws IllegalArgumentException {
        // Verificar valores válidos
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        }
        if (!piso.matches("P[0-4]+")) {
            throw new IllegalArgumentException("El piso debe tener el formato 'P' seguido de un número (ejemplo: P1, P2)");
        }

        String nuevoId = generarSiguienteId();

        // Crear y agregar la nueva logia
        Logia nuevaLogia = new Logia(nuevoId, capacidad, piso);
        logias.add(nuevaLogia);
        json.ingresarLogias(logias);
        System.out.println("Logia agregada: " + nuevaLogia);
    }

    public void deshabilitarLogiaADMIN(String id) throws IllegalArgumentException {
        Logia logia = obtenerLogia(id);
        if (logia == null) {
            throw new IllegalArgumentException("No existe una logia con el ID: " + id);
        }
        // deshabilitamos en lugar de eliminar
        logia.setHabilitada(false);
        json.ingresarLogias(logias);
    }

    public void habilitarLogiaADMIN(String id) throws IllegalArgumentException {
        Logia logia = obtenerLogia(id);
        if (logia == null) {
            throw new IllegalArgumentException("No existe una logia con el ID: " + id);
        }
        logia.setHabilitada(true);
        json.ingresarLogias(logias);
    }
    public ArrayList<Logia> obtenerTodasLasLogias() {
        return new ArrayList<>(logias);
    }

}
