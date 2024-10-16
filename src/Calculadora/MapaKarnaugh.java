package Calculadora;

import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Clase para generar y simplificar mapas de Karnaugh.
 */
public class MapaKarnaugh {

    /**
     * Genera el mapa de Karnaugh basado en la tabla de verdad y el número de variables.
     * @param tablaVerdad La tabla de verdad.
     * @param numVariables El número de variables.
     * @param outputArea El área de texto donde se mostrará el resultado.
     */
    public void generarMapaKarnaugh(boolean[][] tablaVerdad, int numVariables, JTextArea outputArea) {
        switch (numVariables) {
            case 1:
                generarMapa1Variable(tablaVerdad, outputArea);
                break;
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

    private void generarMapa1Variable(boolean[][] tablaVerdad, JTextArea outputArea) {
        outputArea.append("\nMapa de Karnaugh para 1 Variable\n");
        String[] encabezados = {"0", "1"};

        outputArea.append("  ");
        for (String col : encabezados) {
            outputArea.append(col + " ");
        }
        outputArea.append("\n");

        outputArea.append("  ");
        for (int i = 0; i < 2; i++) {
            outputArea.append((tablaVerdad[i][1] ? "1" : "0") + " ");
        }
        outputArea.append("\n");

        mostrarMinitermosYMaxitermos(tablaVerdad, 1, outputArea);
        generarEcuacionSimplificadaKMap(tablaVerdad, 1, outputArea);
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
                int index = i * 2 + (j == 1 ? 2 : 1); // Swap columns 2 and 3
                outputArea.append((tablaVerdad[index][2] ? "1" : "0") + " ");
            }
            outputArea.append("\n");
        }

        mostrarMinitermosYMaxitermos(tablaVerdad, 2, outputArea);
        generarEcuacionSimplificadaKMap(tablaVerdad, 2, outputArea);
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
                int index = i * 4 + (j == 2 ? 3 : (j == 3 ? 2 : j)); // Swap columns 3 and 4
                outputArea.append((tablaVerdad[index][3] ? "1" : "0") + " ");
            }
            outputArea.append("\n");
        }

        mostrarMinitermosYMaxitermos(tablaVerdad, 3, outputArea);
        generarEcuacionSimplificadaKMap(tablaVerdad, 3, outputArea);
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
                int index = i * 4 + (j == 2 ? 3 : (j == 3 ? 2 : j)); // Swap columns 3 and 4
                outputArea.append((tablaVerdad[index][4] ? "1" : "0") + " ");
            }
            outputArea.append("\n");
        }

        mostrarMinitermosYMaxitermos(tablaVerdad, 4, outputArea);
        generarEcuacionSimplificadaKMap(tablaVerdad, 4, outputArea);
    }

    private void mostrarMinitermosYMaxitermos(boolean[][] tablaVerdad, int numVariables, JTextArea outputArea) {
        List<String> minitermos = new ArrayList<>();
        List<String> maxitermos = new ArrayList<>();

        for (int i = 0; i < tablaVerdad.length; i++) {
            if (tablaVerdad[i][numVariables]) {
                minitermos.add(getMinterm(i, numVariables));
            } else {
                maxitermos.add(getMaxterm(i, numVariables));
            }
        }

        outputArea.append("\nMinitérminos: " + String.join(", ", minitermos) + "\n");
        outputArea.append("Maxitérminos: " + String.join(", ", maxitermos) + "\n");
    }

    private void generarEcuacionSimplificadaKMap(boolean[][] tablaVerdad, int numVariables, JTextArea outputArea) {
        List<Integer> minitermos = new ArrayList<>();
        for (int i = 0; i < tablaVerdad.length; i++) {
            if (tablaVerdad[i][numVariables]) {
                minitermos.add(i);
            }
        }

        String ecuacionSimplificada = simplificarConKMap(minitermos, numVariables);
        outputArea.append("\nEcuación simplificada (K-Map): " + ecuacionSimplificada + "\n");
    }

    private String simplificarConKMap(List<Integer> minitermos, int numVariables) {
        int size = (int) Math.pow(2, numVariables);
        boolean[] kMap = new boolean[size];
        for (int miniterm : minitermos) {
            kMap[miniterm] = true;
        }

        List<String> grupos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (kMap[i]) {
                String term = getMinterm(i, numVariables);
                grupos.add(term);
            }
        }

        boolean combinados;
        do {
            combinados = false;
            List<String> nuevosGrupos = new ArrayList<>();
            boolean[] usado = new boolean[grupos.size()];

            for (int i = 0; i < grupos.size(); i++) {
                for (int j = i + 1; j < grupos.size(); j++) {
                    String combinado = combinarSiEsPosible(grupos.get(i), grupos.get(j));
                    if (combinado != null) {
                        nuevosGrupos.add(combinado);
                        usado[i] = true;
                        usado[j] = true;
                        combinados = true;
                    }
                }
            }

            for (int i = 0; i < grupos.size(); i++) {
                if (!usado[i]) {
                    nuevosGrupos.add(grupos.get(i));
                }
            }

            grupos = nuevosGrupos;
        } while (combinados);

        return String.join(" + ", new HashSet<>(grupos));
    }

    private String combinarSiEsPosible(String term1, String term2) {
        term1 = convertirABinario(term1);
        term2 = convertirABinario(term2);

        int diferencia = 0;
        StringBuilder combinado = new StringBuilder();

        for (int i = 0; i < term1.length(); i++) {
            char c1 = term1.charAt(i);
            char c2 = term2.charAt(i);

            if (c1 != c2) {
                diferencia++;
                combinado.append('-');
            } else {
                combinado.append(c1);
            }

            if (diferencia > 1) {
                return null;
            }
        }

        return diferencia == 1 ? combinado.toString() : null;
    }

    private String convertirABinario(String term) {
        return term.replace("A'", "0")
                .replace("A", "1")
                .replace("B'", "0")
                .replace("B", "1")
                .replace("C'", "0")
                .replace("C", "1")
                .replace("D'", "0")
                .replace("D", "1");
    }

    private String getMinterm(int index, int numVariables) {
        StringBuilder minterm = new StringBuilder();
        String binary = String.format("%" + numVariables + "s", Integer.toBinaryString(index)).replace(' ', '0');
        for (int i = 0; i < numVariables; i++) {
            if (binary.charAt(i) == '1') {
                minterm.append((char) ('A' + i));
            } else {
                minterm.append((char) ('A' + i)).append("'");
            }
        }
        return minterm.toString();
    }

    private String getMaxterm(int index, int numVariables) {
        StringBuilder maxterm = new StringBuilder();
        String binary = String.format("%" + numVariables + "s", Integer.toBinaryString(index)).replace(' ', '0');
        for (int i = 0; i < numVariables; i++) {
            if (binary.charAt(i) == '0') {
                maxterm.append((char) ('A' + i));
            } else {
                maxterm.append((char) ('A' + i)).append("'");
            }
            if (i < numVariables - 1) {
                maxterm.append(" + ");
            }
        }
        return "(" + maxterm + ")";
    }
}