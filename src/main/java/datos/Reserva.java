package datos;

public class Reserva {

    private String matricula;
    private String logiaid;
    private int dia;
    private int hora;

    public Reserva(String matricula, String logiaid, int dia, int hora) {
        this.matricula = matricula;
        this.logiaid = logiaid;
        this.dia = dia;
        this.hora = hora;
    }

    public String getMatricula() {
        return matricula;
    }
    public String getLogiaid() {
        return logiaid;
    }

    public int getDia() {
        return dia;
    }

    public int getHora() {
        return hora;
    }
}
