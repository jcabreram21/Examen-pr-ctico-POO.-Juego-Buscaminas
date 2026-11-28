package poo.examenpractico.controlador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import poo.examenpractico.modelo.Jugador;
import poo.examenpractico.modelo.Coordenada;
import poo.examenpractico.modelo.EstadoJuego;

public class BuscaminasControladorTest {

    //Método auxiliar para ejecutar el controlador
    private void ejecutar(
            VistaPrueba vista, ServicioJuegoPrueba servicio) {

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();
    }

    @Test
    public void testIniciar() {

        VistaPrueba vista = new VistaPrueba("0");
        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals(1, vista.getVecesMostrado());
    }

    @Test
    public void testSolicitarOpcion() {

        VistaPrueba vista = new VistaPrueba("0");
        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals(1, vista.getVecesSolicitadaEntrada());
    }

    @Test
    public void testOpcionInvalida() {

        VistaPrueba vista = new VistaPrueba("9", "0");
        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals(1, vista.getVecesError());
        assertEquals(2, vista.getVecesMostrado());
    }

    @Test
    public void testNuevaPartida() {

        VistaPrueba vista =
                new VistaPrueba("1", "Pedro", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals(1, servicio.nuevasPartidas);
        assertEquals(1, vista.getVecesMenuJuego());
        assertEquals(2, vista.getVecesMostrado());

        assertEquals("Pedro",
                servicio.juego.getJugador().getNombre());

        assertNull(vista.getResultado());
    }

    @Test
    public void testNombreInvalido() {

        VistaPrueba vista =
                new VistaPrueba("1", "", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals(1, vista.getVecesError());
        assertEquals(0, servicio.nuevasPartidas);
    }

    @Test
    public void testCargarPartida() {

        VistaPrueba vista =
                new VistaPrueba("2", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        servicio.partidaGuardada =
                ServicioJuegoPrueba.crearJuego(
                        new Jugador("Pedro"));

        ejecutar(vista, servicio);

        assertEquals(1, servicio.partidasCargadas);
        assertEquals(1, vista.getVecesMenuJuego());

        assertEquals("Pedro",
                servicio.juego.getJugador().getNombre());
    }

    @Test
    public void testDescubrirCasilla() {

        VistaPrueba vista = new VistaPrueba("1", "Pedro", "1", "B5", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals(1, servicio.juego.getJugadas());

        assertEquals('3',
                servicio.juego.getTablero().getSimbolo(1, 4));

        assertEquals(2, vista.getVecesTablero());
    }

    @Test
    public void testMarcarCasilla() {

        VistaPrueba vista = new VistaPrueba("1", "Pedro", "2", "B3", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals('M',
                servicio.juego.getTablero().getSimbolo(1, 2));

        assertEquals(9, servicio.juego.getMinasRestantes());
    }

    @Test
    public void testDesmarcarCasilla() {

        VistaPrueba vista = new VistaPrueba("1", "Pedro", "2", "B3",
                							"2", "B3", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals('#', servicio.juego.getTablero().getSimbolo(1, 2));

        assertEquals(10, servicio.juego.getMinasRestantes());

        assertTrue(vista.contieneMensaje("Casilla B3 desmarcada."));
    }

    @Test
    public void testGuardarPartida() {

        VistaPrueba vista =
                new VistaPrueba("1", "Pedro", "3", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals(1, servicio.partidasGuardadas);
        assertNotNull(servicio.partidaGuardada);
    }

    @Test
    public void testVictoria() {

        VistaPrueba vista = new VistaPrueba("1", "Pedro", "1", "J10", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals(EstadoJuego.GANADO,
                servicio.juego.getEstado());

        assertEquals(Boolean.TRUE, vista.getResultado());

        assertEquals(1,
                servicio.juego.getJugador().getPartidasGanadas());
    }

    @Test
    public void testDerrota() {

        VistaPrueba vista = new VistaPrueba("1", "Pedro", "1", "A5", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals(EstadoJuego.PERDIDO,
                servicio.juego.getEstado());

        assertEquals(Boolean.FALSE, vista.getResultado());

        assertEquals(1,
                servicio.juego.getJugador().getPartidasPerdidas());
    }

    @Test
    public void testCoordenadaFueraDeRango() {

        VistaPrueba vista = new VistaPrueba("1", "Pedro", "1", "K5", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals(1, vista.getVecesError());
        assertEquals(0, servicio.juego.getJugadas());
    }

    @Test
    public void testFormatoInvalido() {

        VistaPrueba vista = new VistaPrueba("1", "Pedro", "1", "HOLA", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals(1, vista.getVecesError());
        assertEquals(0, servicio.juego.getJugadas());
    }

    @Test
    public void testCasillaYaDescubierta() {

        VistaPrueba vista = new VistaPrueba("1", "Pedro", "1", "B5",
                							"1", "B5", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals(1, vista.getVecesError());
        assertEquals(1, servicio.juego.getJugadas());
    }

    @Test
    public void testCasillaMarcada() {

        VistaPrueba vista = new VistaPrueba("1", "Pedro", "2", "B5",
                							"1", "B5", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        ejecutar(vista, servicio);

        assertEquals(1, vista.getVecesError());
        assertEquals(0, servicio.juego.getJugadas());

        assertEquals('M',
                servicio.juego.getTablero().getSimbolo(1, 4));
    }

    @Test
    public void testErrorAlCargarPartida() {

        VistaPrueba vista = new VistaPrueba("2", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        servicio.falloCarga = true;

        ejecutar(vista, servicio);

        assertEquals(1, vista.getVecesError());
        assertEquals(0, vista.getVecesMenuJuego());
    }

    @Test
    public void testErrorAlGuardarPartida() {

        VistaPrueba vista = new VistaPrueba("1", "Pedro", "3", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        servicio.falloGuardado = true;

        ejecutar(vista, servicio);

        assertEquals(1, vista.getVecesError());
        assertEquals(0, servicio.partidasGuardadas);
    }
}