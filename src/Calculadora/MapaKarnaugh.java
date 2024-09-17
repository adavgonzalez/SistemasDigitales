package Calculadora;

public class MapaKarnaugh {

    // Método para generar mapa de Karnaugh y agrupar minitérminos
    public void generarMapaKarnaugh(boolean[][] tablaVerdad, int numVariables) {
        switch (numVariables) {
            case 2:
                generarMapa2Variables(tablaVerdad);
                break;
            case 3:
                generarMapa3Variables(tablaVerdad);
                break;
            case 4:
                generarMapa4Variables(tablaVerdad);
                break;
            default:
                System.out.println("El mapa de Karnaugh solo se puede generar para 2, 3 o 4 variables.");
                break;
        }
    }

    // Método para generar y agrupar minitérminos para 4 variables
    private void generarMapa4Variables(boolean[][] tablaVerdad) {
        System.out.println("\nMapa de Karnaugh para 4 Variables");
        String[] encabezados = {"00", "01", "11", "10"};
        String[] filas = {"00", "01", "11", "10"};

        System.out.print("    ");
        for (String col : encabezados) {
            System.out.print(col + " ");
        }
        System.out.println();

        for (int i = 0; i < 4; i++) {
            System.out.print(filas[i] + " ");
            for (int j = 0; j < 4; j++) {
                int fila = i * 4 + j;
                System.out.print((tablaVerdad[fila][4] ? 1 : 0) + " ");
            }
            System.out.println();
        }

        // Agrupación de minitérminos
        agruparMinitermos(tablaVerdad);
    }

    // Método para agrupar minitérminos en el mapa de Karnaugh
    private void agruparMinitermos(boolean[][] tablaVerdad) {
        System.out.println("\nAgrupaciones de minitérminos:");

        boolean[][] agrupado = new boolean[16][16]; // Para verificar agrupaciones

        // Agrupaciones de 1
        for (int i = 0; i < tablaVerdad.length; i++) {
            if (tablaVerdad[i][4]) {
                System.out.println("Minitérmino " + getMinterm(i) + " está en la tabla.");
                agrupado[i / 4][i % 4] = true;
            }
        }

        // Agrupaciones de 2
        buscarAgrupaciones(tablaVerdad, 2, agrupado);

        // Agrupaciones de 4
        buscarAgrupaciones(tablaVerdad, 4, agrupado);

        // Agrupaciones de 8
        buscarAgrupaciones(tablaVerdad, 8, agrupado);
    }

    // Buscar agrupaciones de tamaño específico
    private void buscarAgrupaciones(boolean[][] tablaVerdad, int tamaño, boolean[][] agrupado) {
        int n = tablaVerdad.length;
        int filas = (int) Math.sqrt(n);
        int columnas = filas;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (verificarAgrupacion(tablaVerdad, tamaño, i, j, agrupado)) {
                    System.out.println("Agrupación de tamaño " + tamaño + " encontrada en (" + i + ", " + j + ")");
                }
            }
        }
    }

    // Verificar si se puede formar una agrupación del tamaño deseado
    private boolean verificarAgrupacion(boolean[][] tablaVerdad, int tamaño, int fila, int columna, boolean[][] agrupado) {
        // Aquí puedes agregar lógica para verificar agrupaciones específicas.
        // La lógica puede ser compleja y varía según cómo quieras agrupar los minitérminos.

        // Ejemplo básico de agrupación de 2x2 (para ilustración):
        if (tamaño == 4) {
            return tablaVerdad[fila * 4 + columna][4] &&
                    tablaVerdad[fila * 4 + (columna + 1) % 4][4] &&
                    tablaVerdad[((fila + 1) % 4) * 4 + columna][4] &&
                    tablaVerdad[((fila + 1) % 4) * 4 + (columna + 1) % 4][4];
        }

        return false;
    }

    // Convertir índice a minitérmino (ejemplo simple para ilustración)
    private String getMinterm(int index) {
        String binary = String.format("%4s", Integer.toBinaryString(index)).replace(' ', '0');
        return binary;
    }

    private void generarMapa2Variables(boolean[][] tablaVerdad) {
        // Similar implementación para 2 variables
    }

    private void generarMapa3Variables(boolean[][] tablaVerdad) {
        // Similar implementación para 3 variables
    }
}



