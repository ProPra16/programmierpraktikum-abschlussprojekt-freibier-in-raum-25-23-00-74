import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jan on 08.07.16.
 */
public class FileManagerTest {
    @Test
    public void setNodeValue() throws Exception {
        FileManager.changeNodeValue("aufgabename","test test","FileManagerTest");
    }

    @Test
    public void newFile() throws Exception {
        FileManager.newFile("newFileTest","123455");
    }

    @Test
    public void openFile() throws Exception {
        FileManager.openFile("FileManagerTest");
        assertEquals("test test",XMLManager.getAufgabename());
        assertEquals("Konvertiert arabische in römische Zahlen.",XMLManager.getAufgabenstellung());
        assertEquals("\n" +
                "            public class RomanNumberConverter {\n" +
                "            }\n" +
                "        ",XMLManager.getKlasse());

    }


}