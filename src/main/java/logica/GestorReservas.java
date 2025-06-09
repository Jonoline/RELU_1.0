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

    public GestorReservas() {
        reservas = new Json().CargarReservas();
        limpiezaReservasAntiguas();
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
        for (Reserva r : reservas) {
            if (r.getMatricula().equals(matricula)) {
                return false;
            }
        }
        return true;
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
