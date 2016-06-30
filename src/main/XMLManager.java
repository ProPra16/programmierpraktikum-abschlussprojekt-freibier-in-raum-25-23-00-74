import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 *Methoden von https://www.youtube.com/watch?v=8MJJ7MWX8Qs
 */
public class XMLManager {

    public XMLManager(){
        DocumentBuilderFactory aufgabeXML = DocumentBuilderFactory.newInstance();
        DocumentBuilder XMLLesen = aufgabeXML.newDocumentBuilder();
        Document document = XMLLesen.parse();
    }

}
