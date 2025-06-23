package logica;
import datos.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
public class GestorReservas {
    private final ArrayList<Reserva> reservas;
    private final Json json = new Json();
    private final Usuario usuario;
    private Reserva reservaUsuario;


    public GestorReservas(Usuario usuarioLogueado) {
        reservas = new Json().cargarReservas();
        limpiezaReservasAntiguas();
        this.usuario = usuarioLogueado;
        this.reservaUsuario = buscarReserva(usuarioLogueado.getMatricula());
    }

    public GestorReservas() {
        reservas = new Json().cargarReservas();
        limpiezaReservasAntiguas();
        this.usuario = null;
        this.reservaUsuario = null;
    }

    public Reserva getReservaUsuario() {
        return reservaUsuario;
    }


    public void agregarReserva( Logia logia, LocalDateTime fechaHora) throws IllegalArgumentException{
        if (verificarLogiaEnFecha(logia, fechaHora) && verificarUsuarioNoTengaReserva()){
            reservas.add(new Reserva(usuario.getMatricula(), logia, fechaHora));
            json.IngresarReservas(reservas);
            this.reservaUsuario = buscarReserva(usuario.getMatricula());
        }else {
            throw new IllegalArgumentException("Reserva no realizada, ya existe una reserva para esa logia en ese horario");
        }
    }
    public Reserva buscarReserva(String matricula){
        for (Reserva r : reservas) {
            if (r.getMatricula().equals(matricula)) {
                return r;
            }
        }
        return null;
    }

    private Boolean verificarUsuarioNoTengaReserva(){
        // si no lo pilla es null por lo tanto NO tiene reserva y devuelve verdadero, si lo pilla FALSO
        return buscarReserva(usuario.getMatricula()) == null;
    }
    public Boolean CancelarReserva(){
        if (!verificarUsuarioNoTengaReserva()){
            reservas.remove(reservaUsuario);
            json.IngresarReservas(reservas);
            this.reservaUsuario = buscarReserva(usuario.getMatricula());
            return true;
        }else {
            return false;
        }
    }
    private void limpiezaReservasAntiguas(){
        int length = reservas.size();
        reservas.removeIf(r -> r.getFechaHora().isBefore(LocalDateTime.now())); //es como un for :)
        if (length != reservas.size()) {
        json.IngresarReservas(reservas);
        }
    }

    public boolean verificarLogiaEnFecha(Logia logia, LocalDateTime fechaHora){
        if (logia == null || fechaHora == null) {
            throw new IllegalArgumentException("Logia o fecha no puede ser nulo");
        }
        for (Reserva r : reservas) {
            if (r.getLogia().equals(logia) && r.getFechaHora().equals(fechaHora)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Horario> obtenerBloquesDisponibles(Logia logia, int dia, String mes) {
        ArrayList<Horario> bloquesDisponibles = new ArrayList<>();
        LocalDateTime fechaBase = crearFechaBase(dia, mes);

        verificarDia(fechaBase);

        for (Horario horario : Horario.values()) {
            LocalDateTime fechaPropuesta = crearFechaPropuesta(fechaBase, horario);

            if (fechaValida(fechaPropuesta, logia)) {
                bloquesDisponibles.add(horario);
            }
        }

        return bloquesDisponibles;
    }

    private LocalDateTime crearFechaBase(int dia, String mes) {
        return LocalDateTime.of(
                LocalDateTime.now().getYear(),
                Integer.parseInt(mes),
                dia,
                0, 0
        );
    }

    private void verificarDia(LocalDateTime fecha) {
        switch (fecha.getDayOfWeek()) {
            case SATURDAY -> throw new IllegalArgumentException("No se pueden realizar reservas los sábados");
            case SUNDAY -> throw new IllegalArgumentException("No se pueden realizar reservas los domingos");
        }
    }

    private LocalDateTime crearFechaPropuesta(LocalDateTime fechaBase, Horario horario) {
        return LocalDateTime.of(
                fechaBase.getYear(),
                fechaBase.getMonthValue(),
                fechaBase.getDayOfMonth(),
                Integer.parseInt(horario.getHoraInicio().split(":")[0]),
                Integer.parseInt(horario.getHoraInicio().split(":")[1])
        );
    }

    private boolean fechaValida(LocalDateTime fechaPropuesta, Logia logia) {
        if (fechaPropuesta.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha seleccionada no puede ser anterior a la fecha actual");
        }

        return verificarLogiaEnFecha(logia, fechaPropuesta);
    }
}
