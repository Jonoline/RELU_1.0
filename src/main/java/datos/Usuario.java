package datos;

public class Usuario {
    private final String matricula;
    private final String ufromail;
    private final String contrasena;

    public Usuario(String ufromail, String matricula, String contrasena) {
        this.ufromail = ufromail;
        this.matricula = matricula;
        this.contrasena = contrasena;
    }

    public String getUfromail() {
        return ufromail;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getContrasena() {
        return contrasena;
    }

}

