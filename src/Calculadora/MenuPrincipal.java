package Calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase para el menú principal de la aplicación.
 */
public class MenuPrincipal extends JFrame {

    private JTextArea outputArea;

    public MenuPrincipal() {
        setTitle("Calculadora de Mapas de Karnaugh");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuArchivo = new JMenu("Archivo");
        menuBar.add(menuArchivo);

        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuArchivo.add(itemSalir);

        JMenu menuAyuda = new JMenu("Ayuda");
        menuBar.add(menuAyuda);

        JMenuItem itemInformacion = new JMenuItem("Información");
        itemInformacion.addActionListener(e -> {
            InformacionFrame infoFrame = new InformacionFrame();
            infoFrame.setVisible(true);
        });
        menuAyuda.add(itemInformacion);

        outputArea = new JTextArea();
        outputArea.setEditable(false); // Ensure the output area is not editable
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);

        JLabel labelVariables = new JLabel("Número de variables:");
        panel.add(labelVariables);

        JTextField fieldVariables = new JTextField(2);
        panel.add(fieldVariables);

        JLabel labelExpr = new JLabel("Expresión:");
        panel.add(labelExpr);

        JTextField fieldExpr = new JTextField(20);
        panel.add(fieldExpr);

        JButton buttonGenerar = new JButton("Generar Mapa");
        buttonGenerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numVariables = Integer.parseInt(fieldVariables.getText());
                    String expr = fieldExpr.getText();
                    TablaDeVerdad tabla = new TablaDeVerdad();
                    boolean[][] tablaVerdad = tabla.generarTablaDeVerdad(numVariables, expr);
                    outputArea.setText(""); // Clear previous output
                    mostrarTablaDeVerdad(tablaVerdad, numVariables); // Display the truth table
                    MapaKarnaugh mapa = new MapaKarnaugh();
                    mapa.generarMapaKarnaugh(tablaVerdad, numVariables, outputArea);
                } catch (NumberFormatException ex) {
                    outputArea.append("Número de variables inválido.\n");
                }
            }
        });
        panel.add(buttonGenerar);
    }

    private void mostrarTablaDeVerdad(boolean[][] tabla, int numVariables) {
        outputArea.append("Tabla de Verdad:\n");

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
    }
}