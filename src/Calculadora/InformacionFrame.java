package Calculadora;

import javax.swing.*;
import java.awt.*;

public class InformacionFrame extends JFrame {

    public InformacionFrame() {
        setTitle("Información");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setText("Información:\n\n" +
                "1. Tabla de Verdad:\n" +
                "   - Es una tabla que muestra todas las posibles combinaciones de valores de entrada y sus correspondientes salidas.\n" +
                "   - Ejemplo:\n" +
                "     A | B | A*B\n" +
                "     0 | 0 | 0\n" +
                "     0 | 1 | 0\n" +
                "     1 | 0 | 0\n" +
                "     1 | 1 | 1\n\n" +
                "2. Mapa de Karnaugh:\n" +
                "   - Es una representación gráfica de la tabla de verdad que facilita la simplificación de expresiones booleanas.\n" +
                "   - Ejemplo:\n" +
                "     AB\\CD | 00 | 01 | 11 | 10\n" +
                "     -------------------------\n" +
                "     00    |  0 |  0 |  1 |  1\n" +
                "     01    |  1 |  1 |  0 |  0\n" +
                "     11    |  0 |  1 |  1 |  0\n" +
                "     10    |  1 |  0 |  0 |  1\n\n" +
                "3. Mintermino:\n" +
                "   - Es un término en una expresión booleana que es verdadero para una sola combinación de variables.\n" +
                "   - Ejemplo: A*B*C\n\n" +
                "4. Maxtermino:\n" +
                "   - Es un término en una expresión booleana que es falso para una sola combinación de variables.\n" +
                "   - Ejemplo: (A + B + C)'\n");

        add(new JScrollPane(infoArea), BorderLayout.CENTER);
    }
}
