import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileManager {
    static File f = new File(
            "Aufgaben/");
    static File[] aufgabenArray = f.listFiles();/* Ein Array aller Dateien im Verzeichnis */

    public static List<String> aufgaben(File[] aufgabenArray){
        List<String> aufgaben = new ArrayList<>();
        for(File i : aufgabenArray){
           aufgaben.add(i.toString());
        }
        return aufgaben;
    }
}
