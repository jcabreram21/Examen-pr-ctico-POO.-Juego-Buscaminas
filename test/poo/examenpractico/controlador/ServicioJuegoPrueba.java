package poo.examenpractico.controlador;

import poo.examenpractico.modelo.Juego;
import poo.examenpractico.modelo.Jugador;
import poo.examenpractico.modelo.Tablero;
import poo.examenpractico.modelo.Coordenada;
import poo.examenpractico.excepciones.PersistenciaException;

public class ServicioJuegoPrueba implements ServicioJuego {

    int nuevasPartidas;
    int partidasCargadas;
    int partidasGuardadas;

    boolean falloCarga;
    boolean falloGuardado;

    Juego juego;
    Juego partidaGuardada;

    //Crear tablero conocido para las pruebas
    static Juego crearJuego(Jugador jugador) {

        Coordenada[] minas = new Coordenada[10];

        for (int columna = 0; columna < 10; columna++) {

            minas[columna] = new Coordenada(0, columna);
        }

        Tablero tablero = Tablero.conMinasEn(
                10, 10, minas);

        return new Juego(jugador, tablero);
    }

    @Override
    public Juego nuevaPartida(Jugador jugador) {

        nuevasPartidas++;

        juego = crearJuego(jugador);

        return juego;
    }

    @Override
    public Juego cargarPartida() throws PersistenciaException {

        if (falloCarga || partidaGuardada == null) {

            throw new PersistenciaException("No existe una partida guardada.");
        }

        partidasCargadas++;

        juego = partidaGuardada;

        return juego;
    }

    @Override
    public void guardarPartida(Juego juego)
            throws PersistenciaException {

        if (falloGuardado) {

            throw new PersistenciaException("No se pudo guardar la partida.");
        }

        partidasGuardadas++;

        partidaGuardada = juego;
    }
}