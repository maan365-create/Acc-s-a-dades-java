import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GestorVideojocsApp {
    private static final String FITXER = "videojocs.dat";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Videojoc> videojocs = carregarVideojocs();
        int opcio = 0;

        do {
            System.out.println("\n--- Menú Catàleg de Videojocs ---");
            System.out.println("1. Afegir videojoc");
            System.out.println("2. Llistar tots els videojocs");
            System.out.println("3. Cercar videojocs per títol");
            System.out.println("4. Actualitzar un videojoc");
            System.out.println("5. Eliminar un videojoc");
            System.out.println("6. Sortir del programa");
            System.out.print("Tria una opció: ");

            if (sc.hasNextInt()) {
                opcio = sc.nextInt();
                sc.nextLine(); // Netejar el buffer
            } else {
                System.out.println("Si us plau, introdueix un número vàlid.");
                sc.nextLine(); // Descartar entrada incorrecta
                continue;
            }

            switch (opcio) {
                case 1:
                    afegirVideojoc(sc, videojocs);
                    break;
                case 2:
                    llistarVideojocs(videojocs);
                    break;
                case 3:
                    cercarVideojocs(sc, videojocs);
                    break;
                case 4:
                    actualitzarVideojoc(sc, videojocs);
                    break;
                case 5:
                    eliminarVideojoc(sc, videojocs);
                    break;
                case 6:
                    desarVideojocs(videojocs);
                    System.out.println("Sortint del programa. Canvis desats correctament.");
                    break;
                default:
                    System.out.println("Opció incorrecta. Torna-ho a provar.");
            }
        } while (opcio != 6);

        sc.close();
    }

    // --- Funcionalitats CRUD ---

    private static void afegirVideojoc(Scanner sc, ArrayList<Videojoc> videojocs) {
        System.out.print("Introdueix el títol: ");
        String titol = sc.nextLine();
        System.out.print("Introdueix el gènere (ex: acció, rol, esport...): ");
        String genere = sc.nextLine();
        System.out.print("Introdueix l'any de llançament: ");
        int any = sc.nextInt();
        sc.nextLine(); // Netejar buffer
        System.out.print("Introdueix la plataforma (ex: PC, PlayStation, Xbox, Switch...): ");
        String plataforma = sc.nextLine();
        System.out.print("Introdueix el preu (€): ");
        double preu = sc.nextDouble();
        sc.nextLine(); // Netejar buffer

        Videojoc nouVideojoc = new Videojoc(titol, genere, any, plataforma, preu);
        videojocs.add(nouVideojoc);
        desarVideojocs(videojocs);
        System.out.println("Videojoc afegit i desat correctament al fitxer.");
    }

    private static void llistarVideojocs(ArrayList<Videojoc> videojocs) {
        if (videojocs.isEmpty()) {
            System.out.println("No hi ha cap videojoc al catàleg.");
        } else {
            System.out.println("\n--- Llista de Videojocs ---");
            for (int i = 0; i < videojocs.size(); i++) {
                System.out.println((i + 1) + ". " + videojocs.get(i));
            }
        }
    }

    private static void cercarVideojocs(Scanner sc, ArrayList<Videojoc> videojocs) {
        System.out.print("Introdueix el text a cercar al títol: ");
        String text = sc.nextLine().toLowerCase();
        boolean trobat = false;

        System.out.println("\n--- Resultats de la cerca ---");
        for (Videojoc v : videojocs) {
            if (v.getTitol().toLowerCase().contains(text)) {
                System.out.println(v);
                trobat = true;
            }
        }
        if (!trobat) {
            System.out.println("No s'ha trobat cap videojoc que coincideixi amb el text.");
        }
    }

    private static void actualitzarVideojoc(Scanner sc, ArrayList<Videojoc> videojocs) {
        if (videojocs.isEmpty()) {
            System.out.println("No hi ha videojocs per actualitzar.");
            return;
        }
        llistarVideojocs(videojocs);
        System.out.print("Introdueix el número del videojoc que vols actualitzar: ");
        int index = sc.nextInt() - 1;
        sc.nextLine(); // Netejar buffer

        if (index >= 0 && index < videojocs.size()) {
            Videojoc v = videojocs.get(index);
            System.out.println("Modificant: " + v.getTitol() + " (Deixa en blanc si no vols modificar el camp).");

            System.out.print("Nou títol (" + v.getTitol() + "): ");
            String titol = sc.nextLine();
            if (!titol.isEmpty()) v.setTitol(titol);

            System.out.print("Nou gènere (" + v.getGenere() + "): ");
            String genere = sc.nextLine();
            if (!genere.isEmpty()) v.setGenere(genere);

            System.out.print("Nou any (" + v.getAnyLlançament() + "): ");
            String anyStr = sc.nextLine();
            if (!anyStr.isEmpty()) {
                try {
                    v.setAnyLlançament(Integer.parseInt(anyStr));
                } catch (NumberFormatException e) {
                    System.out.println("Format d'any invàlid. Es manté l'anterior.");
                }
            }

            System.out.print("Nova plataforma (" + v.getPlataforma() + "): ");
            String plataforma = sc.nextLine();
            if (!plataforma.isEmpty()) v.setPlataforma(plataforma);

            System.out.print("Nou preu (" + v.getPreu() + "): ");
            String preuStr = sc.nextLine();
            if (!preuStr.isEmpty()) {
                try {
                    v.setPreu(Double.parseDouble(preuStr));
                } catch (NumberFormatException e) {
                    System.out.println("Format de preu invàlid. Es manté l'anterior.");
                }
            }

            desarVideojocs(videojocs);
            System.out.println("Videojoc actualitzat correctament.");
        } else {
            System.out.println("Número de videojoc invàlid.");
        }
    }

    private static void eliminarVideojoc(Scanner sc, ArrayList<Videojoc> videojocs) {
        if (videojocs.isEmpty()) {
            System.out.println("No hi ha videojocs per eliminar.");
            return;
        }
        llistarVideojocs(videojocs);
        System.out.print("Introdueix el número del videojoc que vols eliminar: ");
        int index = sc.nextInt() - 1;
        sc.nextLine(); // Netejar buffer

        if (index >= 0 && index < videojocs.size()) {
            Videojoc eliminat = videojocs.remove(index);
            desarVideojocs(videojocs);
            System.out.println("S'ha eliminat correctament el videojoc: " + eliminat.getTitol());
        } else {
            System.out.println("Número de videojoc invàlid.");
        }
    }

    // --- Mètodes de Persistència ---

    @SuppressWarnings("unchecked")
    public static ArrayList<Videojoc> carregarVideojocs() {
        ArrayList<Videojoc> videojocs = new ArrayList<>();
        File fitxer = new File(FITXER);
        if (fitxer.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxer))) {
                videojocs = (ArrayList<Videojoc>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error carregant el fitxer de dades: " + e.getMessage());
            }
        }
        return videojocs;
    }

    public static void desarVideojocs(ArrayList<Videojoc> videojocs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FITXER))) {
            oos.writeObject(videojocs);
        } catch (IOException e) {
            System.out.println("Error desant les dades al fitxer: " + e.getMessage());
        }
    }
}