package datos;

import java.time.LocalDateTime;
public class Reserva {

    private final String matricula;
    private final Logia logia;
    private final int capacidad;
    private final LocalDateTime fechaHora;

    public Reserva(String matricula, Logia logia, LocalDateTime fechaHora) {
        this.matricula = matricula;
        this.logia = logia;
        this.capacidad = logia.getCapacidad();
        this.fechaHora = fechaHora;
    }

    public String getMatricula() {
        return matricula;
    }

    public Logia getLogia() {
        return logia;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public int getCapacidad() {
        return capacidad;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "matricula=" + matricula + '\n' +
                ", logia=" + logia + "\n" +
                ", fechaHora= " + JavaTimeUtils.formatearConHoraFinal(fechaHora) +
                '}';
    }
}
