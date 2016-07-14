import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jan on 08.07.16.
 */
public class FileManagerTest {
    @Test
    public void newFile() throws Exception {
        FileManager.newFile("newFileTest","Romische Zahlen konvertieren", "Romische Zahlen", "public class test{}");
    }

    @Test
    public void openFile() throws Exception {
        FileManager.openFile("FileManagerTest");
        assertEquals("Romische Zahlen",XMLManager.getAufgabename());
        assertEquals("Konvertiert arabische in romische Zahlen.",XMLManager.getAufgabenstellung());
        assertEquals("\n" +
                "            public class RomanNumberConverter {\n" +
                "            }\n" +
                "        ",XMLManager.getKlasse());

    }
    @Test
    public void speichereKlasse() throws Exception {
        FileManager.speichereJava("public class RomanNumberConverter {\n" +
                "            }\n" +
                "        ", "javaTestFile");
    }


}