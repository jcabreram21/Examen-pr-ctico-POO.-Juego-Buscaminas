package poo.examenpractico.controlador;

import java.io.IOException;

public class ServicioJuegoPrueba
        implements ServicioJuego, ServicioJuego.Partida {

    int nuevasPartidas;
    int partidasCargadas;
    int partidasGuardadas;
    int casillasDescubiertas;
    int casillasMarcadas;

    int ultimaFila;
    int ultimaColumna;

    boolean falloCarga;
    boolean rechazarDescubrimiento;

    char valorCasilla = 'V';

    Estado estado = Estado.EN_CURSO;
    Estado estadoDespuesDeDescubrir = Estado.EN_CURSO;

    @Override
    public Partida nuevaPartida() {
        nuevasPartidas++;
        return this;
    }

    @Override
    public Partida cargarPartida() throws IOException {

        if (falloCarga) {
            throw new IOException("No existe una partida guardada.");
        }

        partidasCargadas++;
        return this;
    }

    @Override
    public void guardarPartida(Partida partida) {
        partidasGuardadas++;
    }

    @Override
    public char[][] obtenerTableroVisible() {
        return new char[10][10];
    }

    @Override
    public char descubrir(int fila, int columna)
            throws AccionJuegoException {

        if (rechazarDescubrimiento) {
            throw new AccionJuegoException("La casilla ya está descubierta.");
        }

        casillasDescubiertas++;

        ultimaFila = fila;
        ultimaColumna = columna;

        estado = estadoDespuesDeDescubrir;

        return valorCasilla;
    }

    @Override
    public void marcarDesmarcar(int fila, int columna) {

        casillasMarcadas++;

        ultimaFila = fila;
        ultimaColumna = columna;
    }

    @Override
    public Estado obtenerEstado() {
        return estado;
    }
}