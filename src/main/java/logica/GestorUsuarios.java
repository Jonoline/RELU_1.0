package logica;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import datos.Usuario;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;


public class GestorUsuarios {
    private static final Scanner sc = new Scanner(System.in);

    public static Usuario pedirDatosUsuario() {

        System.out.print("Ingrese correo UfroMail: ");
        String ufromail = sc.nextLine();

        System.out.print("Ingrese matrícula: ");
        String matricula = sc.nextLine();

        System.out.print("Ingrese contraseña: ");
        String contrasena = sc.nextLine();

        return new Usuario(ufromail, matricula, contrasena);
    }

    public static void guardarUsuario() {
        Usuario nuevoUsuario = pedirDatosUsuario();
        Json.guardarUsuarioJson(nuevoUsuario, "usuario.json");
    }
    public static Usuario pedirDatosLogin() {
        System.out.print("Ingrese matrícula: ");
        String matricula = sc.nextLine();

        System.out.print("Ingrese contraseña: ");
        String contrasena = sc.nextLine();

        return new Usuario("", matricula, contrasena); // correo vacío porque no es necesario para login
    }
    public static Usuario iniciarSesion(String rutaArchivo) {
        Gson gson = new Gson();

        while (true) {
            Scanner sc = new Scanner(System.in);

            System.out.print("Ingrese matrícula: ");
            String matricula = sc.nextLine();

            System.out.print("Ingrese contraseña: ");
            String contrasena = sc.nextLine();

            try (FileReader reader = new FileReader(rutaArchivo)) {
                Type tipoLista = new TypeToken<List<Usuario>>() {}.getType();
                List<Usuario> listaUsuarios = gson.fromJson(reader, tipoLista);

                for (Usuario u : listaUsuarios) {
                    if (u.getMatricula().equals(matricula) && u.getContrasena().equals(contrasena)) {
                        System.out.println("✅ Inicio de sesión exitoso.");
                        return u;
                    }
                }

                System.out.println("Matrícula o contraseña incorrecta. Intente nuevamente.\n");

            } catch (IOException e) {
                throw new RuntimeException("Error al leer el archivo JSON: " + e.getMessage(), e);
            }
        }
    }
}
