package logica;

import datos.Json;
import datos.Usuario;
import java.util.ArrayList;
import java.util.Scanner;


public class GestorUsuarios {
    private final Scanner sc = new Scanner(System.in);
    private final ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    public GestorUsuarios() {
        Json json = new Json();
        listaUsuarios.addAll(json.cargarUsuarioJson());
    }
    public Usuario pedirDatosLogin() {
        System.out.print("Ingrese matrícula: ");
        String matricula = sc.nextLine();

        System.out.print("Ingrese contraseña: ");
        String contrasena = sc.nextLine();

        return new Usuario(matricula, contrasena); // correo vacío porque no es necesario para login
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

