package poo.examenpractico.persistencia;

import poo.examenpractico.excepciones.PersistenciaException;
import poo.examenpractico.modelo.Coordenada;
import poo.examenpractico.modelo.EstadoJuego;
import poo.examenpractico.modelo.Juego;
import poo.examenpractico.modelo.Jugador;
import poo.examenpractico.modelo.Tablero;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class GestorPartidaTest {

    private final GestorPartida gestor = new GestorPartida();

    @TempDir
    Path carpeta;

    @Test
    void guardarYCargarConservaElEstadoDeLaPartida() throws Exception {
        Juego original = new Juego(new Jugador("Ana"),
                Tablero.conMinasEn(4, 4, new Coordenada(0, 0), new Coordenada(3, 3)));
        original.descubrir(new Coordenada(1, 0)); // B1 tiene número: abre solo esa casilla
        original.marcar(new Coordenada(3, 3));    // marca una mina

        Path archivo = carpeta.resolve("partida.dat");
        gestor.guardar(original, archivo);
        Juego cargado = gestor.cargar(archivo);

        assertEquals("Ana", cargado.getJugador().getNombre());
        assertEquals(EstadoJuego.EN_CURSO, cargado.getEstado());
        assertEquals(original.getJugadas(), cargado.getJugadas());
        assertEquals(original.getMinasRestantes(), cargado.getMinasRestantes());
        for (int f = 0; f < 4; f++) {
            for (int c = 0; c < 4; c++) {
                assertEquals(original.getTablero().getSimbolo(f, c),
                        cargado.getTablero().getSimbolo(f, c), "Difiere en " + new Coordenada(f, c));
            }
        }
    }

    @Test
    void sePuedeContinuarJugandoLaPartidaCargada() throws Exception {
        Juego original = new Juego(new Jugador("Luis"),
                Tablero.conMinasEn(3, 3, new Coordenada(0, 0)));
        Path archivo = carpeta.resolve("continuar.dat");
        gestor.guardar(original, archivo);

        Juego cargado = gestor.cargar(archivo);
        cargado.descubrir(new Coordenada(2, 2));
        assertEquals(EstadoJuego.GANADO, cargado.getEstado());
        // La partida original no se ve afectada
        assertEquals(EstadoJuego.EN_CURSO, original.getEstado());
    }

    @Test
    void cargarUnArchivoInexistenteLanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> gestor.cargar(carpeta.resolve("no-existe.dat")));
    }

    @Test
    void cargarUnArchivoQueNoEsUnaPartidaLanzaExcepcion() throws Exception {
        Path basura = carpeta.resolve("basura.dat");
        Files.write(basura, "esto no es una partida".getBytes());
        assertThrows(PersistenciaException.class, () -> gestor.cargar(basura));
    }

    @Test
    void guardarEnUnaRutaInvalidaLanzaExcepcion() {
        Juego juego = new Juego(new Jugador("Eva"));
        Path rutaMala = carpeta.resolve("carpeta/que/no/existe/partida.dat");
        assertThrows(PersistenciaException.class, () -> gestor.guardar(juego, rutaMala));
    }
}
