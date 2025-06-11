package logica;

import datos.Json;
import datos.Usuario;
import java.util.ArrayList;



public class GestorUsuarios {
    private final ArrayList<Usuario> listaUsuarios = new ArrayList<>();

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
}

