import java.io.*;
import java.util.Scanner;

public class XifradorCesar {

    private static final String ENTRADA = "entrada.txt";
    private static final String XIFRAT = "xifrat.txt";
    private static final String DESXIFRAT = "desxifrat.txt";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Introdueix la clau de xifrat (nombre enter): ");

        int clau;

        try {
            clau = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: la clau ha de ser un nombre enter.");
            return;
        }

        System.out.println("\n--- XIFRAT ---");

        try {
            xifrarFitxer(ENTRADA, XIFRAT, clau);
            System.out.println("Xifrat completat correctament.");
            System.out.println("Fitxer creat: " + XIFRAT);
        } catch (IOException e) {
            System.out.println("Error durant el xifrat: " + e.getMessage());
            return;
        }

        System.out.println("\n--- DESXIFRAT ---");

        try {
            desxifrarFitxer(XIFRAT, DESXIFRAT, clau);
            System.out.println("Desxifrat completat correctament.");
            System.out.println("Fitxer creat: " + DESXIFRAT);
        } catch (IOException e) {
            System.out.println("Error durant el desxifrat: " + e.getMessage());
        }

        scanner.close();
    }

    /**
     * Llegeix el fitxer d'entrada, inverteix cada línia,
     * aplica el xifrat Cèsar i escriu el resultat.
     */
    public static void xifrarFitxer(String entrada, String sortida, int clau)
            throws IOException {

        try (
            BufferedReader br = new BufferedReader(new FileReader(entrada));
            BufferedWriter bw = new BufferedWriter(new FileWriter(sortida))
        ) {

            String linia;
            int numeroLinia = 0;

            while ((linia = br.readLine()) != null) {
                numeroLinia++;

                // 1. Invertim la línia
                String invertida = invertir(linia);

                // 2. Apliquem el desplaçament Cèsar
                String xifrada = aplicarCesar(invertida, clau);

                // 3. Escrivim la línia xifrada
                bw.write(xifrada);
                bw.newLine();

                System.out.println("Línia " + numeroLinia + " xifrada.");
            }
        }
    }

    /**
     * Llegeix el fitxer xifrat, aplica el desplaçament invers
     * i torna a invertir cada línia.
     */
    public static void desxifrarFitxer(String entrada, String sortida, int clau)
            throws IOException {

        try (
            BufferedReader br = new BufferedReader(new FileReader(entrada));
            BufferedWriter bw = new BufferedWriter(new FileWriter(sortida))
        ) {

            String linia;
            int numeroLinia = 0;

            while ((linia = br.readLine()) != null) {
                numeroLinia++;

                // 1. Invertim el desplaçament del xifrat
                String desxifrada = aplicarCesar(linia, -clau);

                // 2. Tornem a invertir la línia
                String original = invertir(desxifrada);

                // 3. Escrivim la línia original
                bw.write(original);
                bw.newLine();

                System.out.println("Línia " + numeroLinia + " desxifrada.");
            }
        }
    }

    /**
     * Inverteix una cadena.
     * Exemple: "Hola món" -> "nóm aloH"
     */
    public static String invertir(String text) {
        return new StringBuilder(text).reverse().toString();
    }

    /**
     * Aplica un desplaçament Cèsar sobre els caràcters Unicode.
     *
     * Cada caràcter es desplaça 'clau' posicions en Unicode.
     */
    public static String aplicarCesar(String text, int clau) {

        StringBuilder resultat = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char caracter = text.charAt(i);

            char nouCaracter = (char) (caracter + clau);

            resultat.append(nouCaracter);
        }

        return resultat.toString();
    }
}
