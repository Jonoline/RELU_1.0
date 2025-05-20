package logica;
import datos.Logia;

import java.util.*;
public class GestorLogias {
    private List<Logia> logias;

    public GestorLogias(){
        this.logias = new ArrayList<>();
        inicializarLogias();

    }
    private void inicializarLogias(){
        for (int i=1; i<=8; i++){
            logias.add(new Logia("L" + i,6));
        }
        for (int i=9; i<=16; i++)
            logias.add(new Logia("L" + i,4));
    }

    public List<Logia> getLogias() {
        return logias;
    }
    public Logia buscarLogiaPorID(String id){
        return logias.stream().filter(logia -> logia.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public void mostrarLogias(){
        for(Logia l : logias){
            System.out.println(l);
        }
    }
}
