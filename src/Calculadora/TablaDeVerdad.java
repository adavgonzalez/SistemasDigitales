package Calculadora;

public class TablaDeVerdad {

    private Operaciones operaciones;

    public TablaDeVerdad() {
        this.operaciones = new Operaciones();
    }

    public boolean[][] generarTablaDeVerdad(int numVariables, String expr) {
        int filas = (int) Math.pow(2, numVariables);
        boolean[][] resultados = new boolean[filas][numVariables + 1]; // Incluye OUT
        boolean[] valores = new boolean[numVariables];

        System.out.print("A B C D OUT\n");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < numVariables; j++) {
                valores[j] = (i & (1 << (numVariables - j - 1))) != 0;
                System.out.print((valores[j] ? 1 : 0) + " ");
                resultados[i][j] = valores[j];
            }
            boolean resultado = operaciones.evaluate(expr, valores);
            resultados[i][numVariables] = resultado;
            System.out.println(resultado ? 1 : 0);
        }
        return resultados;
    }
}

