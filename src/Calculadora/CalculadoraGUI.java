package Calculadora;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * Clase principal para la interfaz gráfica de la calculadora de Karnaugh.
 */
public class CalculadoraGUI extends JFrame {
    private final JTextArea outputArea;
    private final JTextField equationField;

    public CalculadoraGUI() {
        setTitle("Calculadora de Karnaugh");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear panel de entrada
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Número de variables (1-4):"));
        JComboBox<Integer> variableCountBox = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        inputPanel.add(variableCountBox);

        inputPanel.add(new JLabel("Ecuación booleana:"));
        equationField = new JTextField();
        inputPanel.add(equationField);

        JButton calculateButton = new JButton("Calcular");
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.NORTH);

        // Crear área de salida
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Agregar listener al botón
        calculateButton.addActionListener(_ -> calculate());
    }

    /**
     * Método para calcular la tabla de verdad y el mapa de Karnaugh.
     */
    private void calculate() {
        String expr = equationField.getText();
        int numVariables = contarVariablesUnicas(expr);

        try {
            TablaDeVerdad tablaDeVerdad = new TablaDeVerdad();
            MapaKarnaugh mapaKarnaugh = new MapaKarnaugh();

            boolean[][] tabla = tablaDeVerdad.generarTablaDeVerdad(numVariables, expr);
            outputArea.setText(""); // Limpiar salida previa
            mostrarTablaDeVerdad(tabla, numVariables);
            mapaKarnaugh.generarMapaKarnaugh(tabla, numVariables, outputArea);
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
            e.printStackTrace(); // Imprimir traza de error para depuración
        }
    }

    /**
     * Método para contar el número de variables únicas en una ecuación booleana.
     * @param ecuacion La ecuación booleana.
     * @return El número de variables únicas.
     */
    private int contarVariablesUnicas(String ecuacion) {
        HashSet<Character> variables = new HashSet<>();
        for (char c : ecuacion.toCharArray()) {
            if (Character.isLetter(c)) {
                variables.add(Character.toUpperCase(c));
            }
        }
        return variables.size();
    }

    /**
     * Método para mostrar la tabla de verdad en el área de salida.
     * @param tabla La tabla de verdad.
     * @param numVariables El número de variables.
     */
    private void mostrarTablaDeVerdad(boolean[][] tabla, int numVariables) {
        outputArea.append("\nTabla de Verdad:\n");
        for (int i = 0; i < numVariables; i++) {
            outputArea.append((char)('A' + i) + " ");
        }
        outputArea.append("OUT\n");

        for (boolean[] fila : tabla) {
            for (int j = 0; j < numVariables; j++) {
                outputArea.append((fila[j] ? "1" : "0") + " ");
            }
            outputArea.append((fila[numVariables] ? "1" : "0") + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculadoraGUI().setVisible(true));
    }
}