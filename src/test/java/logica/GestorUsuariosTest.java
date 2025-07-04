package logica;

import datos.Rol;
import datos.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GestorUsuariosTest {

    @Test
    public void testIniciarSesionUsuarioExistente() {
        GestorUsuarios gestor = new GestorUsuarios();
        Usuario usuario = new Usuario("test@ufromail.cl", "20203056789", "123456", Rol.ESTUDIANTE);
        gestor.getListaUsuarios().add(usuario);

        Usuario resultado = gestor.iniciarSesion(usuario);
        assertNotNull(resultado);
    }

    @Test
    public void testIniciarSesionUsuarioNoExistente() {
        GestorUsuarios gestor = new GestorUsuarios();
        Usuario usuario1 = new Usuario("test@ufromail.cl", "20255444823", "123456", Rol.ESTUDIANTE);
        Usuario usuario = new Usuario("test@ufromail.cl", "20203056789", "123456", Rol.ESTUDIANTE);
        gestor.getListaUsuarios().add(usuario1);

        Usuario resultado = gestor.iniciarSesion(usuario);
        assertNull(resultado);
    }

    @Test
    public void testBuscarUsuarioExistente() {
        GestorUsuarios gestor = new GestorUsuarios();
        Usuario usuario = new Usuario("test@ufromail.cl", "20203056789", "123456", Rol.ESTUDIANTE);
        gestor.getListaUsuarios().add(usuario);

        Usuario encontrado = gestor.buscarUsuario("20203056789");
        assertEquals(usuario.getMatricula(), encontrado.getMatricula(), "La matrícula debería coincidir");
    }

    @Test
    public void testBuscarUsuarioNoExistente() {
        GestorUsuarios gestor = new GestorUsuarios();
        Usuario usuario = new Usuario("test@ufromail.cl", "20203056789", "123456", Rol.ESTUDIANTE);
        gestor.getListaUsuarios().add(usuario);

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> gestor.buscarUsuario("21203056789"));
        assertEquals("No se ha encontrado el usuario en el sistema.", e.getMessage());
    }

    @Test
    public void testUsuarioEsAdmin(){
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        Usuario usuario = new Usuario("test@ufromail.cl","21342423225","123",Rol.ADMINISTRADOR);
        assertTrue(gestorUsuarios.usuarioEsAdmin(usuario));
    }

    @Test
    public void testUsuarioNoEsAdmin(){
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        Usuario usuario = new Usuario("test@ufromail.cl","21342423225","123",Rol.ESTUDIANTE);
        assertFalse(gestorUsuarios.usuarioEsAdmin(usuario));
    }
}