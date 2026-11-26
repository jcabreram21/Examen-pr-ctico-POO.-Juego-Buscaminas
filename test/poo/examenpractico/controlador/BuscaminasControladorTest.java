package poo.examenpractico.controlador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BuscaminasControladorTest {

    @Test
    public void testIniciar() {

        VistaPrueba vista = new VistaPrueba();
        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();

        assertEquals(1, vista.getVecesMostrado());
    }

    @Test
    public void testSolicitarOpcion() {

        VistaPrueba vista = new VistaPrueba();
        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();

        assertEquals(1,
                vista.getVecesSolicitadaEntrada());
    }

    @Test
    public void testOpcionInvalida() {

        VistaPrueba vista = new VistaPrueba("9", "0");
        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();

        assertEquals(1, vista.getVecesError());
        assertEquals(2, vista.getVecesMostrado());
    }

    @Test
    public void testNuevaPartida() {

        VistaPrueba vista =
                new VistaPrueba("1", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();

        assertEquals(1, servicio.nuevasPartidas);
        assertEquals(1, vista.getVecesMenuJuego());
        assertEquals(2, vista.getVecesMostrado());
        assertNull(vista.getResultado());
    }

    @Test
    public void testCargarPartida() {

        VistaPrueba vista =
                new VistaPrueba("2", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();

        assertEquals(1, servicio.partidasCargadas);
        assertEquals(1, vista.getVecesMenuJuego());
    }

    @Test
    public void testDescubrirCasilla() {

        VistaPrueba vista =
                new VistaPrueba("1", "1", "A5", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();

        assertEquals(1, servicio.casillasDescubiertas);
        assertEquals(0, servicio.ultimaFila);
        assertEquals(4, servicio.ultimaColumna);
        assertEquals(2, vista.getVecesTablero());
    }

    @Test
    public void testMarcarCasilla() {

        VistaPrueba vista =
                new VistaPrueba("1", "2", "B3", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();

        assertEquals(1, servicio.casillasMarcadas);
        assertEquals(1, servicio.ultimaFila);
        assertEquals(2, servicio.ultimaColumna);
    }

    @Test
    public void testGuardarPartida() {

        VistaPrueba vista =
                new VistaPrueba("1", "3", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();

        assertEquals(1, servicio.partidasGuardadas);
    }

    @Test
    public void testVictoria() {

        VistaPrueba vista =
                new VistaPrueba("1", "1", "A5", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        servicio.estadoDespuesDeDescubrir =
                ServicioJuego.Estado.GANADA;

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();

        assertEquals(Boolean.TRUE, vista.getResultado());
    }

    @Test
    public void testDerrota() {

        VistaPrueba vista =
                new VistaPrueba("1", "1", "A5", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        servicio.valorCasilla = 'X';

        servicio.estadoDespuesDeDescubrir =
                ServicioJuego.Estado.PERDIDA;

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();

        assertEquals(Boolean.FALSE, vista.getResultado());
    }

    @Test
    public void testCoordenadaInvalida() {

        VistaPrueba vista =
                new VistaPrueba("1", "1", "K5", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();

        assertEquals(1, vista.getVecesError());
        assertEquals(0, servicio.casillasDescubiertas);
    }

    @Test
    public void testCasillaYaDescubierta() {

        VistaPrueba vista =
                new VistaPrueba("1", "1", "A5", "0", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        servicio.rechazarDescubrimiento = true;

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();

        assertEquals(1, vista.getVecesError());
        assertEquals(0, servicio.casillasDescubiertas);
    }

    @Test
    public void testErrorAlCargarPartida() {

        VistaPrueba vista = new VistaPrueba("2", "0");

        ServicioJuegoPrueba servicio =
                new ServicioJuegoPrueba();

        servicio.falloCarga = true;

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();

        assertEquals(1, vista.getVecesError());
        assertEquals(0, vista.getVecesMenuJuego());
    }
}