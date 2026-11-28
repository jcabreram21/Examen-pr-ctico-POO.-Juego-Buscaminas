package poo.examenpractico.controlador;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Files;

import poo.examenpractico.modelo.Juego;
import poo.examenpractico.modelo.Jugador;
import poo.examenpractico.modelo.Coordenada;
import poo.examenpractico.modelo.EstadoJuego;
import poo.examenpractico.persistencia.GestorPartida;
import poo.examenpractico.excepciones.PersistenciaException;

public class ServicioJuegoRealTest {

    @TempDir
    Path directorio;

    @Test
    public void testNuevaPartidaReal() {

        ServicioJuego servicio = new ServicioJuegoReal(
                new GestorPartida(),
                directorio.resolve("partida.dat"));

        Juego juego = servicio.nuevaPartida(
                new Jugador("Pedro"));

        assertEquals(10, juego.getTablero().getFilas());
        assertEquals(10, juego.getTablero().getColumnas());
        assertEquals(10, juego.getTablero().getTotalMinas());

        assertEquals(EstadoJuego.EN_CURSO,
                juego.getEstado());
    }

    @Test
    public void testGuardarYCargarPartida() throws Exception {

        Path archivo = directorio.resolve("partida.dat");

        ServicioJuego servicio = new ServicioJuegoReal(
                new GestorPartida(), archivo);

        Juego juego = servicio.nuevaPartida(
                new Jugador("Pedro"));

        juego.marcar(Coordenada.desdeTexto("B5"));

        servicio.guardarPartida(juego);

        assertTrue(Files.exists(archivo));

        Juego recuperado = servicio.cargarPartida();

        assertNotSame(juego, recuperado);

        assertEquals("Pedro",
                recuperado.getJugador().getNombre());

        assertEquals('M',
                recuperado.getTablero().getSimbolo(1, 4));

        assertEquals(9, recuperado.getMinasRestantes());

        assertEquals(EstadoJuego.EN_CURSO,
                recuperado.getEstado());
    }

    @Test
    public void testCargarArchivoInexistente() {

        ServicioJuego servicio = new ServicioJuegoReal(
                new GestorPartida(),
                directorio.resolve("inexistente.dat"));

        assertThrows(
                PersistenciaException.class,
                () -> servicio.cargarPartida());
    }
}