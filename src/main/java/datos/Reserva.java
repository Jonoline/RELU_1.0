package datos;

import java.time.LocalDateTime;
public class Reserva {

    private final String matricula;
    private final Logia logia;
    private final LocalDateTime fechaHora;

    public Reserva(String matricula, Logia logia, LocalDateTime fechaHora) {
        this.matricula = matricula;
        this.logia = logia;
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


    @Override
    public String toString() {
        return """
           =============================
                    RESERVA
           =============================
           📝 Matrícula: %s
           🏢 Logia: %s
           👥 Capacidad: %d personas
           📍 Piso: %s
           🕒 Fecha y Hora: %s
           ============================="""
                .formatted(matricula,
                        logia.getID(),
                        logia.getCapacidad(),
                        logia.getPiso(),
                        JavaTimeUtils.formatearConHoraFinal(fechaHora));
    }

}
