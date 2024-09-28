package Calculadora;

import javax.swing.JTextArea;

public class MapaKarnaugh {

    public void generarMapaKarnaugh(boolean[][] tablaVerdad, int numVariables, JTextArea outputArea) {
        switch (numVariables) {
            case 2:
                generarMapa2Variables(tablaVerdad, outputArea);
                break;
            case 3:
                generarMapa3Variables(tablaVerdad, outputArea);
                break;
            case 4:
                generarMapa4Variables(tablaVerdad, outputArea);
                break;
            default:
                outputArea.append("Número de variables no soportado.\n");
                break;
        }
    }

    private void generarMapa2Variables(boolean[][] tablaVerdad, JTextArea outputArea) {
        outputArea.append("\nMapa de Karnaugh para 2 Variables\n");
        String[] encabezados = {"0", "1"};
        String[] filas = {"0", "1"};

        outputArea.append("  ");
        for (String col : encabezados) {
            outputArea.append(col + " ");
        }
        outputArea.append("\n");

        for (int i = 0; i < 2; i++) {
            outputArea.append(filas[i] + " ");
            for (int j = 0; j < 2; j++) {
                int fila = i * 2 + j;
                outputArea.append((tablaVerdad[fila][2] ? "1" : "0") + " ");
            }
            outputArea.append("\n");
        }

        agruparMinitermos(tablaVerdad, 2, outputArea);
    }

    private void generarMapa3Variables(boolean[][] tablaVerdad, JTextArea outputArea) {
        outputArea.append("\nMapa de Karnaugh para 3 Variables\n");
        String[] encabezados = {"00", "01", "11", "10"};
        String[] filas = {"0", "1"};

        outputArea.append("   ");
        for (String col : encabezados) {
            outputArea.append(col + " ");
        }
        outputArea.append("\n");

        for (int i = 0; i < 2; i++) {
            outputArea.append(filas[i] + " ");
            for (int j = 0; j < 4; j++) {
                int fila = i * 4 + j;
                outputArea.append((tablaVerdad[fila][3] ? "1" : "0") + " ");
            }
            outputArea.append("\n");
        }

        agruparMinitermos(tablaVerdad, 3, outputArea);
    }

    private void generarMapa4Variables(boolean[][] tablaVerdad, JTextArea outputArea) {
        outputArea.append("\nMapa de Karnaugh para 4 Variables\n");
        String[] encabezados = {"00", "01", "11", "10"};
        String[] filas = {"00", "01", "11", "10"};

        outputArea.append("    ");
        for (String col : encabezados) {
            outputArea.append(col + " ");
        }
        outputArea.append("\n");

        for (int i = 0; i < 4; i++) {
            outputArea.append(filas[i] + " ");
            for (int j = 0; j < 4; j++) {
                int fila = i * 4 + j;
                outputArea.append((tablaVerdad[fila][4] ? "1" : "0") + " ");
            }
            outputArea.append("\n");
        }

        agruparMinitermos(tablaVerdad, 4, outputArea);
    }

    private void agruparMinitermos(boolean[][] tablaVerdad, int numVariables, JTextArea outputArea) {
        outputArea.append("\nAgrupaciones de minitérminos:\n");

        boolean[][] agrupado = new boolean[16][16];

        for (int i = 0; i < tablaVerdad.length; i++) {
            if (tablaVerdad[i][numVariables]) {
                outputArea.append("Minitérmino " + getMinterm(i, numVariables) + " está en la tabla.\n");
                agrupado[i / 4][i % 4] = true;
            }
        }

        buscarAgrupaciones(tablaVerdad, 2, agrupado, numVariables, outputArea);
        buscarAgrupaciones(tablaVerdad, 4, agrupado, numVariables, outputArea);
        buscarAgrupaciones(tablaVerdad, 8, agrupado, numVariables, outputArea);
    }

    private void buscarAgrupaciones(boolean[][] tablaVerdad, int tamaño, boolean[][] agrupado, int numVariables, JTextArea outputArea) {
        int n = tablaVerdad.length;
        int filas = (int) Math.sqrt(n);
        int columnas = filas;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (verificarAgrupacion(tablaVerdad, tamaño, i, j, agrupado, numVariables)) {
                    outputArea.append("Agrupación de tamaño " + tamaño + " encontrada en (" + i + ", " + j + ")\n");
                }
            }
        }
    }

    private boolean verificarAgrupacion(boolean[][] tablaVerdad, int tamaño, int fila, int columna, boolean[][] agrupado, int numVariables) {
        if (tamaño == 4) {
            return fila * 4 + columna < tablaVerdad.length &&
                    tablaVerdad[fila * 4 + columna][numVariables] &&
                    tablaVerdad[fila * 4 + (columna + 1) % 4][numVariables] &&
                    tablaVerdad[((fila + 1) % 4) * 4 + columna][numVariables] &&
                    tablaVerdad[((fila + 1) % 4) * 4 + (columna + 1) % 4][numVariables];
        }

        return false;
    }

    private String getMinterm(int index, int numVariables) {
        String binary = String.format("%" + numVariables + "s", Integer.toBinaryString(index)).replace(' ', '0');
        return binary;
    }
}