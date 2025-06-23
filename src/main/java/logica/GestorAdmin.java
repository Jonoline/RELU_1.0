package logica;

import datos.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestorAdmin {
    private final GestorUsuarios gestorUsuarios = new GestorUsuarios();
    private final GestorReservas gestorReservas = new GestorReservas(null);
    private final ArrayList<Reserva> reservas;
    private final Json json = new Json();



    public GestorAdmin(Usuario usuarioLogueado) {
        reservas = new Json().cargarReservas();
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
        System.out.println("Usuario agregado correctamente");
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
        usuarios.remove(gestorUsuarios.buscarUsuario(matricula));
        json.ingresarUsuarios(usuarios);
        System.out.println("Usuario eliminado correctamente");
    }

    public void mostrarUsuarios() {
        System.out.println("\nLista de Usuarios:");
        System.out.println("------------------");
        for (Usuario u : gestorUsuarios.getListaUsuarios()) {
            System.out.println("Matrícula: " + u.getMatricula());
            System.out.println("Correo: " + u.getUfromail());
            System.out.println("------------------");
        }
    }







}
