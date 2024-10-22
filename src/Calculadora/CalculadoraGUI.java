package Calculadora;

import javax.swing.*;
import java.awt.*;

public class CalculadoraGUI extends JFrame {
    private final JTextArea outputArea;
    private final JTextField equationField;

    public CalculadoraGUI() {
        setTitle("Calculadora de Karnaugh");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create input panel
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

        // Create output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Add listener to the button
        calculateButton.addActionListener(_ -> {
            System.out.println("Botón 'Calcular' presionado"); // Debug message
            int numVariables = (Integer) variableCountBox.getSelectedItem();
            System.out.println("Número de variables seleccionado: " + numVariables); // Debug message
            calculate(numVariables);
        });
    }

    private void calculate(int numVariables) {
        String expr = equationField.getText();

        try {
            TablaDeVerdad tablaDeVerdad = new TablaDeVerdad();
            MapaKarnaugh mapaKarnaugh = new MapaKarnaugh();

            boolean[][] tabla = tablaDeVerdad.generarTablaDeVerdad(numVariables, expr);
            outputArea.setText(""); // Clear previous output
            System.out.println("Tabla de verdad generada"); // Debug message
            mostrarTablaDeVerdad(tabla, numVariables); // Display the truth table
            System.out.println("Tabla de verdad mostrada"); // Debug message
            mapaKarnaugh.generarMapaKarnaugh(tabla, numVariables, outputArea); // Display Karnaugh map
        } catch (Exception e) {
            outputArea.setText("Error: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    private void mostrarTablaDeVerdad(boolean[][] tabla, int numVariables) {
        outputArea.append("Tabla de Verdad:\n");
        System.out.println("Mostrando encabezados de la tabla de verdad"); // Debug message

        // Print headers (A, B, C, ... and OUT)
        for (int i = 0; i < numVariables; i++) {
            outputArea.append((char)('A' + i) + " ");
        }
        outputArea.append("OUT\n");

        // Print rows of the truth table
        for (boolean[] fila : tabla) {
            for (int j = 0; j < numVariables; j++) {
                outputArea.append((fila[j] ? "1" : "0") + " ");
            }
            // Print the OUT result
            outputArea.append((fila[numVariables] ? "1" : "0") + "\n");
        }
        System.out.println("Tabla de verdad completada"); // Debug message
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculadoraGUI().setVisible(true));
    }
}