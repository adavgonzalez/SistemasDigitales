package Calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraGUI extends JFrame {
    private JTextArea outputArea;
    private JTextField equationField;
    private JComboBox<Integer> variableCountBox;

    public CalculadoraGUI() {
        setTitle("Calculadora de Karnaugh");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        inputPanel.add(new JLabel("Número de variables (2-4):"));
        variableCountBox = new JComboBox<>(new Integer[]{2, 3, 4});
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

        // Add action listener to the button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });
    }

    private void calculate() {
        int numVariables = (int) variableCountBox.getSelectedItem();
        String expr = equationField.getText();

        TablaDeVerdad tablaDeVerdad = new TablaDeVerdad();
        MapaKarnaugh mapaKarnaugh = new MapaKarnaugh();

        boolean[][] tabla = tablaDeVerdad.generarTablaDeVerdad(numVariables, expr);
        outputArea.setText(""); // Clear previous output
        mostrarTablaDeVerdad(tabla, numVariables);
        mapaKarnaugh.generarMapaKarnaugh(tabla, numVariables, outputArea);
    }

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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CalculadoraGUI().setVisible(true);
            }
        });
    }
}