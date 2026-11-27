package poo.examenpractico.modelo;

import poo.examenpractico.excepciones.CasillaMarcadaException;
import poo.examenpractico.excepciones.CasillaYaDescubiertaException;
import poo.examenpractico.excepciones.CoordenadaInvalidaException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Tablero del Buscaminas: una matriz de {@link Casilla} con minas colocadas
 * al azar.
 * <p>
 * Por defecto es de 10x10 con 10 minas. Las filas se nombran con letras
 * (A, B, C...) y las columnas con números (1, 2, 3...), de modo que en el
 * tablero por defecto las coordenadas van de A1 a J10.
 * </p>
 * <p>
 * Al crearse, el tablero decide dónde van las minas, cuenta las minas
 * vecinas de cada casilla y crea el tipo de casilla correspondiente
 * ({@link CasillaMina}, {@link CasillaVacia} o {@link CasillaNumerica}).
 * </p>
 */
public class Tablero implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Filas del tablero por defecto. */
    public static final int FILAS_POR_DEFECTO = 10;
    /** Columnas del tablero por defecto. */
    public static final int COLUMNAS_POR_DEFECTO = 10;
    /** Minas del tablero por defecto. */
    public static final int MINAS_POR_DEFECTO = 10;
    /** Máximo de filas permitido (una por letra del abecedario). */
    public static final int MAX_FILAS = 26;
    /** Máximo de columnas permitido (la coordenada admite hasta dos dígitos). */
    public static final int MAX_COLUMNAS = 99;

    private final int filas;
    private final int columnas;
    private final int totalMinas;
    private final Casilla[][] casillas;

    /** Crea un tablero de 10x10 con 10 minas en posiciones aleatorias. */
    public Tablero() {
        this(FILAS_POR_DEFECTO, COLUMNAS_POR_DEFECTO, MINAS_POR_DEFECTO);
    }

    /**
     * Crea un tablero con minas en posiciones aleatorias.
     *
     * @param filas          número de filas (1 a 26)
     * @param columnas       número de columnas (1 a 99)
     * @param cantidadMinas  cantidad de minas (al menos 1 y menos que el total de casillas)
     * @throws IllegalArgumentException si alguno de los valores no es válido
     */
    public Tablero(int filas, int columnas, int cantidadMinas) {
        this(filas, columnas, cantidadMinas, new Random());
    }

    /**
     * Crea un tablero usando un generador de números aleatorios dado.
     * Con la misma semilla se obtiene siempre el mismo tablero, lo que
     * permite hacer pruebas repetibles.
     *
     * @param filas          número de filas (1 a 26)
     * @param columnas       número de columnas (1 a 99)
     * @param cantidadMinas  cantidad de minas
     * @param random         generador de números aleatorios
     * @throws IllegalArgumentException si alguno de los valores no es válido
     */
    public Tablero(int filas, int columnas, int cantidadMinas, Random random) {
        this(filas, columnas, elegirPosicionesDeMinas(filas, columnas, cantidadMinas, random));
    }

    private Tablero(int filas, int columnas, List<Coordenada> minas) {
        validarDimensiones(filas, columnas, minas.size());
        this.filas = filas;
        this.columnas = columnas;
        this.totalMinas = minas.size();
        this.casillas = construirCasillas(minas);
    }

    /**
     * Crea un tablero con las minas en posiciones exactas. Sirve para
     * pruebas, porque permite conocer de antemano dónde está cada mina.
     *
     * @param filas    número de filas
     * @param columnas número de columnas
     * @param minas    posiciones de las minas (sin repetir y dentro del tablero)
     * @return el tablero creado
     * @throws IllegalArgumentException si alguna mina está fuera del tablero,
     *                                  está repetida o las dimensiones no son válidas
     */
    public static Tablero conMinasEn(int filas, int columnas, Coordenada... minas) {
        List<Coordenada> lista = new ArrayList<>();
        for (Coordenada mina : minas) {
            if (mina.getFila() >= filas || mina.getColumna() >= columnas) {
                throw new IllegalArgumentException("La mina " + mina + " está fuera del tablero.");
            }
            if (lista.contains(mina)) {
                throw new IllegalArgumentException("La mina " + mina + " está repetida.");
            }
            lista.add(mina);
        }
        return new Tablero(filas, columnas, lista);
    }

    // ------------------------------------------------------------------
    // Construcción
    // ------------------------------------------------------------------

    private static void validarDimensiones(int filas, int columnas, int minas) {
        if (filas < 1 || filas > MAX_FILAS) {
            throw new IllegalArgumentException("Las filas deben estar entre 1 y " + MAX_FILAS + ".");
        }
        if (columnas < 1 || columnas > MAX_COLUMNAS) {
            throw new IllegalArgumentException("Las columnas deben estar entre 1 y " + MAX_COLUMNAS + ".");
        }
        if (minas < 1 || minas >= filas * columnas) {
            throw new IllegalArgumentException(
                    "La cantidad de minas debe ser al menos 1 y menor que el total de casillas.");
        }
    }

    /** Sortea las posiciones de las minas: baraja todas las posiciones y toma las primeras. */
    private static List<Coordenada> elegirPosicionesDeMinas(int filas, int columnas, int cantidad, Random random) {
        validarDimensiones(filas, columnas, cantidad);
        List<Coordenada> todas = new ArrayList<>();
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                todas.add(new Coordenada(f, c));
            }
        }
        Collections.shuffle(todas, random);
        return new ArrayList<>(todas.subList(0, cantidad));
    }

    /** Crea la matriz de casillas ya con el tipo correcto en cada posición. */
    private Casilla[][] construirCasillas(List<Coordenada> minas) {
        boolean[][] hayMina = new boolean[filas][columnas];
        for (Coordenada mina : minas) {
            hayMina[mina.getFila()][mina.getColumna()] = true;
        }

        Casilla[][] resultado = new Casilla[filas][columnas];
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                if (hayMina[f][c]) {
                    resultado[f][c] = new CasillaMina();
                } else {
                    int vecinasConMina = contarMinasVecinas(hayMina, f, c);
                    resultado[f][c] = (vecinasConMina == 0)
                            ? new CasillaVacia()
                            : new CasillaNumerica(vecinasConMina);
                }
            }
        }
        return resultado;
    }

    private int contarMinasVecinas(boolean[][] hayMina, int fila, int columna) {
        int cantidad = 0;
        for (Coordenada vecina : vecinos(fila, columna)) {
            if (hayMina[vecina.getFila()][vecina.getColumna()]) {
                cantidad++;
            }
        }
        return cantidad;
    }

    /** Devuelve las casillas que rodean a una posición (hasta 8, con diagonales). */
    private List<Coordenada> vecinos(int fila, int columna) {
        List<Coordenada> lista = new ArrayList<>();
        for (int df = -1; df <= 1; df++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (df == 0 && dc == 0) {
                    continue; // la casilla misma no es vecina de sí misma
                }
                int f = fila + df;
                int c = columna + dc;
                if (f >= 0 && f < filas && c >= 0 && c < columnas) {
                    lista.add(new Coordenada(f, c));
                }
            }
        }
        return lista;
    }

    // ------------------------------------------------------------------
    // Acciones del jugador
    // ------------------------------------------------------------------

    /**
     * Descubre la casilla indicada. Si no tiene minas alrededor, descubre
     * automáticamente a sus vecinas, y así sucesivamente.
     *
     * @param coordenada posición a descubrir
     * @return {@code true} si la casilla descubierta era una mina
     * @throws CoordenadaInvalidaException   si la posición no existe en el tablero
     * @throws CasillaYaDescubiertaException si la casilla ya estaba descubierta
     * @throws CasillaMarcadaException       si la casilla tiene una marca
     */
    public boolean descubrir(Coordenada coordenada)
            throws CoordenadaInvalidaException, CasillaYaDescubiertaException, CasillaMarcadaException {
        Casilla casilla = getCasilla(coordenada);
        casilla.descubrir();
        if (casilla.esMina()) {
            return true;
        }
        if (casilla.seExpande()) {
            expandir(coordenada);
        }
        return false;
    }

    /**
     * Abre las vecinas de una casilla vacía y repite el proceso con cada
     * vecina que también quede vacía (recursividad). Se detiene al llegar a
     * casillas con número, y no toca las casillas ya descubiertas ni las marcadas.
     */
    private void expandir(Coordenada origen) {
        for (Coordenada v : vecinos(origen.getFila(), origen.getColumna())) {
            Casilla vecina = casillas[v.getFila()][v.getColumna()];
            if (!vecina.isDescubierta() && !vecina.isMarcada()) {
                vecina.revelar();
                if (vecina.seExpande()) {
                    expandir(v);
                }
            }
        }
    }

    /**
     * Pone o quita la marca de una casilla.
     *
     * @param coordenada posición a marcar
     * @return {@code true} si la casilla quedó marcada, {@code false} si se le quitó la marca
     * @throws CoordenadaInvalidaException   si la posición no existe en el tablero
     * @throws CasillaYaDescubiertaException si la casilla ya estaba descubierta
     */
    public boolean marcar(Coordenada coordenada)
            throws CoordenadaInvalidaException, CasillaYaDescubiertaException {
        return getCasilla(coordenada).alternarMarca();
    }

    /** Descubre todas las casillas. Se usa cuando la partida termina. */
    public void revelarTodo() {
        for (Casilla[] fila : casillas) {
            for (Casilla casilla : fila) {
                casilla.revelar();
            }
        }
    }

    // ------------------------------------------------------------------
    // Consultas
    // ------------------------------------------------------------------

    /**
     * Obtiene la casilla de una posición.
     *
     * @param coordenada posición buscada
     * @return la casilla en esa posición
     * @throws CoordenadaInvalidaException si la posición está fuera del tablero
     */
    public Casilla getCasilla(Coordenada coordenada) throws CoordenadaInvalidaException {
        try {
            return casillas[coordenada.getFila()][coordenada.getColumna()];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CoordenadaInvalidaException(
                    "La coordenada " + coordenada + " está fuera del tablero (filas A-"
                            + (char) ('A' + filas - 1) + ", columnas 1-" + columnas + ").", e);
        }
    }

    /**
     * Devuelve el símbolo que debe dibujarse en una posición. Está pensado
     * para que la vista recorra el tablero con dos ciclos {@code for}.
     *
     * @param fila    índice de fila, desde 0
     * @param columna índice de columna, desde 0
     * @return el carácter que representa el estado actual de esa casilla
     * @throws ArrayIndexOutOfBoundsException si los índices están fuera del tablero
     */
    public char getSimbolo(int fila, int columna) {
        return casillas[fila][columna].getSimbolo();
    }

    /**
     * Devuelve el estado visible de todo el tablero como una matriz de
     * caracteres, lista para que la vista la dibuje. Es una copia: modificarla
     * no cambia el tablero.
     *
     * @return matriz de {@code filas x columnas} con el símbolo de cada casilla
     */
    public char[][] getSimbolos() {
        char[][] simbolos = new char[filas][columnas];
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                simbolos[f][c] = casillas[f][c].getSimbolo();
            }
        }
        return simbolos;
    }

    /**
     * Cuenta las casillas sin mina que ya fueron descubiertas.
     *
     * @return cantidad de casillas seguras descubiertas
     */
    public int contarDescubiertasSeguras() {
        int cantidad = 0;
        for (Casilla[] fila : casillas) {
            for (Casilla casilla : fila) {
                if (casilla.isDescubierta() && !casilla.esMina()) {
                    cantidad++;
                }
            }
        }
        return cantidad;
    }

    /**
     * Cuenta las casillas que tienen una marca puesta.
     *
     * @return cantidad de casillas marcadas
     */
    public int contarMarcadas() {
        int cantidad = 0;
        for (Casilla[] fila : casillas) {
            for (Casilla casilla : fila) {
                if (casilla.isMarcada()) {
                    cantidad++;
                }
            }
        }
        return cantidad;
    }

    /**
     * Indica si el jugador ya descubrió todas las casillas sin mina,
     * que es la condición para ganar.
     *
     * @return {@code true} si no queda ninguna casilla segura por descubrir
     */
    public boolean todasLasSegurasDescubiertas() {
        return contarDescubiertasSeguras() == getTotalSeguras();
    }

    /**
     * Devuelve la cantidad de filas.
     *
     * @return número de filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * Devuelve la cantidad de columnas.
     *
     * @return número de columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * Devuelve la cantidad de minas del tablero.
     *
     * @return total de minas
     */
    public int getTotalMinas() {
        return totalMinas;
    }

    /**
     * Devuelve la cantidad de casillas sin mina.
     *
     * @return total de casillas seguras
     */
    public int getTotalSeguras() {
        return filas * columnas - totalMinas;
    }
}
