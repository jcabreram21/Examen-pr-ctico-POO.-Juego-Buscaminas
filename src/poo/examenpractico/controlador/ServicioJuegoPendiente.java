package poo.examenpractico.controlador;

import java.io.IOException;

public class ServicioJuegoPendiente
        implements ServicioJuego {

    @Override
    public Partida nuevaPartida()
            throws AccionJuegoException {

        throw new AccionJuegoException("No integrado.");
    }

    @Override
    public Partida cargarPartida() throws IOException {

        throw new IOException("No integrado.");
    }

    @Override
    public void guardarPartida(Partida partida)
            throws IOException {

        throw new IOException("No integrado.");
    }
}