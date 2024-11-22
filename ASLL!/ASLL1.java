import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * -------------LL1---------------
 * este es un programa no recursivo para revisar la sintaxis de un programa
 * fuente
 * si se desea modificar la sintaxis del lenguaje, solo hay que modificar los
 * terminales, no terminales y la produccion correspondiente y la matriz de
 * transiciones
 * Entrada: tabla de token y lexemas generado por el analizador lexicografico
 * Salida: mensaje de error o de aceptacion
 */

public class ASLL1 {

    static String LEX = "", a;
    static String RENGLON;
    static int posicion = 0;
    static String entrada;
    static String term[] = new String[11];
    static String nt[] = new String[7];
    static String pila[] = new String[9999];
    static int tope = -1;
    static int M[][] = new int[11][7];
    static String prod[] = new String[12];
    static String X;

    
    

    public static void main(String[] args) {

        term[0] = "w";
        term[1] = "s";
        term[2] = "(";
        term[3] = ")";
        term[4] = "e";
        term[5] = "f";
        term[6] = "id";
        term[7] = "num";
        term[8] = ">";
        term[9] = "<";
        term[10] = "fin";

        nt[0] = "P";
        nt[1] = "I";
        nt[2] = "IF";
        nt[3] = "PP";
        nt[4] = "C";
        nt[5] = "E";
        nt[6] = "OP";

        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[i].length; j++) {
                M[i][j] = 0;
            }
        }

        M[0][0] = 1;
        M[0][1] = 5;
        M[0][3] = 2;
        M[1][0] = 1;
        M[1][1] = 4;
        M[1][2] = 6;
        M[1][3] = 2;
        M[5][3] = 3;
        M[6][4] = 7;
        M[6][5] = 8;
        M[7][4] = 7;
        M[7][5] = 9;
        M[8][6] = 10;
        M[9][6] = 11;
        M[10][3] = 3;

        prod[1] = "I PP";
        prod[2] = "P";
        prod[3] = "epsilon";
        prod[4] = "IF";
        prod[5] = "w";
        prod[6] = "s ( C ) e P f";
        prod[7] = "E OP E";
        prod[8] = "id";
        prod[9] = "num";
        prod[10] = ">";
        prod[11] = ">";

        entrada = args[0] + ".cm1";
        push("fin");
        push("P");

        lee_token(xArchivo(entrada));
        do {
            X = pop();
            if (a.equals("fin") && X.equals("fin")) {
                System.out.println("Analisis sintactico correcto");
            } else {
                if (es_term(X) >= 0) {
                    if (X.equals(a)) {
                        lee_token(xArchivo(entrada));
                    } else {
                        rut_error();
                    }
                } else {
                    if (M[es_term(a)][es_nt(X)] > 0) {
                        System.out.println("M[" + a + "][" + X + "] = " + M[es_term(a)][es_nt(X)] + "");
                        String Y[] = prod[M[es_term(a)][es_nt(X)]].split(" ");
                        // *hay que invertir el orden de la produccion
                        for (int i = (Y.length - 1); i >= 0; i--) {
                            push(Y[i]);
                        }
                        printPila();
                    } else {
                        rut_error();
                    }
                }
            }
        } while (!X.equals("fin"));
    }

    
    //|//
    public static void printPila() {
        for (int i = 0; i <= tope; i++) {
            System.out.print(pila[i] + " ");
        }
        System.out.println("");
        // pausa();
    }

    public static void push(String x) {
        if (tope >= 9999) {
            System.out.println("Error pila llena");
            System.exit(4);
        }
        pila[++tope] = x;
    }

    public static String pop() {
        if (tope < 0) {
            System.out.println("Error pila vacia");
            System.exit(4);
        }
        return (pila[tope--]);
    }

    public static void rut_error() {
        System.out.println("Error sitactico");
        System.exit(4);
    }

    public static File xArchivo(String xName) {
        File xFile = new File(xName);
        return xFile;
    }

    public static void lee_token(File xFile) {
        try {
            FileReader fr = new FileReader(xFile);
            BufferedReader br = new BufferedReader(fr);
            long noSirve = br.skip(posicion);
            String linea = br.readLine();
            posicion = posicion + linea.length() + 2;
            a = linea;
            linea = br.readLine();
            posicion = posicion + linea.length() + 2;
            LEX = linea;
            linea = br.readLine();
            posicion = posicion + linea.length() + 2;
            RENGLON = linea;
            fr.close();
        } catch (IOException e) {
            System.out.println("Errorsote");
        }
    }

    public static String pausa() {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String nada = null;
        try {
            nada = entrada.readLine();
            return (nada);
        } catch (Exception e) {
            System.err.println(e);
        }
        return ("");
    }

    public static int es_term(String x) {
        for (int i = 0; i < term.length; i++) {
            if (term[i].equals(x))
                return (i);
        }
        return (-1);
    }

    public static int es_nt(String x) {
        for (int i = 0; i < nt.length; i++) {
            if (nt[i].equals(x))
                return (i);
        }
        return (-1);
    }

}
