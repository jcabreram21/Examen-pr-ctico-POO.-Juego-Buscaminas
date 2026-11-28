package poo.examenpractico.controlador;

import poo.examenpractico.modelo.Juego;
import poo.examenpractico.modelo.Jugador;
import poo.examenpractico.excepciones.PersistenciaException;

public interface ServicioJuego {

    Juego nuevaPartida(Jugador jugador);

    Juego cargarPartida() throws PersistenciaException;

    void guardarPartida(Juego juego)
            throws PersistenciaException;
}