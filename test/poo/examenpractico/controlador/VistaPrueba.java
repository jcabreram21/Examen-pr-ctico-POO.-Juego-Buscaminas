package poo.examenpractico.controlador;

import poo.examenpractico.vista.VistaConsola;

public class VistaPrueba extends VistaConsola {

    private int vecesMostrado = 0;
    private int vecesMenuJuego = 0;
    private int vecesTablero = 0;
    private int vecesSolicitadaEntrada = 0;
    private int vecesError = 0;

    private Boolean resultado;

    private final String[] entradas;
    private int indice = 0;

    public VistaPrueba(String... entradas) {

        this.entradas = entradas.length == 0
                ? new String[]{"0"} : entradas;
    }

    @Override
    public void mostrarMenuPrincipal() {
        vecesMostrado++;
    }

    @Override
    public void mostrarMenuJuego() {
        vecesMenuJuego++;
    }

    @Override
    public void mostrarTablero(char[][] celdas) {
        vecesTablero++;
    }

    @Override
    public String pedirEntrada(String mensaje) {

        vecesSolicitadaEntrada++;

        if (indice >= entradas.length) {
            throw new AssertionError("No hay más entradas de prueba.");
        }

        return entradas[indice++];
    }

    @Override
    public void mostrarError(String mensaje) {
        vecesError++;
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        // Evitar mensajes durante las pruebas.
    }

    @Override
    public void mostrarResultadoPartida(boolean gano) {
        resultado = gano;
    }

    public int getVecesMostrado() {
        return vecesMostrado;
    }

    public int getVecesMenuJuego() {
        return vecesMenuJuego;
    }

    public int getVecesTablero() {
        return vecesTablero;
    }

    public int getVecesSolicitadaEntrada() {
        return vecesSolicitadaEntrada;
    }

    public int getVecesError() {
        return vecesError;
    }

    public Boolean getResultado() {
        return resultado;
    }
}