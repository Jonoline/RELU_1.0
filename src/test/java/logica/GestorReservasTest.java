package logica;

import datos.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GestorReservasTest {
    private GestorReservas gestor;
    private Usuario usuario;
    private Logia logia;
    ArrayList<Reserva> reservas;
    @BeforeEach
    void setUp() {
        reservas = new Json().cargarReservas();
        usuario = new Usuario("correo@ufromail.cl", "123456789", "clave", Rol.ESTUDIANTE);
        logia = new Logia("L1", 6,"P2");
        gestor = new GestorReservas(usuario);

    }

    @AfterEach
    void tearDown() {
        gestor.CancelarReserva(usuario);
        Json json = new Json();
        json.IngresarReservas(reservas);
    }

    @Test
    void testConstructorInicializaReservaUsuarioCorrectamente() {

        assertNull(gestor.getReservaUsuario());
    }

    @Test
    void testAgregarReservaValida() {
        LocalDateTime fechaFutura = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0);
        gestor.CancelarReserva(usuario);
        gestor.agregarReserva(usuario, logia, fechaFutura);

        Reserva reserva = gestor.getReservaUsuario();
        assertNotNull(reserva);
        assertEquals("123456789", reserva.getMatricula());
        assertEquals(logia, reserva.getLogia());
        assertEquals(fechaFutura, reserva.getFechaHora());
    }
    @Test
    void testAgregarReservaUsuarioYaTieneReserva() {
        // Primera reserva válida
        gestor.agregarReserva(usuario, logia, LocalDateTime.now().plusDays(1).withHour(10).withMinute(0));

        // Intentar reservar de nuevo
        assertThrows(IllegalArgumentException.class, () -> {
            gestor.agregarReserva(usuario, logia, LocalDateTime.now().plusDays(2).withHour(12));
        });
    }

    @Test
    void testAgregarReservaLogiaOcupada() {
        Usuario usuario1 = new Usuario("correo1@ufromail.cl", "123456778", "clave", Rol.ESTUDIANTE);
        Usuario usuario2 = new Usuario("correo2@ufromail.cl", "987654321", "clave", Rol.ESTUDIANTE);
        LocalDateTime fecha = LocalDateTime.now().plusDays(1).withHour(10);

        GestorReservas gestor1 = new GestorReservas(usuario1);
        gestor1.agregarReserva(usuario1, logia, fecha);
        assertThrows(IllegalArgumentException.class, () -> {
            gestor1.agregarReserva(usuario2, logia, fecha);
        });
        gestor1.CancelarReserva(usuario1);
    }

    @Test
    void testCancelarReservaExistente() {
        Usuario usuario = new Usuario("correo@ufromail.cl", "123456789", "clave", Rol.ESTUDIANTE);
        Logia logia = new Logia("L1", 6,"P2");
        GestorReservas gestor = new GestorReservas(usuario);
        LocalDateTime fecha = LocalDateTime.now().plusDays(1).withHour(9);

        gestor.agregarReserva(usuario, logia, fecha);

        boolean cancelada = gestor.CancelarReserva(usuario);
        assertTrue(cancelada);
        assertNull(gestor.getReservaUsuario());
    }

    @Test
    void testNoPermitirReservaSabadoODomingo() {
        Usuario usuario = new Usuario("correo@ufromail.cl", "123456789", "clave", Rol.ESTUDIANTE);
        Logia logia = new Logia("L1", 6 ,"P2");
        GestorReservas gestor = new GestorReservas(usuario);

        // Buscar próximo sábado
        LocalDateTime fecha = LocalDateTime.now().plusDays(1);
        while (fecha.getDayOfWeek().getValue() != 6) { // 6 = Saturday
            fecha = fecha.plusDays(1);
        }

        int dia = fecha.getDayOfMonth();
        String mes = String.valueOf(fecha.getMonthValue());

        assertThrows(IllegalArgumentException.class, () -> {
            gestor.obtenerBloquesDisponibles(logia, dia, mes);
        });
    }






}