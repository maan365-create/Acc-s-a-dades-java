import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class LlegirZooXML {

    public static void main(String[] args) {
        try {
            File fitxerXML = new File("zoo.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fitxerXML);

            // Normalitza el document per evitar problemes de format
            doc.getDocumentElement().normalize();

            // Obtenir tots els nodes <animal>
            NodeList llistaAnimals = doc.getElementsByTagName("animal");

            System.out.println("=== LLISTAT DE TOTS ELS ANIMALS ===");
            for (int i = 0; i < llistaAnimals.getLength(); i++) {
                Node node = llistaAnimals.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String id = element.getAttribute("id");
                    String nom = element.getElementsByTagName("nom").item(0).getTextContent();
                    String especie = element.getElementsByTagName("especie").item(0).getTextContent();
                    String aliment = element.getElementsByTagName("aliment").item(0).getTextContent();
                    String edat = element.getElementsByTagName("edat").item(0).getTextContent();

                    System.out.println("Animal #" + id);
                    System.out.println("Nom: " + nom);
                    System.out.println("Espècie: " + especie);
                    System.out.println("Aliment: " + aliment);
                    System.out.println("Edat: " + edat);
                    System.out.println();
                }
            }

            // --- BONUS 1: Comptar quants animals hi ha al zoo ---
            System.out.println("TOTAL D'ANIMALS AL ZOO: " + llistaAnimals.getLength());
            System.out.println("-----------------------------------");

            // --- BONUS 2: Mostrar només els animals que mengen "Carn" ---
            System.out.println("\n=== ANIMALS QUE MENGEN CARN ===");
            for (int i = 0; i < llistaAnimals.getLength(); i++) {
                Node node = llistaAnimals.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String aliment = element.getElementsByTagName("aliment").item(0).getTextContent();

                    if ("Carn".equalsIgnoreCase(aliment)) {
                        String nom = element.getElementsByTagName("nom").item(0).getTextContent();
                        String especie = element.getElementsByTagName("especie").item(0).getTextContent();
                        System.out.println("- " + nom + " (" + especie + ")");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}