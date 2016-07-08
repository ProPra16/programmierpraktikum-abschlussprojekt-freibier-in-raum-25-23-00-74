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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


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

    //Methoden von http://www.drdobbs.com/jvm/creating-and-modifying-xml-in-java/240150782
        public static void ändereNodeValue(String tagName, String value, String dateiname) throws ParserConfigurationException {
            DocumentBuilderFactory aufgabeXML = DocumentBuilderFactory.newInstance();
            try{
                DocumentBuilder XMLLesen = aufgabeXML.newDocumentBuilder();
                Document document = XMLLesen.parse(new File("src/main/"+dateiname+".xml"));
                NodeList rootNodes = document.getElementsByTagName("aufgabe");
                Node root = rootNodes.item(0);
                if ( root == null )
                    return;

                //Findet den Node und ändert den Value
                for (int y = 0; y < rootNodes.getLength(); y++ ) {
                    Node data = rootNodes.item(y);
                    if ( data.getNodeType() == Node.TEXT_NODE ) {
                        data.setNodeValue(value);
                        return;
                    }
                }

            }catch (IOException e){
                e.printStackTrace();
            }
            catch (SAXException e){
                e.printStackTrace();
            }


        }
    }