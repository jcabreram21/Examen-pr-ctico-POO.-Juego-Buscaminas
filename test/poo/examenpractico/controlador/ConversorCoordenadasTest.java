package poo.examenpractico.controlador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.InputMismatchException;

public class ConversorCoordenadasTest {

    @Test
    public void testCoordenadaValida() {

        assertArrayEquals(
                new int[]{0, 4},
                ConversorCoordenadas.convertir("A5"));
    }

    @Test
    public void testColumnaDiez() {

        assertArrayEquals(
                new int[]{9, 9},
                ConversorCoordenadas.convertir("J10"));
    }

    @Test
    public void testMinusculasYEspacios() {

        assertArrayEquals(
                new int[]{1, 2},
                ConversorCoordenadas.convertir(" b3 "));
    }

    @Test
    public void testFormatoInvalido() {

        assertThrows(
                InputMismatchException.class,
                () -> ConversorCoordenadas.convertir("HOLA"));
    }

    @Test
    public void testFilaFueraDeRango() {

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> ConversorCoordenadas.convertir("K5"));
    }

    @Test
    public void testColumnaFueraDeRango() {

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> ConversorCoordenadas.convertir("A11"));
    }
}