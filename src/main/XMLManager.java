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

    static String aufgabename = "";
    static String aufgabenstellung = "";
    static String klasse = "";
    static String babysteps = "";
    static int babystepsTime;
    static String atdd = "";
    static String atddName = "";
    static String atddInhalt = "";
    static String dateiname = "";
    static String testName = "";
    static String test = "";

    public static void XMLManager(String datei) throws ParserConfigurationException {
        DocumentBuilderFactory aufgabeXML = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder XMLLesen = aufgabeXML.newDocumentBuilder();
            Document document = XMLLesen.parse(datei);

            NodeList rootNodes = document.getElementsByTagName("aufgabe");
            Node aufgabe = rootNodes.item(0);
            Element noteElement = (Element) aufgabe;

            Node babystepsNode = noteElement.getElementsByTagName("babysteps").item(0);
            Element babystepsElement = (Element) babystepsNode;
            babysteps = babystepsElement.getTextContent();

            Node dateinameNode = noteElement.getElementsByTagName("dateiname").item(0);
            Element dateinameElement = (Element) dateinameNode;
            dateiname = dateinameElement.getTextContent();

            Node babystepsTimeNode = noteElement.getElementsByTagName("babystepstime").item(0);
            Element babystepsTimeElement = (Element) babystepsTimeNode;
            babystepsTime = Integer.parseInt(babystepsTimeElement.getTextContent());

            Node atddNode = noteElement.getElementsByTagName("atdd").item(0);
            Element atddElement = (Element) atddNode;
            atdd = atddElement.getTextContent();

            Node atddNameNode = noteElement.getElementsByTagName("atddName").item(0);
            Element atddNameElement = (Element) atddNameNode;
            atddName = atddNameElement.getTextContent();

            Node testNameNode = noteElement.getElementsByTagName("testName").item(0);
            Element testNameElement = (Element) testNameNode;
            testName = testNameElement.getTextContent();

            Node testNode = noteElement.getElementsByTagName("test").item(0);
            Element testElement = (Element) testNode;
            test = testElement.getTextContent();

            Node atddInhaltNode = noteElement.getElementsByTagName("atddInhalt").item(0);
            Element atddInhaltElement = (Element) atddInhaltNode;
            atddInhalt = atddInhaltElement.getTextContent();

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
        catch (AssertionError e){
            System.out.println("Leider erfüllt die XML Datei nicht die Anforderungen1111111");
        }
        catch (NullPointerException e){
            System.out.println("Leider erfüllt die XML Datei nicht die Anforderungen");
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

    public static boolean getBabysteps(){
        if(babysteps.equals("true")){
            return true;
        }
        return false;
    }
    public static int getBabystepsTime(){
        return babystepsTime;
    }
    public static boolean getAttd(){
        if(atdd.equals("true")){return true;}
        return false;
    }
    public static String getAttdName() {return atddName;}
    public static String getAttdInhalt(){
        return atddInhalt;
    }
    public static String getDateiname(){
        return dateiname;
    }
    public static String getTestName(){
        return testName;
    }
    public static String getTest(){
        return test;
    }
}
