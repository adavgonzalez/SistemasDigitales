package Calculadora;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.mostrarMenu();
    }

    private void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        TablaDeVerdad tablaDeVerdad = new TablaDeVerdad();
        MapaKarnaugh mapaKarnaugh = new MapaKarnaugh();

        while (true) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Ingresar ecuación booleana");
            System.out.println("2. Guía de cómo ingresar ecuaciones");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    // Ingresar la ecuación
                    System.out.print("Ingrese el número de variables (1-4): ");
                    int numVariables = scanner.nextInt();
                    scanner.nextLine(); // Limpiar buffer
                    System.out.print("Ingrese la ecuación booleana (con variables a, b, c, d): ");
                    String expr = scanner.nextLine();

                    // Generar tabla de verdad
                    boolean[][] tabla = tablaDeVerdad.generarTablaDeVerdad(numVariables, expr);

                    // Generar mapa de Karnaugh si son 4 variables
                    if (numVariables == 4) {
                        mapaKarnaugh.generarMapaKarnaugh(tabla, numVariables);
                    }
                    break;
                case 2:
                    // Mostrar guía
                    mostrarGuia();
                    break;
                case 3:
                    // Salir
                    System.out.println("Saliendo del programa...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
                    break;
            }
        }
    }

    private void mostrarGuia() {
        System.out.println("\n--- Guía para ingresar ecuaciones booleanas ---");
        System.out.println("1. Use 'a', 'b', 'c', 'd' para las variables.");
        System.out.println("2. Operadores válidos:");
        System.out.println("   !  -> NOT");
        System.out.println("   &  -> AND");
        System.out.println("   |  -> OR");
        System.out.println("   ^  -> XOR");
        System.out.println("3. Use paréntesis para agrupar expresiones, ejemplo:");
        System.out.println("   !(a & b) | (c ^ d)");
    }
}

