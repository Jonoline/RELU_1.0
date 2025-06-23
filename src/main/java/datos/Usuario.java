package datos;

import java.util.Objects;

public class Usuario {
    private final String matricula;
    private final String ufromail;
    private final String contrasena;

    public Usuario(String ufromail, String matricula, String contrasena) {
        this.ufromail = ufromail;
        this.matricula = matricula;
        this.contrasena = contrasena;
    }
    public Usuario(String matricula, String contrasena) {
        this.ufromail = "";
        this.matricula = matricula;
        this.contrasena = contrasena;
    }

    public String getUfromail() {
        return ufromail;
    }

    public String getMatricula() {
        return matricula;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(matricula, usuario.matricula) && Objects.equals(contrasena, usuario.contrasena);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula, contrasena);
    }
}

