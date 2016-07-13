import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 *Methoden von https://www.youtube.com/watch?v=8MJJ7MWX8Qs
 */
public class XMLManager {

    static String aufgabename;
    static String aufgabenstellung;
    static String klasse;

    public static void XMLManager(String dateiname) throws ParserConfigurationException {
        DocumentBuilderFactory aufgabeXML = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder XMLLesen = aufgabeXML.newDocumentBuilder();
            Document document = XMLLesen.parse((XMLManager.class.getResource("/"+dateiname+".xml").toString()));

            NodeList rootNodes = document.getElementsByTagName("aufgabe");
            Node aufgabe = rootNodes.item(0);
            Element noteElement = (Element) aufgabe;

            Node aufgabenameNode = noteElement.getElementsByTagName("aufgabename").item(0);
            Element aufgabenameElemet = (Element) aufgabenameNode;
            aufgabename = aufgabenameElemet.getTextContent();

            Node aufgabenstellungNode = noteElement.getElementsByTagName("aufgabenstellung").item(0);
            Element aufgabenstellungElement = (Element) aufgabenstellungNode;
            aufgabenstellung = aufgabenstellungElement.getTextContent();

            Node klasseNode = noteElement.getElementsByTagName("klasse").item(0);
            Element klasseElement = (Element) klasseNode;
            klasse = klasseElement.getTextContent();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (SAXException e){
            e.printStackTrace();
        }
    }

    public static String getAufgabename(){
        return aufgabename;
    }

    public static String getAufgabenstellung(){
        return aufgabenstellung;
    }

    public static String getKlasse(){
        return klasse;
    }
}
