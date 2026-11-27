package poo.examenpractico.modelo;

/**
 * Estados posibles de una partida.
 */
public enum EstadoJuego {
    /** La partida sigue; el jugador puede descubrir y marcar casillas. */
    EN_CURSO,
    /** El jugador descubrió todas las casillas sin mina. */
    GANADO,
    /** El jugador descubrió una mina. */
    PERDIDO
}
