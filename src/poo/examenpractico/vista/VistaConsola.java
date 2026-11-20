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
    
   
    }
