package poo.examenpractico;

import poo.examenpractico.vista.VistaConsola;
import poo.examenpractico.controlador.BuscaminasControlador;
import poo.examenpractico.controlador.ServicioJuego;
import poo.examenpractico.controlador.ServicioJuegoPendiente;

public class Main {

    public static void main(String[] args) {

        VistaConsola vista = new VistaConsola();

        ServicioJuego servicio =
                new ServicioJuegoPendiente();

        BuscaminasControlador controlador =
                new BuscaminasControlador(vista, servicio);

        controlador.iniciar();
    }
}