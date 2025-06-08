package datos;

public class Reserva {

    private String matricula;
    private Logia logia;
    private int capacidad;
    private int dia;
    private int hora;

    public Reserva(String matricula, Logia logia, int dia, int hora) {
        this.matricula = matricula;
        this.logia = logia;
        this.capacidad = logia.getCapacidad();
        this.dia = dia;
        this.hora = hora;
    }

    public String getMatricula() {
        return matricula;
    }

    public Logia getLogia() {
        return logia;
    }

    public int getDia() {
        return dia;
    }

    public int getHora() {
        return hora;
    }

    public int getCapacidad() {
        return capacidad;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "matricula='" + matricula + '\'' +
                ", logia=" + logia +
                ", capacidad=" + capacidad +
                ", dia=" + dia +
                ", hora=" + hora +
                '}';
    }
}
