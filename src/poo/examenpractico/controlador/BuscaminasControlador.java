package poo.examenpractico.controlador;

import java.io.IOException;
import java.util.InputMismatchException;

import poo.examenpractico.vista.VistaConsola;
import poo.examenpractico.controlador.ServicioJuego.Partida;
import poo.examenpractico.controlador.ServicioJuego.Estado;
import poo.examenpractico.controlador.ServicioJuego.AccionJuegoException;

public class BuscaminasControlador {


    private final VistaConsola vista;
    private final ServicioJuego servicio;

    
    public BuscaminasControlador(
            VistaConsola vista, ServicioJuego servicio) {

        this.vista = vista;
        this.servicio = servicio;
    }

    // Iniciar la aplicacion
    public void iniciar() {

        String opcion;

        do {

            vista.mostrarMenuPrincipal();

            opcion = vista.pedirEntrada("Seleccione una opción: ");

            try {

                switch (opcion) {

                    case "1":
                        nuevaPartida();
                        break;

                    case "2":
                        cargarPartida();
                        break;

                    case "0":
                        vista.mostrarMensaje(
                                "Gracias por jugar.");
                        break;

                    default:
                        vista.mostrarError(
                                "Opción inválida.");
                        break;
                }

            } catch (AccionJuegoException | IOException e) {

                vista.mostrarError(e.getMessage());
            }

        } while (!opcion.equals("0"));
    }

    //Crear una nueva partida
    private void nuevaPartida()
            throws AccionJuegoException {

        Partida partida = servicio.nuevaPartida();

        vista.mostrarMensaje("Nueva partida iniciada.");

        jugar(partida);
    }

    //Cargar una partida existente
    private void cargarPartida() throws IOException {

        Partida partida = servicio.cargarPartida();

        vista.mostrarMensaje("Partida cargada correctamente.");

        jugar(partida);
    }

    //Gestionar el ciclo de juego
    private void jugar(Partida partida) {

        mostrarTablero(partida);

        while (partida.obtenerEstado() == Estado.EN_CURSO) {

            vista.mostrarMenuJuego();

            String opcion = vista.pedirEntrada("Seleccione una opción: ");

            try {

                switch (opcion) {

                    case "1":
                        descubrirCasilla(partida);
                        break;

                    case "2":
                        marcarCasilla(partida);
                        break;

                    case "3":
                        guardarPartida(partida);
                        break;

                    case "0":
                        vista.mostrarMensaje("Regresando al menú principal.");
                        return;

                    default:
                        vista.mostrarError("Opción inválida.");
                        break;
                }

            } catch (InputMismatchException| ArrayIndexOutOfBoundsException| AccionJuegoException e) {

                vista.mostrarError(e.getMessage());

            } catch (IOException e) {

                vista.mostrarError("Error al guardar: " + e.getMessage());
            }

            mostrarTablero(partida);
        }

        mostrarResultado(partida);
    }

    //Descubrir una casilla
    private void descubrirCasilla(Partida partida)
            throws AccionJuegoException {

        String coordenada = vista.pedirCoordenada();

        int[] posicion =
                ConversorCoordenadas.convertir(coordenada);

        int fila = posicion[0];
        int columna = posicion[1];

        char valor = partida.descubrir(fila, columna);

        if (valor == 'X') {

            vista.mostrarMinaEnCasilla(coordenada);

        } else if (valor == 'V') {

            vista.mostrarCasillaVacia(coordenada);

        } else {

            vista.mostrarMensaje("Casilla " + coordenada + " descubierta: " + valor);
        }
    }

    //Marcar o desmarcar una casilla
    private void marcarCasilla(Partida partida)
            throws AccionJuegoException {

        String coordenada = vista.pedirCoordenada();

        int[] posicion =
                ConversorCoordenadas.convertir(coordenada);

        partida.marcarDesmarcar(
                posicion[0], posicion[1]);

        vista.mostrarMensaje("Marcado actualizado en " + coordenada);
    }

    //Guardar la partida
    private void guardarPartida(Partida partida)
            throws IOException {

        servicio.guardarPartida(partida);

        vista.mostrarMensaje("Partida guardada correctamente.");
    }

    //Mostrar tablero actualizado
    private void mostrarTablero(Partida partida) {

        vista.mostrarTablero(partida.obtenerTableroVisible());
    }

    //Mostrar el resultado final
    private void mostrarResultado(Partida partida) {

        Estado estado = partida.obtenerEstado();

        if (estado == Estado.GANADA) {

            vista.mostrarResultadoPartida(true);

        } else if (estado == Estado.PERDIDA) {

            vista.mostrarResultadoPartida(false);
        }
    }
}