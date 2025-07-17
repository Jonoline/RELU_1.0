package datos;

import java.util.Objects;

public class Usuario {
    private final String matricula;
    private final String ufromail;
    private final String contrasena;
    private final Rol rol;

    public Usuario(String ufromail, String matricula, String contrasena, Rol rol) {
        this.ufromail = ufromail;
        this.matricula = matricula;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public Usuario(String matricula, String contrasena) {
        this.ufromail = "";
        this.matricula = matricula;
        this.contrasena = contrasena;
        this.rol = Rol.ESTUDIANTE; // Por defecto es estudiante
    }

    public String getMatricula() {
        return matricula;
    }

    public Rol getRol() {
        return rol;
    }

    public String getUfromail() {return ufromail;}

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(matricula, usuario.matricula) &&
                Objects.equals(contrasena, usuario.contrasena);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula, contrasena);
    }
}