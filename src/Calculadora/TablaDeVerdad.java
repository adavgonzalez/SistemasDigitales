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
        System.out.println("Generando tabla de verdad"); // Mensaje de depuración
        int filas = (int) Math.pow(2, numVariables);
        boolean[][] resultados = new boolean[filas][numVariables + 1]; // Incluye OUT
        boolean[] valores = new boolean[numVariables];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < numVariables; j++) {
                valores[j] = (i & (1 << (numVariables - j - 1))) != 0;
                resultados[i][j] = valores[j];
            }
            boolean resultado = operaciones.evaluate(expr, valores);
            resultados[i][numVariables] = resultado;
            System.out.println("Fila " + i + ": " + java.util.Arrays.toString(resultados[i])); // Mensaje de depuración
        }

        System.out.println("Tabla de verdad generada"); // Mensaje de depuración
        return resultados;
    }
}