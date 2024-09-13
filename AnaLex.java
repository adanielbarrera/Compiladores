import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class musica {

    static int com, est;
    static int c;
    static int Renglon;
    static char[] linea;
    static int filesize;
    static int a_i = 0;
    static int a_a = 0;
    static boolean fin_archivo = false;
    static String lex, miToken;
    static String entrada, salida;

    public static void rut_error() {
        System.out.println("\n\nError Lexicografico(" + Renglon + ")):  compilacion terminada, en el caracter["
                + Character.toString(c) + "] !!!!\n");
        System.exit(4);
    }

    public static int lee_car() {
        if (a_a <= filesize - 1) {
            if (linea[a_a] == 10) {
                Renglon++;
            }
            return (linea[a_a++]);
        } else {
            fin_archivo = true;
            return 255;
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

    public static char[] abreLeeCierra(String xName) {
        File xFile = new File(xName);
        char[] data;
        try {
            FileReader fin = new FileReader(xFile);
            filesize = (int) xFile.length();
            data = new char[filesize + 1];
            fin.read(data, 0, filesize);
            data[filesize] = ' ';
            filesize++;
            return (data);
        } catch (FileNotFoundException exc) {
        } catch (IOException exc) {
        }
        return null;
    }

    public static boolean creaEscribeArchivo(File xFile, String mensaje) {
        try {
            PrintWriter fileOut = new PrintWriter(
                    new FileWriter(xFile, true));
            fileOut.println(mensaje);
            fileOut.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static File xArchivo(String xName) {
        File xFile = new File(xName);
        return xFile;
    }

    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("Error: falta archivo:");
            System.exit(4);
        }

        entrada = args[0] + ".cm";
        salida = args[0] + ".cm1";

        if (!xArchivo(entrada).exists()) {
            System.err.println("Error: el archivo " + entrada + " no existe.");
            System.exit(4);
        }

        linea = abreLeeCierra(entrada);

        while (!fin_archivo) {
            est = 0;
            com = 0;
            miToken = token();
            if (!miToken.equals("nosirve")) {
                creaEscribeArchivo(xArchivo(salida), miToken);
                creaEscribeArchivo(xArchivo(salida), lex);
                creaEscribeArchivo(xArchivo(salida), Renglon + "");
            }
        }
        creaEscribeArchivo(xArchivo("tabla_resultado.cm1"),"fin");
        creaEscribeArchivo(xArchivo("tabla_resultado.cm1"),"fin");
        creaEscribeArchivo(xArchivo("tabla_resultado.cm1"),"666");
        System.out.println("Analisis Lexicografico correcto");
    }

    public static int diagrama() {
        a_a = a_i;
        switch (com) {
            case 0:
                com = 4;
                break;
            case 4:
                com = 12;
                break;
            case 12:
                com = 17;
                break;
            case 17:
                com = 20;
                break;
            case 20:
                com = 27;
                break;
            case 27:
                com = 30;
                break;
            case 30:
                com = 41;
                break;
            case 41:
                com = 48;
                break;
            case 48:
                rut_error();
                break;

            default:
                break;
        }
        return com;
    }

    public static String token() {
        while (true) {
            switch (est) {
                case 0:
                    c = lee_car();
                    if (esLetra(c)) {
                        est = 1;
                    } else {
                        est = diagrama();
                    }
                    break;
                case 1:
                    c = lee_car();
                    if (esDigito(c) || esLetra(c)) {
                        est = 1;
                    } else {
                        if (c == '_') {
                            est = 2;
                        } else {
                            est = 3;
                        }
                    }
                    break;
                case 2:
                    c = lee_car();
                    if (esDigito(c) || esLetra(c)) {
                        est = 1;
                    } else {
                        est = diagrama();
                    }
                    break;
                case 3:
                    a_a--;
                    lex = obtenLex();
                    a_i = a_a;
                    return ("id");
                case 4:
                    c = lee_car();
                    if (esDigito(c)) {
                        est = 5;
                    } else {
                        est = diagrama();
                    }
                    break;
                case 5:
                    c = lee_car();
                    if (esDigito(c)) {
                        est = 5;
                    } else {
                        if (c == '.') {
                            est = 6;
                        } else {
                            if (c == 'e' || c == 'E') {
                                est = 8;
                            } else {
                                est = diagrama();
                            }
                        }
                    }
                    break;
                case 6:
                    c = lee_car();
                    if (esDigito(c)) {
                        est = 7;
                    }
                    break;
                case 7:
                    c = lee_car();
                    if (esDigito(c)) {
                        est = 7;
                    } else {
                        if (c == 'E' || c == 'e') {
                            est = 8;
                        } else {
                            est = diagrama();
                        }
                    }
                    break;
                case 8:
                    c = lee_car();
                    if (esDigito(c)) {
                        est = 10;
                    } else {
                        if (c == '+' || c == '-') {
                            est = 9;
                        } else {
                            est = diagrama();
                        }
                    }
                    break;
                case 9:
                    c = lee_car();
                    if (esDigito(c)) {
                        est = 10;
                    } else {
                        est = diagrama();
                    }
                    break;
                case 10:
                    c = lee_car();
                    if (esDigito(c)) {
                        est = 10;
                    } else {
                        est = 11;
                    }
                    break;
                case 11:
                    a_a--;
                    lex = obtenLex();
                    a_i = a_a;
                    return ("num");
                case 12:
                    c = lee_car();
                    if (esDigito(c)) {
                        est = 13;
                    } else {
                        est = diagrama();
                    }
                    break;
                case 13:
                    c = lee_car();
                    if (esDigito(c)) {
                        est = 13;
                    } else {
                        if (c == '.') {
                            est = 14;
                        } else {
                            est = diagrama();
                        }
                    }
                    break;
                case 14:
                    c = lee_car();
                    if (esDigito(c)) {
                        est = 15;
                    } else {
                        est = diagrama();
                    }
                    break;
                case 15:
                    c = lee_car();
                    if (esDigito(c)) {
                        est = 15;
                    } else {
                        est = 16;
                    }
                    break;
                case 16:
                    a_a--;
                    lex = obtenLex();
                    a_i = a_a;
                    return ("num");
                case 17:
                    c = lee_car();
                    if (esDigito(c)) {
                        est = 18;
                    } else {
                        est = diagrama();
                    }
                    break;
                case 18:
                    c = lee_car();
                    if (esDigito(c)) {
                        est = 18;
                    } else {
                        est = 19;
                    }
                    break;
                case 19:
                    a_a--;
                    lex = obtenLex();
                    a_i = a_a;
                    return ("num");
                case 20:
                    c = lee_car();
                    if (c == '>') {
                        est = 21;
                    } else {
                        if (c == '<') {
                            est = 23;
                        } else {
                            est = diagrama();
                        }
                    }
                    break;
                case 21:
                    c = lee_car();
                    if (c == '=') {
                        est = 22;
                    } else {
                        est = 24;
                    }
                    break;
                case 22:
                    lex = obtenLex();
                    a_i = a_a;
                    return ("mai");
                case 23:
                    c = lee_car();
                    if (c == '=') {
                        est = 25;
                    } else {
                        est = 26;
                    }
                    break;
                case 24:
                    a_a--;
                    lex = obtenLex();
                    a_i = a_a;
                    return ("may");
                case 25:
                    lex = obtenLex();
                    a_i = a_a;
                    return ("mei");
                case 26:
                    a_a--;
                    lex = obtenLex();
                    a_i = a_a;
                    return ("men");
                case 27:
                    c = lee_car();
                    if (esDelim(c)) {
                        est = 28;
                    } else {
                        est = diagrama();
                    }
                    break;
                case 28:
                    c = lee_car();
                    if (esDelim(c)) {
                        est = 28;
                    } else {
                        est = 29;
                    }
                case 29:
                    a_a--;
                    lex = obtenLex();
                    a_i = a_a;
                    // como es un delimitador entonces no necesitamos que devuelva nada
                    return ("nosirve");
                case 30:
                    c = lee_car();
                    switch (c) {
                        case '=':
                            est = 31;
                            break;
                        case '!':
                            est = 34;
                            break;
                        case '&':
                            est = 37;
                            break;
                        case '|':
                            est = 39;
                            break;
                        default:
                            est = diagrama();
                            break;
                    }
                    break;
                case 31:
                    c = lee_car();
                    if (c == '=') {
                        est = 32;
                    } else {
                        est = 33;
                    }
                    break;
                case 32:
                    lex = obtenLex();
                    a_i = a_a;
                    return ("ig");
                case 33:
                    a_a--;
                    lex = obtenLex();
                    a_i = a_a;
                    return ("asig");
                case 34:
                    c = lee_car();
                    if (c == '=') {
                        est = 35;
                    } else {
                        est = 36;
                    }
                    break;
                case 35:
                    lex = obtenLex();
                    a_i = a_a;
                    return ("dif");
                case 36:
                    a_a--;
                    lex = obtenLex();
                    a_i = a_a;
                    return ("not");
                case 37:
                    c = lee_car();
                    if (c == '&') {
                        est = 38;
                    } else {
                        est = diagrama();
                    }
                    break;
                case 38:
                    lex = obtenLex();
                    a_i = a_a;
                    return ("and");
                case 39:
                    c = lee_car();
                    if (c == '|') {
                        est = 40;
                    } else {
                        est = diagrama();
                    }
                    break;
                case 40:
                    lex = obtenLex();
                    a_i = a_a;
                    return ("or");
                case 41:
                    c = lee_car();
                    switch (c) {
                        case '(':
                            est = 42;
                            break;
                        case ')':
                            est = 43;
                            break;
                        case '+':
                            est = 44;
                            break;
                        case '-':
                            est = 45;
                            break;
                        case '*':
                            est = 46;
                            break;
                        case '/':
                            est = 47;
                            break;
                        default:
                            est = diagrama();
                            break;
                    }
                    break;
                case 42:
                    lex = obtenLex();
                    a_i = a_a;
                    return ("pa");
                case 43:
                    lex = obtenLex();
                    a_i = a_a;
                    return ("pc");
                case 44:
                    lex = obtenLex();
                    a_i = a_a;
                    return ("mas");
                case 45:
                    lex = obtenLex();
                    a_i = a_a;
                    return ("menos");
                case 46:
                    lex = obtenLex();
                    a_i = a_a;
                    return ("*");
                case 47:
                    lex = obtenLex();
                    a_i = a_a;
                    return ("/");
                case 48:
                    c = lee_car();
                    if (c == 255) {
                        est = 49;
                    } else {
                        est = diagrama();
                    }
                    break;
                case 49:
                    est = diagrama();
                    break;
                default:
                    rut_error();
                    break;
            }
        }
    }

    public static boolean esLetra(int x) {
        if ((x >= 65 && x <= 90) || (x >= 97 && x <= 122)) {
            return true;
        }
        return false;
    }

    public static boolean esDigito(int x) {
        if (x >= 48 && x <= 57) {
            return true;
        }
        return false;
    }

    public static boolean esDelim(int x) {
        switch (x) {
            case 32:
                return true;
            case 9:
                return true;
            case 13:
                return true;
            case 10:
                return true;
            default:
                return false;

        }
    }

    public static String obtenLex() {
        String x = "";
        for (int i = a_i; i < a_a; i++) {
            x = x + linea[i];
        }
        return x;
    }
    
}

