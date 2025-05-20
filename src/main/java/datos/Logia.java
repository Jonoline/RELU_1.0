package datos;

public class Logia {
    private String id;
    private int capacidad;

    public Logia(String id, int capacidad){
        this.id = id;
        this.capacidad = capacidad;
    }

    public String getId() {
        return id;
    }

    public int getCapacidad() {
        return capacidad;
    }

    @Override
    public String toString() {
        return "Logia "+ id + '\'' +
                ", capacidad='" + capacidad + '\'';
    }
}
