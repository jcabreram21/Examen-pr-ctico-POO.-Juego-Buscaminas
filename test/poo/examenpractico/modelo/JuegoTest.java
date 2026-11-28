package poo.examenpractico.modelo;

import poo.examenpractico.excepciones.JuegoTerminadoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JuegoTest {

    private Jugador jugador;
    private Juego juego;

    /** Tablero 3x3 con una sola mina en A1. Las 8 casillas seguras son las demás. */
    @BeforeEach
    void preparar() {
        jugador = new Jugador("Ana");
        juego = new Juego(jugador, Tablero.conMinasEn(3, 3, new Coordenada(0, 0)));
    }

    @Test
    void laPartidaEmpiezaEnCurso() {
        assertEquals(EstadoJuego.EN_CURSO, juego.getEstado());
        assertFalse(juego.isTerminado());
        assertEquals(0, juego.getJugadas());
    }

    @Test
    void descubrirUnaMinaPierdeLaPartida() throws Exception {
        juego.descubrir(new Coordenada(0, 0));
        assertEquals(EstadoJuego.PERDIDO, juego.getEstado());
        assertTrue(juego.isTerminado());
        assertEquals(1, jugador.getPartidasPerdidas());
        assertEquals(0, jugador.getPartidasGanadas());
    }

    @Test
    void alPerderSeMuestraTodoElTablero() throws Exception {
        juego.descubrir(new Coordenada(0, 0));
        assertEquals('X', juego.getTablero().getSimbolo(0, 0));
        assertTrue(juego.getTablero().getCasilla(new Coordenada(2, 2)).isDescubierta());
    }

    @Test
    void descubrirTodasLasSegurasGanaLaPartida() throws Exception {
        // C3 es vacía: la cascada abre las 8 casillas seguras de una sola jugada
        juego.descubrir(new Coordenada(2, 2));
        assertEquals(EstadoJuego.GANADO, juego.getEstado());
        assertEquals(1, jugador.getPartidasGanadas());
        assertEquals(0, jugador.getPartidasPerdidas());
    }

    @Test
    void ganaSoloCuandoSeDescubrenTodasLasSeguras() throws Exception {
        // Tablero 1x3 con mina en el centro: B2 ... A1 y A3 son las dos seguras
        Juego pequeno = new Juego(new Jugador("Luis"), Tablero.conMinasEn(1, 3, new Coordenada(0, 1)));
        pequeno.descubrir(new Coordenada(0, 0));
        assertEquals(EstadoJuego.EN_CURSO, pequeno.getEstado());
        pequeno.descubrir(new Coordenada(0, 2));
        assertEquals(EstadoJuego.GANADO, pequeno.getEstado());
    }

    @Test
    void noSePuedeJugarDespuesDeTerminar() throws Exception {
        juego.descubrir(new Coordenada(0, 0));
        assertThrows(JuegoTerminadoException.class, () -> juego.descubrir(new Coordenada(1, 1)));
        assertThrows(JuegoTerminadoException.class, () -> juego.marcar(new Coordenada(1, 1)));
    }

    @Test
    void marcarActualizaLasMinasRestantes() throws Exception {
        assertEquals(1, juego.getMinasRestantes());
        juego.marcar(new Coordenada(0, 0));
        assertEquals(0, juego.getMinasRestantes());
        juego.marcar(new Coordenada(0, 0));
        assertEquals(1, juego.getMinasRestantes());
    }

    @Test
    void lasJugadasInvalidasNoSeCuentan() throws Exception {
        juego.descubrir(new Coordenada(2, 2)); // gana
        assertEquals(1, juego.getJugadas());
        assertThrows(JuegoTerminadoException.class, () -> juego.descubrir(new Coordenada(1, 1)));
        assertEquals(1, juego.getJugadas());
    }

    @Test
    void elJuegoPorDefectoUsaTablero10x10() {
        Juego nuevo = new Juego(new Jugador("Eva"));
        assertEquals(10, nuevo.getTablero().getFilas());
        assertEquals(10, nuevo.getTablero().getTotalMinas());
    }

    @Test
    void rechazaJugadorOTableroNulos() {
        assertThrows(IllegalArgumentException.class, () -> new Juego(null));
        assertThrows(IllegalArgumentException.class, () -> new Juego(new Jugador("X"), null));
    }

    @Test
    void jugadorRechazaNombreVacio() {
        assertThrows(IllegalArgumentException.class, () -> new Jugador("   "));
        assertThrows(IllegalArgumentException.class, () -> new Jugador(null));
        assertEquals("Ana", new Jugador("  Ana ").getNombre());
    }
}
