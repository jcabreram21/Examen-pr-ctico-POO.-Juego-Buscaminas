package poo.examenpractico.controlador;

import java.util.InputMismatchException;
import java.util.Locale;

public class ConversorCoordenadas {

    private static final int TAMANO = 10;

    private ConversorCoordenadas() {
    }

    public static int[] convertir(String coordenada) {

        //Validar que exista una entrada
        if (coordenada == null) {
            throw new InputMismatchException("Debe ingresar una coordenada.");
        }

        //Normalizar coordenada
        coordenada = coordenada.trim()
                .toUpperCase(Locale.ROOT);

        //Validar formato
        if (!coordenada.matches("[A-Z][0-9]+")) {
            throw new InputMismatchException("Formato incorrecto. Ejemplo: A5.");
        }

        //Convertir fila
        int fila = coordenada.charAt(0) - 'A';

        //Convertir columna
        int columna;

        try {
            columna = Integer.parseInt(
                    coordenada.substring(1));

        } catch (NumberFormatException e) {
            throw new ArrayIndexOutOfBoundsException("Columna fuera del tablero.");
        }

        //Validar límites
        if (fila < 0 || fila >= TAMANO
                || columna < 1 || columna > TAMANO) {

            throw new ArrayIndexOutOfBoundsException("Coordenada fuera del tablero.");
        }

        return new int[]{fila, columna - 1};
    }
}