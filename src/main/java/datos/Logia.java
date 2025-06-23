package datos;

import java.util.Objects;

public class Logia {
    private final String ID;
    private final int capacidad;
    private final String piso;
    private Boolean habilitada = true;

    public Logia(String ID, int capacidad, String piso){
    this.ID = ID;
    this.capacidad = capacidad;
    this.piso = piso;
}
    public Logia(String ID, int capacidad, String piso, Boolean habilitada){
        this.ID = ID;
        this.capacidad = capacidad;
        this.piso = piso;
        this.habilitada = habilitada;
    }
    public String getID() {
        return ID;
    }

    public int getCapacidad() {
    return capacidad;
    }

    public String getPiso() {
    return piso;
    }

    public Boolean getHabilitada() {
        return habilitada;
    }

    public void setHabilitada(Boolean habilitada) {
        this.habilitada = habilitada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logia logia = (Logia) o;
        return ID.equals(logia.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    public String toString() {
        return "logia " + getID() + " con capacidad: " + getCapacidad() +" en el piso: "+ getPiso();
    }
}
