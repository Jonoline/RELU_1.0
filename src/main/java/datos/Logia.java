package datos;

public class Logia {
    private String ID;
    private int capacidad;
    private String piso;
    private Boolean habilitada = true;

    Logia(String ID, int capacidad, String piso){
    this.ID = ID;
    this.capacidad = capacidad;
    this.piso = piso;
}
    Logia(String ID, int capacidad, String piso, Boolean habilitada){
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

    public String toString() {
        return "logia " + getID() + " con capacidad: " + getCapacidad() +" en el piso: "+ getPiso()
                + " estado: "+ getHabilitada();
    }
}
