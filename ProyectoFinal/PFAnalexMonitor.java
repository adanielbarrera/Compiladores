import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class PFAnalexMonitor {

	static int c;
	static int Renglon = 1;
	static int filesize;
	static int a_i = 0;
	static int a_a = 0;
	static boolean fin_archivo = false;
	static char[] linea;
	static String entrada, salida;
	static int COM, EST;
	static String LEX, MITOKEN;
	static String[] pr = new String[5];

	public static String obten_lex(int inicio, int fin) {
		String x = "";
		for (int i = inicio; i < fin; i++) {
			x = x + linea[i];
		}
		return (x);
	}

	public static boolean es_letra(int x) {
		if ((x >= 65 && x <= 90) || (x >= 97 && x <= 122))
			return (true);
		return (false);
	}

	public static boolean es_digito(int x) {
		if ((x >= 48 && x <= 57))
			return (true);
		return (false);
	}

	public static boolean es_delim(int x) {
		if (x == 32 || x == 9 || x == 10 || x == 13)
			return (true);
		return (false);
	}

	public static boolean any1(int x) {
		if (x == '"' || x == 9 || x == 10 || x == 13 || x == 255)
			return (false);
		return (true);
	}

	public static boolean any2(int x) {
		if (x == '%' || x == 10 || x == 13)
			return (false);
		return (true);
	}

	public static boolean any3(int x) {
		if (x == 10 || x == 13)
			return (false);
		return (true);
	}

	public static boolean any4(int x) {
		if (x == '%' || x == 255)
			return (false);
		return (true);
	}

	public static void rut_error() {
		System.out.println("\n\nERROR Lexicografico(" + Renglon + "):  compilacion terminada, en el caracter["
				+ Character.toString(c) + "] !!!!\n");
		System.exit(4);
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

	public static int DIAGRAMA() {
		a_a = a_i;
		switch (COM) {
			case 0:
				COM = 4;
				break;
			case 4:
				COM = 9;
				break;
			case 9:
				COM = 12;
				break;
			case 12:
				COM = 15;
				break;
			case 15:
				COM = 19;
				break;
			case 19:
				COM = 32;
				break;
			case 32:
				COM = 37;
				break;
			case 37:
				COM = 42;
				break;
			case 42:
				COM = 54;
				break;
			case 55:
				rut_error();
		}
		return (COM);
	}

	public static String TOKEN() {
		while (true) {
			switch (EST) {
				case 0:
					c = lee_car();
					// System.out.println("Letra="+c+" estado 1");
					// pausa();
					if (es_letra(c))
						EST = 1;
					else
						EST = DIAGRAMA();
					break;
				case 1:
					c = lee_car();
					if (es_digito(c) || es_letra(c))
						EST = 1;
					else {
						if (c == '_')
							EST = 2;
						else
							EST = 3;
					}
					break;
				case 2:
					c = lee_car();
					if (es_digito(c) || es_letra(c))
						EST = 1;
					else
						EST = DIAGRAMA();
					break;
				case 3:
					a_a--;
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return (LEX);
				case 4:
					c = lee_car();
					if (es_digito(c))
						EST = 5;
					else
						EST = DIAGRAMA();
					break;
				case 5:
					c = lee_car();
					if (es_digito(c))
						EST = 5;
					else {
						if (c == '.')
							EST = 6;
						else {
							EST = DIAGRAMA();
						}
					}
					break;
				case 6:
					c = lee_car();
					if (es_digito(c))
						EST = 7;
					else
						EST = DIAGRAMA();
					break;
				case 7:
					c = lee_car();
					if (es_digito(c))
						EST = 7;
					else
						EST = 8;
					break;
				case 8:
					a_a--;
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("num");
				case 9:
					c = lee_car();
					if (es_digito(c))
						EST = 10;
					else
						EST = DIAGRAMA();
					break;
				case 10:
					c = lee_car();
					if (es_digito(c))
						EST = 10;
					else
						EST = 11;
					break;
				case 11:
					a_a--;
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("num");
				case 12:
					c = lee_car();
					if (es_delim(c))
						EST = 13;
					else
						EST = DIAGRAMA();
					break;
				case 13:
					c = lee_car();
					if (es_delim(c))
						EST = 13;
					else
						EST = 14;
					break;
				case 14:
					a_a--;
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("nosirve");
				case 15:
					c = lee_car();
					if (c == '"')
						EST = 16;
					else
						EST = DIAGRAMA();
					break;
				case 16:
					c = lee_car();
					if (any1(c))
						EST = 17;
					else
						EST = DIAGRAMA();
					break;
				case 17:
					c = lee_car();
					if (c == '"')
						EST = 18;
					else {
						if (any1(c)) {
							EST = 17;
						} else
							EST = DIAGRAMA();
					}
					break;
				case 18:
					LEX = obten_lex(a_i + 1, a_a - 1);
					a_i = a_a;
					return ("cad");
				case 19:
					c = lee_car();
					switch (c) {
						case '>':
							EST = 20;
							break;
						case '<':
							EST = 24;
							break;
						case '=':
							EST = 29;
							break;
						default:
							EST = DIAGRAMA();
					}
					break;
				case 20:
					c = lee_car();
					switch (c) {
						case '=':
							EST = 22;
							break;
						case '<':
							EST = 23;
							break;
						default:
							EST = 21;
					}
					break;
				case 21:
					a_a--;
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return (">");
				case 22:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("mai");
				case 23:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("dif");
				case 24:
					c = lee_car();
					switch (c) {
						case '>':
							EST = 23;
							break;
						case '=':
							EST = 25;
							break;
						default:
							EST = 28;
					}
					break;
				case 25:
					c = lee_car();
					if (c == '>')
						EST = 26;
					else
						EST = 27;
					break;
				case 26:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("asig");
				case 27:
					a_a--;
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("mei");
				case 28:
					a_a--;
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("men");
				case 29:
					c = lee_car();
					switch (c) {
						case '<':
							EST = 30;
							break;
						case '>':
							EST = 22;
							break;
						default:
							EST = 31;
					}
					break;
				case 30:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("mei");
				case 31:
					a_a--;
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("=");
				case 32:
					c = lee_car();
					if (c == '%')
						EST = 33;
					else
						EST = DIAGRAMA();
					break;
				case 33:
					c = lee_car();
					if (any2(c))
						EST = 35;
					else {
						if (c == 10 || c == 13) {
							EST = 34;
						} else {
							EST = DIAGRAMA();
						}
					}
					break;
				case 34:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("nosrive");
				case 35:
					c = lee_car();
					if (any3(c))
						EST = 35;
					else {
						if (c == 10 || c == 13) {
							EST = 36;
						} else {
							EST = DIAGRAMA();
						}
					}
					break;
				case 36:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("nosirve");
				case 37:
					c = lee_car();
					if (c == '%')
						EST = 38;
					else
						EST = DIAGRAMA();
					break;
				case 39:
					c = lee_car();
					if (c == '%')
						EST = 40;
					else if (any4(c)) {
						EST = 39;
					} else {
						EST = DIAGRAMA();
					}
					break;
				case 40:
					c = lee_car();
					if (c == '%')
						EST = 41;
					else if (any4(c)) {
						EST = 39;
					} else {
						EST = DIAGRAMA();
					}
					break;
				case 41:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("nosirve");
				case 42:
					c = lee_car();
					switch (c) {
						case '{':
							EST = 43;
							break;
						case '}':
							EST = 44;
							break;
						case '(':
							EST = 45;
							break;
						case ')':
							EST = 46;
							break;
						case '[':
							EST = 47;
							break;
						case ']':
							EST = 48;
							break;
						case '+':
							EST = 49;
							break;
						case '-':
							EST = 50;
							break;
						case '*':
							EST = 51;
							break;
						case '/':
							EST = 52;
							break;
						case ':':
							EST = 53;
							break;
						default:
							EST = DIAGRAMA();
					}
					break;
				case 43:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("{");
				case 44:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("}");
				case 45:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("(");
				case 46:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return (")");
				case 47:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("[");
				case 48:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("]");
				case 49:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("+");
				case 50:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("-");
				case 51:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("*");
				case 52:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("/");
				case 53:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return (":");
				case 54:
					c = lee_car();
					if (c == 255)
						EST = 55;
					else
						EST = DIAGRAMA();
					break;
				case 55:
					LEX = obten_lex(a_i, a_a);
					a_i = a_a;
					return ("nosirve");
				default:
					return ("");
			}
		}
	}

	public static void main(String[] argumento) {

		pr[0] = "si";
		pr[1] = "entonces";
		pr[2] = "finsi";
		pr[3] = "mientras";
		pr[4] = "finmientras";

		if (argumento.length == 0) {
			System.out.println("ERROR: Falta el archivo....");
			System.exit(4);
		}
		entrada = argumento[0] + ".cm";
		salida = argumento[0] + ".cm1";

		if (!xArchivo(entrada).exists()) {
			System.out.println("ERROR: Al archivo [" + entrada + "] no existe....");
			System.exit(4);
		}
		linea = abreLeeCierra(entrada);

		while (!fin_archivo) {
			EST = 0;
			COM = 0;
			MITOKEN = TOKEN();
			if (!MITOKEN.equals("nosirve")) {
				creaEscribeArchivo(xArchivo(salida), MITOKEN);
				creaEscribeArchivo(xArchivo(salida), LEX);
				creaEscribeArchivo(xArchivo(salida), Renglon + "");
			}
		}
		creaEscribeArchivo(xArchivo(salida), "fin");
		creaEscribeArchivo(xArchivo(salida), "fin");
		creaEscribeArchivo(xArchivo(salida), "666");

		System.out.println("Analisis lexicografico correcto . . .");
	}

}
