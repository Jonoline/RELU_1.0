package logica;

import datos.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GestorAdmin {
    private final GestorUsuarios gestorUsuarios;
    private final GestorReservas gestorReservas ;
    private final Json json = new Json();



    public GestorAdmin(GestorUsuarios gestorUsuarios) {
        this.gestorReservas = new GestorReservas();
        this.gestorUsuarios = gestorUsuarios;
    }



    public void agregarReservaParaUsuario(Usuario usuario, Logia logia, LocalDateTime fechaHora) throws IllegalArgumentException{
        gestorReservas.agregarReserva(usuario, logia, fechaHora);
    }


    public Reserva buscarReserva(String matricula){
        return gestorReservas.buscarReserva(matricula);
    }

    public Boolean CancelarReserva(Usuario usuario){
        return gestorReservas.CancelarReserva(usuario);
    }



    public void agregarUsuario(String matricula, String ufromail, String contrasena, Rol rol) throws IllegalArgumentException {
        ArrayList<Usuario> usuarios = gestorUsuarios.getListaUsuarios();
        verificacionesUsuario(usuarios, matricula, contrasena, ufromail, rol);
        // Crear y agregar el nuevo usuario
        Usuario nuevoUsuario = new Usuario(ufromail, matricula, contrasena, rol);
        usuarios.add(nuevoUsuario);
        json.ingresarUsuarios(usuarios);
        gestorUsuarios.setListaUsuarios(usuarios);
    }
    private void verificacionesUsuario(ArrayList<Usuario> usuarios, String matricula, String contrasena, String ufromail, Rol rol){
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
        // Validar contraseña no vacía
        if (contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        if (!validarUfromailSegunRol(ufromail, rol)){
            throw new IllegalArgumentException("El correo debe ser acorde al rol, ufromail.cl -> Estudiante, ufrontera.cl -> Profesor, cualquiera -> Administrador");
        }
    }

    public boolean validarUfromailSegunRol(String ufromail, Rol rol) {
        if (ufromail == null || rol == null) {
            return false;
        }

        return switch (rol) {
            case ESTUDIANTE -> ufromail.endsWith("@ufromail.cl");
            case PROFESOR -> ufromail.endsWith("@ufrontera.cl");
            case ADMINISTRADOR -> true; // El administrador puede usar cualquier correo
        };
    }

    public void eliminarUsuario(String matricula) throws IllegalArgumentException {
        ArrayList<Usuario> usuarios = gestorUsuarios.getListaUsuarios();
        Usuario usuario = gestorUsuarios.buscarUsuario(matricula);
        usuarios.remove(usuario);
        gestorUsuarios.eliminarUsuario(usuario);
        json.ingresarUsuarios(usuarios);
    }








}
