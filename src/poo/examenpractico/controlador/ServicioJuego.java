package poo.examenpractico.controlador;

import java.io.IOException;

public interface ServicioJuego {

    Partida nuevaPartida() throws AccionJuegoException;

    Partida cargarPartida() throws IOException;

    void guardarPartida(Partida partida) throws IOException;

    public interface Partida {

        char[][] obtenerTableroVisible();

        char descubrir(int fila, int columna)
                throws AccionJuegoException;

        void marcarDesmarcar(int fila, int columna)
                throws AccionJuegoException;

        Estado obtenerEstado();
    }

    public enum Estado {
        EN_CURSO, GANADA, PERDIDA
    }

    public class AccionJuegoException extends Exception {

        public AccionJuegoException(String mensaje) {
            super(mensaje);
        }

        public AccionJuegoException(
                String mensaje, Throwable causa) {

            super(mensaje, causa);
        }
    }
}