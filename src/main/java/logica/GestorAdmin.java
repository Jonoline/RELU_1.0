package logica;

import datos.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestorAdmin {
    private final GestorUsuarios gestorUsuarios;
    private final GestorReservas gestorReservas ;
    private final ArrayList<Reserva> reservas;
    private final Json json = new Json();



    public GestorAdmin(GestorUsuarios gestorUsuarios) {
        this.reservas = new Json().cargarReservas();
        this.gestorReservas = new GestorReservas();
        this.gestorUsuarios = gestorUsuarios;
    }



    public void agregarReserva(Usuario usuario, Logia logia, LocalDateTime fechaHora) throws IllegalArgumentException{
        if (gestorReservas.verificarLogiaEnFecha(logia, fechaHora) && verificarUsuarioNoTengaReserva( usuario)){
            reservas.add(new Reserva(usuario.getMatricula(), logia, fechaHora));
            json.IngresarReservas(reservas);
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

    private Boolean verificarUsuarioNoTengaReserva(Usuario usuario){
        // si no lo pilla es null por lo tanto NO tiene reserva y devuelve verdadero, si lo pilla FALSO
        return buscarReserva(usuario.getMatricula()) == null;
    }

    public Boolean CancelarReserva(Usuario usuario){
        if (!verificarUsuarioNoTengaReserva(usuario)){
            Reserva reserva =buscarReserva(usuario.getMatricula());
            reservas.remove(reserva);
            json.IngresarReservas(reservas);
            return true;
        }else {
            return false;
        }
    }



    public void agregarUsuario(String matricula, String ufromail, String contrasena) throws IllegalArgumentException {
        ArrayList<Usuario> usuarios = gestorUsuarios.getListaUsuarios();
        verificacionesUsuario(usuarios, matricula, ufromail, contrasena);
        // Crear y agregar el nuevo usuario
        Usuario nuevoUsuario = new Usuario(ufromail, matricula, contrasena);
        usuarios.add(nuevoUsuario);
        json.ingresarUsuarios(usuarios);
        gestorUsuarios.setListaUsuarios(usuarios);
    }
    private void verificacionesUsuario(ArrayList<Usuario> usuarios, String matricula, String ufromail, String contrasena){
        // Validar que la matrícula no exista
        for (Usuario u : usuarios) {
            if (u.getMatricula().equals(matricula)) {
                throw new IllegalArgumentException("Ya existe un usuario con esa matrícula");
            }
        }
        // Validar formato de matrícula
        if (!matricula.matches("\\d{8}[0-9kK]\\d{2}")) {
            throw new IllegalArgumentException("Formato de matrícula inválido");
        }
        // Validar formato de ufromail
        if (!ufromail.endsWith("@ufromail.cl")) {
            throw new IllegalArgumentException("El correo debe ser un ufromail válido");
        }
        // Validar contraseña no vacía
        if (contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }

    }

    public void eliminarUsuario(String matricula) throws IllegalArgumentException {
        ArrayList<Usuario> usuarios = gestorUsuarios.getListaUsuarios();
        Usuario usuario = gestorUsuarios.buscarUsuario(matricula);
        usuarios.remove(usuario);
        gestorUsuarios.eliminarUsuario(usuario);
        json.ingresarUsuarios(usuarios);
    }








}
