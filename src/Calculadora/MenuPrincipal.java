package Calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase para el menú principal de la aplicación.
 */
public class MenuPrincipal extends JFrame {

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

        JTextArea outputArea = new JTextArea();
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
                    MapaKarnaugh mapa = new MapaKarnaugh();
                    mapa.generarMapaKarnaugh(tablaVerdad, numVariables, outputArea);
                } catch (NumberFormatException ex) {
                    outputArea.append("Número de variables inválido.\n");
                }
            }
        });
        panel.add(buttonGenerar);
    }
}
