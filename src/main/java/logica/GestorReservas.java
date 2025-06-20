package logica;
import datos.Json;
import datos.Logia;
import datos.Reserva;
import datos.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
public class GestorReservas {
    private final ArrayList<Reserva> reservas;
    private final Json json = new Json();
    private final Usuario usuario;
    private Reserva reservaUsuario;

    public GestorReservas(Usuario usuarioLogueado) {
        reservas = new Json().CargarReservas();
        limpiezaReservasAntiguas();
        this.usuario = usuarioLogueado;
    }
    public void agregarReserva(Usuario usuario, Logia logia, LocalDateTime fechaHora){
        reservas.add(new Reserva(usuario.getMatricula(), logia, fechaHora));
        json.IngresarReservas(reservas);
    }
    public Reserva buscarReserva(String matricula){
        for (Reserva r : reservas) {
            if (r.getMatricula().equals(matricula)) {
                return r;
            }
        }
        return null;
    }

    private Boolean verificarUsuarioNoTengaReserva(String matricula){
        // si no lo pilla es null por lo tanto NO tiene reserva y devuelve verdadero, si lo pilla FALSO
        return buscarReserva(matricula) == null;
    }
    public void CancelarReserva(Reserva reservaUsuario){

        reservas.remove(reservaUsuario);
        json.IngresarReservas(reservas);
    }
    private void limpiezaReservasAntiguas(){
        int length = reservas.size();
        for (Reserva r : reservas) {
            if (r.getFechaHora().isBefore(LocalDateTime.now())) {
                reservas.remove(r);
            }
        }
        if (length != reservas.size()) {
        json.IngresarReservas(reservas);
        }
    }



}
