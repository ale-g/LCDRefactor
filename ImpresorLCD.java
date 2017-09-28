import java.util.ArrayList;
import java.util.List;

public class ImpresorLCD {

    // Puntos fijos
    private final int[] pf1;
    private final int[] pf2;
    private final int[] pf3;
    private final int[] pf4;
    private final int[] pf5;
    private String[][] matrizImpr;

    static final String CARACTER_VERTICAL = "|";
    static final String CARACTER_HORIZONTAL = "-";

    private int size;
    private int filasDig;
    private int columDig;
    private int totalFilas;
    private int totalColum;

    public ImpresorLCD() {
        this.pf1 = new int[2];
        this.pf2 = new int[2];
        this.pf3 = new int[2];
        this.pf4 = new int[2];
        this.pf5 = new int[2];
    }

    /**
     *
     * Metodo encargado de a�adir una linea vertical a la matriz de Impresion
     *
     * @param matriz Matriz Impresion
     * @param punto Punto Pivote
     * @param size Tama�o Segmento
     * @param caracter Caracter Segmento
     */
    private void adicionarLineaVertical(String[][] matriz, int[] punto,
            int size, String caracter) {
        for (int i = 1; i <= size; i++) {
            int valor = punto[0] + i;
            matriz[valor][punto[1]] = caracter;
        }
    }

    /**
     *
     * Metodo encargado de a�adir una linea horizontal a la matriz de Impresion
     *
     * @param matriz Matriz Impresion
     * @param punto Punto Pivote
     * @param size Tama�o Segmento
     * @param caracter Caracter Segmento
     */
    private void adicionarLineaHorizontal(String[][] matriz, int[] punto,
            int size, String caracter) {
        for (int i = 1; i <= size; i++) {
            int valor = punto[1] + i;
            matriz[punto[0]][valor] = caracter;
        }
    }

    /**
     *
     * Metodo encargado de un segmento a la matriz de Impresion
     *
     * @param segmento Segmento a adicionar
     */
    private void adicionarSegmento(int segmento) {
        int[] pfaux = asignarPuntoFijo(segmento);
        if (pfaux != null) {
            if (segmento > 0 && segmento < 5) {
                adicionarLineaVertical(this.matrizImpr, pfaux,
                        this.size, CARACTER_VERTICAL);
            } else if (segmento > 4 && segmento < 8) {
                adicionarLineaHorizontal(this.matrizImpr, pfaux,
                        this.size, CARACTER_HORIZONTAL);
            }
        }
    }
    
    /**
     *
     * Metodo encargado de asingar el punto fijo que corresponden al segmento
     *
     * @param segmento Segmento a evaluar
     */
    private int[] asignarPuntoFijo(int segmento) {
        switch (segmento) {
            case 1:
                return this.pf1;
            case 2:
                return this.pf2;
            case 3:
                return this.pf5;
            case 4:
                return this.pf4;
            case 5:
                return this.pf1;
            case 6:
                return this.pf2;
            case 7:
                return this.pf3;
            default:
                return null;
        }
    }

    /**
     *
     * Metodo encargado de definir los segmentos que componen un digito y a
     * partir de los segmentos adicionar la representacion del digito a la
     * matriz
     *
     * @param numero Digito
     */
    private void adicionarDigito(int numero) {
        // Establece los segmentos de cada numero
        List<Integer> segList = new ArrayList<>();
        int[][] matrizPos = {{1, 2, 3, 4, 5, 7}, {3, 4}, {5, 3, 6, 2, 7}, {5, 3, 6, 4, 7}, {1, 6, 3, 4},
        {5, 1, 6, 4, 7}, {5, 1, 6, 2, 7, 4}, {5, 3, 4}, {1, 2, 3, 4, 5, 6, 7}, {1, 3, 4, 5, 6, 7}};

        for (int i = 0; i < matrizPos[numero].length; i++) {
            segList.add(matrizPos[numero][i]);
        }

        for (Integer e : segList) {
            adicionarSegmento(e);
        }
    }

