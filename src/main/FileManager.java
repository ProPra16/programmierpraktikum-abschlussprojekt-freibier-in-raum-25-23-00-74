import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class FileManager {
    static File f = new File(
            "src/main/");
    static File[] aufgabenArray = f.listFiles();/* Ein Array aller Dateien im Verzeichnis */

    public static List<String> aufgaben(File[] aufgabenArray){
        List<String> aufgaben = new ArrayList<>();
        for(File i : aufgabenArray){
            aufgaben.add(i.toString());
        }
        return aufgaben;
    }
    //Methode  aus den Folien Übung 11
    private static void changeNodeValue(String tagName, String value, String dateiname) throws ParserConfigurationException {
        DocumentBuilderFactory aufgabeXML = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder XMLLesen = aufgabeXML.newDocumentBuilder();
            Document document = XMLLesen.parse(new File("src/main/" + dateiname + ".xml"));

            NodeList rootNodes = document.getElementsByTagName("aufgabe");
            Node aufgabe = rootNodes.item(0);
            Element noteElement = (Element) aufgabe;

            Node aufgabenameNode = noteElement.getElementsByTagName(tagName).item(0);
            Element aufgabenameElemet = (Element) aufgabenameNode;
            aufgabenameElemet.setTextContent(value);

            writeToFile(dateiname, document);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (SAXException e){
            e.printStackTrace();
        }
    }

    //Methoden von http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
    public static void newFile(String dateiname, String aufgaben, String aufgabename, String klasseninhalt)throws ParserConfigurationException{
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.newDocument();
            Element rootElement = document.createElement("aufgabe");
            document.appendChild(rootElement);

            Element aufgabenname = document.createElement("aufgabename");
            rootElement.appendChild(aufgabenname);
            aufgabenname.setTextContent(aufgabename);


            Element aufgabenstellung = document.createElement("aufgabenstellung");
            rootElement.appendChild(aufgabenstellung);
            aufgabenstellung.setTextContent(aufgaben);

            Element klassen = document.createElement("klassen");
            rootElement.appendChild(klassen);

            Element klasse = document.createElement("klasse");
            klassen.appendChild(klasse);
            klasse.setTextContent(klasseninhalt);

            writeToFile(dateiname, document);
    }

    public static void openFile(String dateiname)throws ParserConfigurationException {
        XMLManager.XMLManager(dateiname);
    }

    public static void safeFile(String dateiname, String inhalt)throws ParserConfigurationException{
       changeNodeValue("klasse",inhalt,dateiname);
    }

    private static void writeToFile(String dateiname, Document document) throws ParserConfigurationException{
        DocumentBuilderFactory aufgabeXML = DocumentBuilderFactory.newInstance();
        try{
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            DOMSource src = new DOMSource(document);
            StreamResult fileResult = new StreamResult(
                    new File("src/main/"+dateiname+".xml"));
            transformer.transform(src, fileResult);
        }catch(TransformerException e){
            e.printStackTrace();
        }

    }
}