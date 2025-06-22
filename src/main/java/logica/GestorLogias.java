package logica;

import datos.Logia;
import datos.Json;


import java.util.ArrayList;

public class GestorLogias {
    private ArrayList<Logia> logias;
    private Json json = new Json();

    public GestorLogias(){
        logias = json.cargarLogias();
    }

    public void mostrarLogias(){
        for (Logia l : logias) {
            System.out.println(l);
        }
    }

    public Logia obtenerLogia(String ID){
        for (Logia l : logias) {
            if (l.getID().equals(ID)) {
                return l;
            }
        }
        return null;
    }

}