    /**
     *
     * Metodo encargado de imprimir un numero
     *
     * @param numeroImp Numero a Imprimir
     * @param espacio Espacio Entre digitos
     */
    private void imprimirNumero(String numeroImp, int espacio) {
        int pivotX = 0;
        char[] digitos;
       
        digitos = numeroImp.toCharArray();

        for (char digito : digitos) {
            //Valida que el caracter sea un digito
            if (!Character.isDigit(digito)) {
                throw new IllegalArgumentException("Caracter " + digito
                        + " no es un digito");
            }

            int numero = Integer.parseInt(String.valueOf(digito));

            //Calcula puntos fijos
            this.pf1[0] = 0;
            this.pf1[1] = 0 + pivotX;

            this.pf2[0] = (this.filasDig / 2);
            this.pf2[1] = 0 + pivotX;

            this.pf3[0] = (this.filasDig - 1);
            this.pf3[1] = 0 + pivotX;

            this.pf4[0] = (this.columDig - 1);
            this.pf4[1] = (this.filasDig / 2) + pivotX;

            this.pf5[0] = 0;
            this.pf5[1] = (this.columDig - 1) + pivotX;

            pivotX = pivotX + this.columDig + espacio;

            adicionarDigito(numero);
        }
        imprimirMatriz();
    }
    
    /**
     *
     * Metodo encargado de inicializar la matriz
     *
     * @param size cantidad de lineas por segmento
     * @param numeroImp numero a imprimir
     * @param espacio cantidad de espacios entre números
     */
    public void inicializarMatriz(int size, String numeroImp, int espacio){
        this.size = size;
      
        // Calcula el numero de filas cada digito
        this.filasDig = (2 * this.size) + 3;

        // Calcula el numero de columna de cada digito
        this.columDig = this.size + 2;

        // Calcula el total de filas de la matriz en la que se almacenaran los digitos
        this.totalFilas = this.filasDig;

        // Calcula el total de columnas de la matriz en la que se almacenaran los digitos
        this.totalColum = (this.columDig * numeroImp.length())
                + (espacio * numeroImp.length());

        // crea matriz para almacenar los numero a imprimir
        this.matrizImpr = new String[this.totalFilas][this.totalColum];

        // Inicializa matriz
        for (int i = 0; i < this.totalFilas; i++) {
            for (int j = 0; j < this.totalColum; j++) {
                this.matrizImpr[i][j] = " ";
            }
        }
    }

    /**
     *
     * Metodo encargado de imprimir la matriz
     */
    public void imprimirMatriz() {
        for (int i = 0; i < this.totalFilas; i++) {
            for (int j = 0; j < this.totalColum; j++) {
                System.out.print(this.matrizImpr[i][j]);
            }
            System.out.println();
        }
    }

    /**
     *
     * Metodo encargado de procesar la entrada que contiene el size del segmento
     * de los digitos y los digitos a imprimir
     *
     * @param comando Entrada que contiene el size del segmento de los digito y
     * el numero a imprimir
     * @param espacioDig Espacio Entre digitos
     */
    public void procesar(String comando, int espacioDig) {
        String[] parametros = generarParametros(comando);
        int tam = isNumeric(parametros[0]);
        estaEnRango(tam, 1, 10);
        inicializarMatriz(tam, parametros[1], espacioDig);
        imprimirNumero(parametros[1], espacioDig);
    }

    /**
     *
     * Metodo encargado de separar los parametros a usar
     *
     * @param comando parametros a separar
     */
    public String[] generarParametros(String comando) {
        String[] parametros;

        if (!comando.contains(",")) {
            throw new IllegalArgumentException("Cadena " + comando
                    + " no contiene caracter ,");
        }
        parametros = comando.split(",");

        if (parametros.length > 2) {
            throw new IllegalArgumentException("Cadena " + comando
                    + " contiene mas caracter ,");
        } else if (parametros.length < 2) {
            throw new IllegalArgumentException("Cadena " + comando
                    + " no contiene los parametros requeridos");
        }
        return parametros;
    }

    /**
     *
     * Metodo encargado de validar si una cadena es numerica
     *
     * @param cadena Cadena
     */
    static int isNumeric(String cadena) {
        try {
            return Integer.parseInt(cadena);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Cadena " + cadena
                    + " no es un entero");
        }
    }

    /**
     *
     * Metodo encargado de validar si un número está dentro de un rango
     * establcido
     *
     * @param numero Numero
     * @param menor Menor
     * @param mayor Mayor
     */
    static boolean estaEnRango(int numero, int menor, int mayor) {
        if (numero < menor || numero > mayor) {
            throw new IllegalArgumentException("El espacio entre "
                    + "digitos debe estar entre" + menor + " y " + mayor);
        }
        return true;
    }

}
