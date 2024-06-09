import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EquipoBaloncesto {

	public static void main(String[] args) {
		boolean usarDatosPredefinidos = false; // Cambiar a true para usar datos predefinidos
		String[] jugadoresNombres;
		double[] estaturas;
		double[] edades;
		double[] velocidadesMaximas;
		double[] alcancesEnSalto;
		double[] pesos;
		double[] envergaduras;

		if (usarDatosPredefinidos) {
			jugadoresNombres = new String[] { "Josué Mojica", "Hollman Vargas", "Fernando Mendoza", "Juan Matínez",
					"Neffer Cano" };
			estaturas = new double[] { 185, 192, 188, 196, 205 };
			edades = new double[] { 21, 25, 28, 26, 31 };
			velocidadesMaximas = new double[] { 31, 28, 30, 33, 15 };
			alcancesEnSalto = new double[] { 102, 110, 100, 98, 40 };
			pesos = new double[] { 86, 90, 85, 100, 120 };
			envergaduras = new double[] { 192, 192, 195, 210, 211 };
		} else {
			try (Scanner scanner = new Scanner(System.in)) {
				int numJugadores = 0;

				while (true) {
					try {
						System.out.println("\n" + "\u001B[32m" + "Ingrese el número de jugadores:" + "\u001B[0m");
						numJugadores = scanner.nextInt();
						scanner.nextLine();
						if (numJugadores <= 0) {
							System.out.println("El número de jugadores debe ser mayor que cero. Inténtalo de nuevo.");
							continue;
						}
						break;
					} catch (InputMismatchException e) {
						System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
						scanner.nextLine();
					}
				}

				jugadoresNombres = new String[numJugadores];
				estaturas = new double[numJugadores];
				edades = new double[numJugadores];
				velocidadesMaximas = new double[numJugadores];
				alcancesEnSalto = new double[numJugadores];
				pesos = new double[numJugadores];
				envergaduras = new double[numJugadores];

				for (int i = 0; i < numJugadores; i++) {
					System.out.println(
							"\n" + "\u001B[32m" + "Ingrese los datos para el jugador " + (i + 1) + ":" + "\u001B[0m");

					System.out.println("\n" + "\u001B[32m" + "Nombre:" + "\u001B[0m");
					jugadoresNombres[i] = scanner.nextLine();

					estaturas[i] = solicitarEntero(scanner, "\n" + "\u001B[32m" + "Estatura (cm):" + "\u001B[0m");
					edades[i] = solicitarEntero(scanner, "\n" + "\u001B[32m" + "Edad:" + "\u001B[0m");
					velocidadesMaximas[i] = solicitarDouble(scanner,
							"\n" + "\u001B[32m" + "Velocidad máxima (km/h):" + "\u001B[0m");
					alcancesEnSalto[i] = solicitarEntero(scanner,
							"\n" + "\u001B[32m" + "Alcance en salto (cm):" + "\u001B[0m");
					pesos[i] = solicitarEntero(scanner, "\n" + "\u001B[32m" + "Peso (kg):" + "\u001B[0m");
					envergaduras[i] = solicitarEntero(scanner, "\n" + "\u001B[32m" + "Envergadura (cm):" + "\u001B[0m");
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				return;
			}
		}

		ImprimirJugadoresPorVelocidades(Arrays.copyOf(jugadoresNombres, jugadoresNombres.length),
				Arrays.copyOf(velocidadesMaximas, velocidadesMaximas.length));

		System.out.println(
				"\n" + "\u001B[32m" + "Promedio de estaturas de la línea titular: " + "\u001B[0m"
						+ calcularPromedioEstaturas(estaturas) + " cm");

		System.out.println("\n" + "\u001B[32m" + "El jugador más completo es: " + "\u001B[0m"
				+ calcularJugadorMasCompleto(jugadoresNombres, estaturas, velocidadesMaximas,
						alcancesEnSalto, pesos, envergaduras));
		System.out.println("\n" + "\u001B[32m" + "El jugador más capacitado para clavar el balón es: " + "\u001B[0m"
				+ calculadorJugarMasCapacitado(jugadoresNombres, estaturas, envergaduras, alcancesEnSalto));

		ordenarJugadoresPorIMC(Arrays.copyOf(jugadoresNombres, jugadoresNombres.length),
				Arrays.copyOf(pesos, pesos.length), Arrays.copyOf(estaturas, estaturas.length));

	}

	private static String calculadorJugarMasCapacitado(String[] nombre, double[] estatura, double[] envergadura,
			double[] saltos) {
		double[] alcances = new double[nombre.length];
		String[] nombresOrdenados = Arrays.copyOf(nombre, nombre.length);

		try {
			for (int i = 0; i < nombre.length; i++) {
				alcances[i] = estatura[i] + (envergadura[i] / 2) * 0.7 + (saltos[i]);
			}

			insertionSort(alcances, nombresOrdenados);

			return nombresOrdenados[nombresOrdenados.length - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Error: Índice fuera de rango.");
			return "Error: Índice fuera de rango." + e.getMessage();
		} catch (Exception e) {
			System.err.println("Error inesperado: " + e.getMessage());
			return "Error inesperado: " + e.getMessage();
		}

	}

	private static String calcularJugadorMasCompleto(String[] nombres, double[] estaturas,
			double[] velocidades, double[] saltos, double[] pesos, double[] envergaduras) {

		double[] puntuaciones = new double[nombres.length];
		String[] nombresOrdenados = Arrays.copyOf(nombres, nombres.length);

		try {

			for (int i = 0; i < nombres.length; i++) {
				puntuaciones[i] = (estaturas[i] * 0.25) + (velocidades[i] * 0.25) + (saltos[i] * 0.20)
						+ (pesos[i] * 0.10)
						+ (envergaduras[i] * 0.20);
			}

			insertionSort(puntuaciones, nombresOrdenados);

			return nombresOrdenados[nombresOrdenados.length - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("Error: Índice fuera de rango.");
			return "Error: Índice fuera de rango." + e.getMessage();
		} catch (Exception e) {
			System.err.println("Error inesperado: " + e.getMessage());
			return "Error inesperado: " + e.getMessage();
		}

	}

	private static void ImprimirJugadoresPorVelocidades(String[] nombres, double[] velocidades) {

		insertionSort(velocidades, nombres);

		System.out.println("\n" + "\u001B[32m" + "Jugadores ordenados por velocidad:" + "\u001B[0m");
		for (int i = 0; i < nombres.length; i++) {
			System.out.println(nombres[i] + ": " + velocidades[i] + " km/h");
		}
	}

	private static void insertionSort(double[] arr, String[] nombres) {
		for (int i = 1; i < arr.length; i++) {
			double key = arr[i];
			String nombreKey = nombres[i];
			int j = i - 1;

			while (j >= 0 && arr[j] > key) {
				arr[j + 1] = arr[j];
				nombres[j + 1] = nombres[j];
				j = j - 1;
			}

			arr[j + 1] = key;
			nombres[j + 1] = nombreKey;
		}
	}

	private static double calcularPromedioEstaturas(double[] estaturas) {
		return Arrays.stream(estaturas).average().orElse(0);
	}

	private static void ordenarJugadoresPorIMC(String[] nombres, double[] pesos, double[] estaturas) {
		double[] imc = new double[nombres.length];
		for (int i = 0; i < nombres.length; i++) {
			imc[i] = pesos[i] / Math.pow(estaturas[i] / 100, 2);
			// La estatura debe estar en metros al calcular el IMC
		}
		insertionSort(imc, nombres);

		System.out.println("\n" + "\u001B[32m" + "Jugadores ordenados por índice de masa corporal: " + "\u001B[0m");
		for (int i = 0; i < nombres.length; i++) {
			System.out.println(nombres[i] + ": " + imc[i]);
		}
	}

	private static int solicitarEntero(Scanner scanner, String mensaje) {
		int valor = 0;
		while (true) {
			try {
				System.out.println(mensaje);
				valor = scanner.nextInt();
				scanner.nextLine();
				if (valor <= 0) {
					System.out.println("Debe ser un número mayor a 0");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
				scanner.nextLine();
			}
		}
		return valor;
	}

	private static double solicitarDouble(Scanner scanner, String mensaje) {
		double valor = 0;
		while (true) {
			try {
				System.out.println(mensaje);
				valor = scanner.nextDouble();
				scanner.nextLine();
				if (valor <= 0) {
					System.out.println("Debe ser un número mayor a 0");
					continue;
				}
				break;
			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida. Por favor, ingrese un número.");
				scanner.nextLine();
			}
		}
		return valor;
	}
}