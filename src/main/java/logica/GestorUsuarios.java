package logica;

import datos.Json;
import datos.Usuario;
import java.util.ArrayList;
import datos.Rol;



public class GestorUsuarios {

    private ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    public GestorUsuarios() {
        Json json = new Json();
        listaUsuarios.addAll(json.cargarUsuarioJson());
    }

    public Usuario iniciarSesion(Usuario tryUsuario) {
        for (Usuario u : listaUsuarios) {
            if (u.equals(tryUsuario)) {
                System.out.println("✅ Inicio de sesión exitoso.");
                return u;
            }
        }
        System.out.println("Matrícula o contraseña incorrecta. Intente nuevamente.\n");
        return null;
    }
    public Usuario buscarUsuario(String matricula){
        String matriculaNormalizada = matricula.trim();
        for (Usuario u : listaUsuarios) {
            if (u.getMatricula().equals(matriculaNormalizada)) {
                return u;
            }
        }
        throw new IllegalArgumentException("No se ha encontrado el usuario en el sistema.");
    }

    public ArrayList<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }
    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    public void agregarUsuario(Usuario usuario) {
        listaUsuarios.add(usuario);
    }
    public void eliminarUsuario(Usuario usuario) {
        listaUsuarios.remove(usuario);
    }
    public boolean usuarioEsAdmin(Usuario usuario) {
        return usuario.getRol() == Rol.ADMINISTRADOR;
    }
}