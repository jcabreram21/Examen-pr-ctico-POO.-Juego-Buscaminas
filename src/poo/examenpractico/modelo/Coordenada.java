package poo.examenpractico.modelo;

import poo.examenpractico.excepciones.CoordenadaInvalidaException;

import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

/**
 * Posición de una casilla en el tablero.
 * <p>
 * El jugador escribe coordenadas como {@code A5} (letra = fila, número =
 * columna). Internamente se guardan como índices que empiezan en 0, así
 * {@code A5} es la fila 0 y la columna 4. La clase es inmutable: una vez
 * creada no cambia.
 * </p>
 */
public final class Coordenada implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int fila;
    private final int columna;

    /**
     * Crea una coordenada a partir de índices que empiezan en 0.
     *
     * @param fila    índice de fila (0 = A)
     * @param columna índice de columna (0 = columna 1)
     * @throws IllegalArgumentException si alguno de los índices es negativo
     */
    public Coordenada(int fila, int columna) {
        if (fila < 0 || columna < 0) {
            throw new IllegalArgumentException("Los índices no pueden ser negativos: (" + fila + ", " + columna + ")");
        }
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Convierte el texto escrito por el jugador (por ejemplo "A5" o "j10")
     * en una coordenada. Ignora espacios al inicio y al final y acepta
     * mayúsculas o minúsculas.
     * <p>
     * Solo valida el formato (una letra seguida de uno o dos dígitos, con
     * número mayor que cero). Si la posición existe en el tablero lo revisa
     * {@link Tablero}.
     * </p>
     *
     * @param texto lo que escribió el jugador
     * @return la coordenada equivalente
     * @throws CoordenadaInvalidaException si el texto es nulo o no tiene el formato esperado
     */
    public static Coordenada desdeTexto(String texto) throws CoordenadaInvalidaException {
        if (texto == null) {
            throw new CoordenadaInvalidaException("Debe ingresar una coordenada, por ejemplo A5.");
        }
        String limpio = texto.trim().toUpperCase(Locale.ROOT);
        if (!limpio.matches("[A-Z][0-9]{1,2}")) {
            throw new CoordenadaInvalidaException(
                    "Coordenada inválida: \"" + texto.trim() + "\". Use una letra y un número, por ejemplo A5.");
        }
        int numero = Integer.parseInt(limpio.substring(1));
        if (numero < 1) {
            throw new CoordenadaInvalidaException("El número de columna debe ser 1 o mayor.");
        }
        return new Coordenada(limpio.charAt(0) - 'A', numero - 1);
    }

    /**
     * Devuelve el índice de fila.
     *
     * @return índice de fila, empezando en 0
     */
    public int getFila() {
        return fila;
    }

    /**
     * Devuelve el índice de columna.
     *
     * @return índice de columna, empezando en 0
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Devuelve la coordenada como la escribe el jugador, por ejemplo "A5".
     *
     * @return texto con letra de fila y número de columna
     */
    @Override
    public String toString() {
        return String.valueOf((char) ('A' + fila)) + (columna + 1);
    }

    /** {@inheritDoc} Dos coordenadas son iguales si tienen la misma fila y columna. */
    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Coordenada)) {
            return false;
        }
        Coordenada c = (Coordenada) otro;
        return fila == c.fila && columna == c.columna;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hash(fila, columna);
    }
}
