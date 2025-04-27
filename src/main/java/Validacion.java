import java.util.Scanner;

public class Validacion {
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args){

    }
    public static String leerMatricula(){
        System.out.println("ingrese su matrícula");
        return sc.nextLine();
    }
    private static String leerContrasena(){
        System.out.println("ingrese su contraseña:");
        return sc.nextLine();
    }
    private static Boolean validarMatricula(){
        return true;
    }
    private static Boolean validarContrasena(){
        return true;
    }

}
