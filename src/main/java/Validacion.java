import java.util.Scanner;
import java.util.ArrayList;

public class Validacion {
    private static final Scanner sc = new Scanner(System.in);
    static ArrayList<String> datos = new ArrayList<>(1);

    // Simulación de "base de datos"
    static {
        datos.add("Juan,j.alberto14@ufromail.cl,casa1243");
        datos.add("Ana,a.perez@ufromail.cl,clave123");
        datos.add("hola,hola,hola");
    }

    public static boolean validadorIniciarSesion() {
        boolean accesoPermitido = false;

        while (!accesoPermitido) {
            System.out.println("====== Iniciar Sesión ======");

            System.out.print("Ingrese nombre o correo: ");
            String identificador = sc.nextLine().trim();  //* El trim sirve para eliminar los espacios vacíos *//

            System.out.print("Ingrese contraseña: ");
            String contrasena = sc.nextLine().trim();  //* El trim sirve para eliminar los espacios vacíos *//

            boolean usuarioExiste = validarNombreOCorreo(identificador);
            boolean claveCorrecta = validarContrasena(identificador, contrasena);

            if (usuarioExiste && claveCorrecta) {
                System.out.println("Inicio de sesión satisfactorio, bienvenido, " + identificador + "!");
                accesoPermitido = true;
                Menu.menu();
            } else {
                System.out.println("Usuario o contraseña inválidos. Intente nuevamente.");
            }
        }
        return accesoPermitido;
    }

    //* El trim sirve para eliminar los espacios vacíos *//

    private static boolean validarNombreOCorreo(String entrada) {
        for (String dato : datos) {
            String[] partes = dato.split(",");
            String nombre = partes[0].trim();  //* El trim sirve para eliminar los espacios vacíos *//
            String correo = partes[1].trim();  //* El trim sirve para eliminar los espacios vacíos *//

            if (entrada.equals(nombre) || entrada.equals(correo)) {
                return true;
            }
        }
        return false;
    }

    private static boolean validarContrasena(String entrada, String contrasena) {
        for (String dato : datos) {
            String[] partes = dato.split(",");
            String clave = partes[2].trim();  //* El trim sirve para eliminar los espacios vacíos *//

            if (clave.equals(contrasena)) {
                return true;
            }
        }
        return false;
    }
}
