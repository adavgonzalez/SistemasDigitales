package Calculadora;

/**
 * Clase para generar la tabla de verdad de una ecuación booleana.
 */
public class TablaDeVerdad {

    private final Operaciones operaciones;

    public TablaDeVerdad() {
        this.operaciones = new Operaciones();
    }

    /**
     * Genera la tabla de verdad para una ecuación booleana.
     * @param numVariables El número de variables.
     * @param expr La ecuación booleana.
     * @return La tabla de verdad.
     */
    public boolean[][] generarTablaDeVerdad(int numVariables, String expr) {
        int filas = (int) Math.pow(2, numVariables);
        boolean[][] resultados = new boolean[filas][numVariables + 1]; // Incluye OUT
        boolean[] valores = new boolean[numVariables];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < numVariables; j++) {
                // Establecer los valores de las variables en función del índice actual
                valores[j] = (i & (1 << (numVariables - j - 1))) != 0;
                resultados[i][j] = valores[j];
            }
            // Evaluar la expresión usando los valores actuales
            boolean resultado = operaciones.evaluate(expr, valores);
            resultados[i][numVariables] = resultado; // Guardar el resultado en la última columna
        }

        return resultados;
    }
}