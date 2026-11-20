package poo.examenpractico.vista;

import java.util.Scanner;

public class VistaConsola {

    private final Scanner entrada;

    public VistaConsola() {
    
    this.entrada = new Scanner(System.in);
    
    }

    public void mostrarMenuPrincipal() {
        System.out.println("\n          ================== BUSCAMINAS ==================");
        System.out.println("1. Nueva partida");
        System.out.println("2. Cargar partida");
        System.out.println("0. Salir");
    }
    
    public void mostrarTablero(char[][] celdas) {
        System.out.println("\n    1  2  3  4  5  6  7  8  9  10");
        for (int fila = 0; fila < celdas.length; fila++) {
            char letraFila = (char) ('A' + fila);
            System.out.print(" " + letraFila + " ");
            for (int col = 0; col < celdas[fila].length; col++) {
                System.out.print(" " + celdas[fila][col] + " ");
            }
        System.out.println();
        }
    }

    
    public void mostrarMenuJuego() {
        System.out.println("\n       ========= OPCIONES DE JUEGO =========");
        System.out.println("1. Descubrir casilla");
        System.out.println("2. Marcar / desmarcar casilla");
        System.out.println("3. Guardar partida");
        System.out.println("0. Salir");
    }
    
    public String pedirCoordenada() {
        return pedirEntrada("Ingresa la coordenada (ej. A5): ");
    }
    
    public String pedirEntrada(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine().trim();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.out.println("Error: " + mensaje);
    }
    public void mostrarMinaEnCasilla(String coordenada) {
        mostrarMensaje("Boom!! Había una mina (" + 'X' + ") en la casilla " + coordenada + ".");
    }

    public void mostrarCasillaVacia(String coordenada) {
        mostrarMensaje("Casilla " + coordenada + " vacía (" + 'V' + "). Se revelaron espacios vacíos alrededor!.");
    }


    public void mostrarTodasDescubiertas() {
        mostrarMensaje("¡Has descubierto todas las casillas seguras del tablero!");
    }

    public void mostrarResultadoPartida(boolean gano) {
        if (gano) {
            mostrarTodasDescubiertas();
            mostrarMensaje("VICTORIA! Has ganado, completaste el tablero sin detonar minas");
        } else {
            mostrarMensaje("DERROTA! Perdiste, detonaste una mina...\n\n         FIN DEL JUEGO...");
        }
    }

   
    }
