import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

ppublic class EXERCICI1{
    public static void main(String[] args) {
        // Variables recomanades en la guia
        int numCaracters = 0;
        int numLinies = 0;
        int numParaules = 0;
        boolean dinsParaula = false;
        int[] frequencia = new int[65536];
        boolean fitxerBuit = true;

        try (FileReader fr = new FileReader("text.txt")) {
            int c;

            // Bucle principal: lectura caràcter a caràcter
            while ((c = fr.read()) != -1) {
                fitxerBuit = false;
                char caracter = (char) c;

                // 1. Comptar caràcters (sense comptar salts de línia \n i \r)
                if (caracter != '\n' && caracter != '\r') {
                    numCaracters++;
                }

                // 2. Comptar línies (comptem els caràcters \n)
                if (caracter == '\n') {
                    numLinies++;
                }

                // 3. Comptar paraules (transició de separador a caràcter normal)
                if (caracter == ' ' || caracter == '\t' || caracter == '\n' || caracter == '\r') {
                    dinsParaula = false;
                } else {
                    if (!dinsParaula) {
                        numParaules++;
                        dinsParaula = true;
                    }

                    // 4. Freqüència de caràcters (només caràcters que NO siguin separadors)
                    frequencia[caracter]++;
                }
            }

            // Si el fitxer no està buit i l'última línia no acaba en '\n', sumem 1 a les línies
            if (!fitxerBuit) {
                numLinies++;
            }

            // Buscar el caràcter més freqüent a l'array de freqüències
            char caracterMesFrequent = ' ';
            int maxFrequencia = 0;

            for (int i = 0; i < frequencia.length; i++) {
                if (frequencia[i] > maxFrequencia) {
                    maxFrequencia = frequencia[i];
                    caracterMesFrequent = (char) i;
                }
            }

            // Mostrar els resultats per pantalla
            System.out.println("Resultats de l'anàlisi:");
            System.out.println("-------------------------");
            System.out.println("Total de caràcters (sense salts): " + numCaracters);
            System.out.println("Total de línies: " + numLinies);
            System.out.println("Total de paraules: " + numParaules);

            if (maxFrequencia > 0) {
                System.out.println("Caràcter més repetit: '" + caracterMesFrequent + "' (" + maxFrequencia + " vegades)");
            } else {
                System.out.println("El fitxer no conté caràcters vàlids per comptar freqüències.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("El fitxer no existeix.");
        } catch (IOException e) {
            System.out.println("S'ha produït un error de lectura.");
        } catch (SecurityException e) {
            System.out.println("No tens permisos per accedir al fitxer.");
        }
    }
}