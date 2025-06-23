package datos;

public enum Rol {
    ESTUDIANTE,
    PROFESOR,
    ADMINISTRADOR;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}