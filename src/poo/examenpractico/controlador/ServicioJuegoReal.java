package poo.examenpractico.controlador;

import java.nio.file.Path;

import poo.examenpractico.modelo.Juego;
import poo.examenpractico.modelo.Jugador;
import poo.examenpractico.persistencia.GestorPartida;
import poo.examenpractico.excepciones.PersistenciaException;

public class ServicioJuegoReal implements ServicioJuego {

    private final GestorPartida gestor;
    private final Path archivo;

    
    public ServicioJuegoReal(GestorPartida gestor, Path archivo) {
        this.gestor = gestor;
        this.archivo = archivo;
    }

    // Crear una nueva partida
    @Override
    public Juego nuevaPartida(Jugador jugador) {
        return new Juego(jugador);
    }

    // Cargar partida
    @Override
    public Juego cargarPartida() throws PersistenciaException {
        return gestor.cargar(archivo);
    }

    // Guardar partida
    @Override
    public void guardarPartida(Juego juego)
            throws PersistenciaException {

        gestor.guardar(juego, archivo);
    }
}