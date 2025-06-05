package view;

import java.time.LocalDate;
import java.util.Scanner;

public class InputUtils {
    public static int readInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Limpiar salto de línea
        return value;
    }

    public static double readDouble(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Ingrese un número válido: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // Limpiar salto de línea
        return value;
    }

    public static String readString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static LocalDate readDate(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (true) {
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.print("Formato inválido. Ingrese la fecha nuevamente (YYYY-MM-DD): ");
            }
        }
    }
}
