public class Usuario {
    private final String nombre;
    private final String matricula;
    private final String contrasena;

    public Usuario(String nombre, String matricula, String contrasena) {
        this.nombre = nombre;
        this.matricula = matricula;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getContrasena() {
        return contrasena;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", matricula='" + matricula + '\'' +
                '}';
    }
}

