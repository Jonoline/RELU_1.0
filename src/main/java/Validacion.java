import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;

public class Validacion {
    private static final Scanner sc = new Scanner(System.in);
    static ArrayList<String> datos = new ArrayList<>(1);
    public static void main(String[] args){
    }
    public static String leerMatricula(){
        System.out.println("ingrese su matrícula: ");
        return sc.nextLine();
    }
    private static String leerContrasena(){
        System.out.println("ingrese su contraseña:");
        return sc.nextLine();
    }
    public static Boolean validarMatricula(String matricula){
        String regex = "^[0-9]{8}[0-9Kk][0-9]{2}";
        boolean dato = datos.contains(matricula);

        return dato && Pattern.matches(regex,matricula);
    }
    private static Boolean validarContrasena(){
        return true;
    }

}
