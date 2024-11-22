import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SLR1 {
    static String LEX = "", a;
    static String RENGLON;
    static int posicion = 0;
    static String entrada;
    static String term[] = new String[11];
    static String nt[] = new String[7];
    static String pila[] = new String[9999];
    static int tope = -1;
    static int M[][] = new int[21][18];
    static String pi[] = new String[12];
    static int lpd[] = new int[12];
    static String S;
    static int e;

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
                return (i + term.length);
        }
        return (-1);
    }

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
        nt[1] = "PP";
        nt[2] = "I";
        nt[3] = "IF";
        nt[4] = "C";
        nt[5] = "E";
        nt[6] = "OP";
        // inicializar la tabla con 0
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < term.length + nt.length; j++) {
                M[i][j] = 0;
            }
        }
        // Shift y reduce
        M[0][0] = 4;
        M[0][1] = 5;
        M[0][11] = 1; // Goto
        M[0][13] = 2; // Goto
        M[0][14] = 3; // Goto
        M[1][10] = 1313;
        M[2][1] = 5;
        M[2][5] = -3;
        M[2][10] = -3;
        M[2][11] = 7;
        M[2][12] = 2;
        M[2][13] = 2;
        M[2][14] = 3;
        M[3][0] = -4;
        M[3][1] = -4;
        M[3][5] = -4;
        M[3][10] = -4;
        M[4][0] = -5;
        M[4][5] = -5;
        M[4][10] = -5;
        M[5][2] = 8;
        M[6][5] = -1;
        M[6][10] = 1;
        M[7][5] = -2;
        M[7][10] = -2;
        M[8][6] = 11;
        M[8][7] = 12;
        M[8][15] = 9;
        M[8][16] = 10;
        M[9][3] = 13;
        M[10][8] = 15;
        M[10][9] = 16;
        M[10][17] = 14;
        M[11][9] = 8;
        //*aqui me quede */
        M[12][3] = 10;
        M[8][15] = 9;
        M[8][15] = 9;
        M[8][15] = 9;
        M[8][15] = 9;
        M[8][15] = 9;
        M[8][15] = 9;
        

        pi[1] = "PPP";
        pi[2] = "P";
        pi[3] = "epsilon";
        pi[4] = "IF";
        pi[5] = "w";
        pi[6] = "s ( C ) e P f";
        pi[7] = "E OP E";
        pi[8] = "id";
        pi[9] = "num";
        pi[10] = ">";
        pi[11] = ">";

        entrada = args[0] + ".cm1";
        push("0");

        lee_token(xArchivo(entrada));
        while(true){
            S = pila[tope];
            if(M[Integer.parseInt(S)][es_term(a)]==1313){
                System.out.println("Yei");
                System.exit(0);
            }else{
                if(M[Integer.parseInt(S)][es_term(a)]>0){   //si es positivo entonces es un shift
                    push(a);
                    push(M[Integer.parseInt(S)][es_term(a)]+"");
                    lee_token(xArchivo(entrada));
                }else{
                    if(M[Integer.parseInt(S)][es_term(a)]<0){ //reduccion
                        for(int k =1;k<=2*(lpd[M[Integer.parseInt(S)][es_term(a)]*-1]);k++){
                            pop();
                        }
                        e = Integer.parseInt(pila[tope]);
                        push(pi[M[Integer.parseInt(S)][es_term(a)]*-1]);
                        if(M[Integer.parseInt(pila[tope])][es_nt(pi[M[Integer.parseInt(S)][es_term(a)]*-1])] == 0){
                            rut_error();
                        }else{
                            push(M[e][es_nt(pi[M[Integer.parseInt(S)][es_term(a)]*-1])]+"");
                        }
                    }else{
                        rut_error();
                    }
                }
            }
        }
    }
}
