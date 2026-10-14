import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class CrearZooXML {

    public static void main(String[] args) {
        try {
            // 1. Inicialitzar el constructor de documents XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // 2. Crear l'element arrel <zoo>
            Element rootElement = doc.createElement("zoo");
            doc.appendChild(rootElement);

            // 3. Afegir animals
            crearAnimal(doc, rootElement, "1", "Pingüí", "Aptenodytes forsteri", "Peix", "5");
            crearAnimal(doc, rootElement, "2", "Lleó", "Panthera leo", "Carn", "8");
            crearAnimal(doc, rootElement, "3", "Tigre", "Panthera tigris", "Carn", "4");

            // 4. Escriure el contingut en un fitxer XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            // Configuració per indicar que el fitxer s'ha d'indentar (format de línies i espais)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("zoo.xml"));

            transformer.transform(source, result);
            System.out.println("Fitxer 'zoo.xml' creat correctament!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mètode auxiliar per afegir un element <animal> amb les seves etiquetes
    private static void crearAnimal(Document doc, Element root, String id, String nom, String especie, String aliment, String edat) {
        Element animal = doc.createElement("animal");
        root.appendChild(animal);

        // Atribut id
        Attr attrId = doc.createAttribute("id");
        attrId.setValue(id);
        animal.setAttributeNode(attrId);

        // Fills de <animal>
        afegirFill(doc, animal, "nom", nom);
        afegirFill(doc, animal, "especie", especie);
        afegirFill(doc, animal, "aliment", aliment);
        afegirFill(doc, animal, "edat", edat);
    }

    private static void afegirFill(Document doc, Element pare, String nomEtiqueta, String valor) {
        Element elem = doc.createElement(nomEtiqueta);
        elem.appendChild(doc.createTextNode(valor));
        pare.appendChild(elem);
    }
}