static int com, est;
    static int c;
    static int Renglon;
    static char[] linea;
    static int filesize;
    static int a_i = 0;
    static int a_a = 0;
    static boolean fin_archivo = false;
    static String lex, miToken;
    static String entrada;

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
                creaEscribeArchivo(xArchivo("tabla_resultado.cm1"), miToken);
                creaEscribeArchivo(xArchivo("tabla_resultado.cm1"), lex);
                creaEscribeArchivo(xArchivo("tabla_resultado.cm1"), Renglon + "");
            }
        }
        System.out.println("Analisis Lexicografico correcto");
    }

    public static int diagrama() {
        a_a = a_i;
        switch (com) {
            case(0):
            com = 11;
            break;
        }
        return com;
    }

    public static String token() {
        while (true) {
            switch (est) {
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
