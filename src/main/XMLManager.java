import jdk.internal.org.xml.sax.XMLReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 *Methoden von https://www.youtube.com/watch?v=8MJJ7MWX8Qs
 */
public class XMLManager {

    public static void main(String[] args) throws ParserConfigurationException {
        DocumentBuilderFactory aufgabeXML = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder XMLLesen = aufgabeXML.newDocumentBuilder();
            Document document = XMLLesen.parse(XMLReader.class.getResourceAsStream("/XMLAufgabe.xml"));

            NodeList rootNodes = document.getElementsByTagName("aufgabe");
            Node aufgabe = rootNodes.item(0);
            Element noteElement = (Element) aufgabe;

            Node klassenname = noteElement.getElementsByTagName("klassenname").item(0);
            Element klassennameElemet = (Element) klassenname;

            Node aufgabenstellung = noteElement.getElementsByTagName("aufgabenstellung").item(0);
            Element aufgabenstellungElement = (Element) aufgabenstellung;

            Node klasse = noteElement.getElementsByTagName("klasse").item(0);
            Element klasseElement = (Element) klasse;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (SAXException e){
            e.printStackTrace();
        }


    }

}
