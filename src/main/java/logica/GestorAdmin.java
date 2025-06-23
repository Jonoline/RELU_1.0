package logica;

import datos.*;
import java.time.LocalDateTime;

public class GestorAdmin {
    private final GestorUsuarios gestorUsuarios;
    private final GestorReservas gestorReservas;
    private final GestorLogias gestorLogias;
    private final Json json = new Json();

    // Constructor mejorado con inyección de dependencias
    public GestorAdmin(GestorUsuarios gestorUsuarios, GestorReservas gestorReservas, GestorLogias gestorLogias) {
        this.gestorUsuarios = gestorUsuarios;
        this.gestorReservas = gestorReservas;
        this.gestorLogias = gestorLogias;
    }

    // Métodos delegados corregidos
    public void agregarReservaParaUsuario(Usuario usuario, Logia logia, LocalDateTime fechaHora) {
        gestorReservas.agregarReservaAdmin(usuario, logia, fechaHora);
    }

    public Reserva buscarReservaDeUsuario(String matricula) {
        return gestorReservas.buscarReserva(matricula);
    }

    public boolean cancelarReservaDeUsuario(Usuario usuario) {
        return gestorReservas.CancelarReserva(usuario);
    }

    // Métodos de gestión de logias
    public void agregarLogia(int capacidad, String piso) {
        gestorLogias.agregarLogiaADMIN(capacidad, piso);
    }

    public void habilitarLogia(String id) {
        gestorLogias.habilitarLogiaADMIN(id);
    }

    public void deshabilitarLogia(String id) {
        gestorLogias.deshabilitarLogiaADMIN(id);
    }

    public void agregarUsuario(String matricula, String ufromail, String contrasena, Rol rol) {
        Usuario nuevoUsuario = new Usuario(ufromail, matricula, contrasena, rol);
        gestorUsuarios.agregarUsuario(nuevoUsuario);
    }
}