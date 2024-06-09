import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EquipoBaloncesto {

	public static void main(String[] args) {
		boolean usarDatosPredefinidos = false; // Cambiar a true para usar datos predefinidos
		Object[][] jugadores;

		if (usarDatosPredefinidos) {
			jugadores = new Object[][] {
					{ "Josué Mojica", 185.0, 21, 31.0, 102.0, 86.0, 192.0 },
					{ "Hollman Vargas", 192.0, 25, 28.0, 110.0, 90.0, 192.0 },
					{ "Fernando Mendoza", 188.0, 28, 30.0, 100.0, 85.0, 195.0 },
					{ "Juan Matínez", 196.0, 26, 33.0, 98.0, 100.0, 210.0 },
					{ "Neffer Cano", 205.0, 31, 15.0, 40.0, 120.0, 211.0 }
			};
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

				jugadores = new Object[numJugadores][7];

				for (int i = 0; i < numJugadores; i++) {
					System.out.println(
							"\n" + "\u001B[32m" + "Ingrese los datos para el jugador " + (i + 1) + ":" + "\u001B[0m");

					System.out.println("\n" + "\u001B[32m" + "Nombre:" + "\u001B[0m");
					jugadores[i][0] = scanner.nextLine();

					jugadores[i][1] = solicitarDouble(scanner, "\n" + "\u001B[32m" + "Estatura (cm):" + "\u001B[0m");
					jugadores[i][2] = solicitarDouble(scanner, "\n" + "\u001B[32m" + "Edad:" + "\u001B[0m");
					jugadores[i][3] = solicitarDouble(scanner,
							"\n" + "\u001B[32m" + "Velocidad máxima (km/h):" + "\u001B[0m");
					jugadores[i][4] = solicitarDouble(scanner,
							"\n" + "\u001B[32m" + "Alcance en salto (cm):" + "\u001B[0m");
					jugadores[i][5] = solicitarDouble(scanner, "\n" + "\u001B[32m" + "Peso (kg):" + "\u001B[0m");
					jugadores[i][6] = solicitarDouble(scanner, "\n" + "\u001B[32m" + "Envergadura (cm):" + "\u001B[0m");
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				return;
			}
		}

		imprimirJugadoresPorVelocidades(Arrays.copyOf(jugadores, jugadores.length));

		System.out.println(
				"\n" + "\u001B[32m" + "Promedio de estaturas de la línea titular: " + "\u001B[0m"
						+ calcularPromedioEstaturas(jugadores) + " cm");

		System.out.println("\n" + "\u001B[32m" + "El jugador más completo es: " + "\u001B[0m"
				+ calcularJugadorMasCompleto(jugadores));
		System.out.println("\n" + "\u001B[32m" + "El jugador más capacitado para clavar el balón es: " + "\u001B[0m"
				+ calculadorJugadorMasCapacitado(jugadores));

		ordenarJugadoresPorIMC(Arrays.copyOf(jugadores, jugadores.length));
	}

	private static String calculadorJugadorMasCapacitado(Object[][] jugadores) {
		double[] alcances = new double[jugadores.length];
		String[] nombresOrdenados = new String[jugadores.length];

		try {
			for (int i = 0; i < jugadores.length; i++) {
				alcances[i] = (double) jugadores[i][1] + ((double) jugadores[i][6] / 2) * 0.7
						+ (double) jugadores[i][4];
				nombresOrdenados[i] = (String) jugadores[i][0];
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

	private static String calcularJugadorMasCompleto(Object[][] jugadores) {
		double[] puntuaciones = new double[jugadores.length];
		String[] nombresOrdenados = new String[jugadores.length];

		try {
			for (int i = 0; i < jugadores.length; i++) {
				puntuaciones[i] = ((double) jugadores[i][1] * 0.25) + ((double) jugadores[i][3] * 0.25)
						+ ((double) jugadores[i][4] * 0.20)
						+ ((double) jugadores[i][5] * 0.10) + ((double) jugadores[i][6] * 0.20);
				nombresOrdenados[i] = (String) jugadores[i][0];
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

	private static void imprimirJugadoresPorVelocidades(Object[][] jugadores) {
		double[] velocidades = new double[jugadores.length];
		String[] nombres = new String[jugadores.length];

		for (int i = 0; i < jugadores.length; i++) {
			velocidades[i] = (double) jugadores[i][3];
			nombres[i] = (String) jugadores[i][0];
		}

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

	private static double calcularPromedioEstaturas(Object[][] jugadores) {
		double sumaEstaturas = 0;
		int numJugadores = jugadores.length;

		for (int i = 0; i < numJugadores; i++) {
			sumaEstaturas += (double) jugadores[i][1];
		}

		return numJugadores > 0 ? sumaEstaturas / numJugadores : 0;
	}

	private static void ordenarJugadoresPorIMC(Object[][] jugadores) {
		double[] imc = new double[jugadores.length];
		String[] nombres = new String[jugadores.length];

		for (int i = 0; i < jugadores.length; i++) {
			imc[i] = (double) jugadores[i][5] / Math.pow((double) jugadores[i][1] / 100, 2);
			nombres[i] = (String) jugadores[i][0];
		}

		insertionSort(imc, nombres);

		System.out.println("\n" + "\u001B[32m" + "Jugadores ordenados por índice de masa corporal: " + "\u001B[0m");
		for (int i = 0; i < nombres.length; i++) {
			System.out.println(nombres[i] + ": " + imc[i]);
		}
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
