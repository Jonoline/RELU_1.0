package logica;

import datos.Logia;
import datos.Json;


import java.util.ArrayList;

public class GestorLogias {
    private ArrayList<Logia> logias;
    private Json json = new Json();
    public GestorLogias(){
        logias = json.cargarLogias();
        System.out.println("Logias cargadas");
        System.out.println(logias.toString());
    }
}
