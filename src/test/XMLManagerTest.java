import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by jan on 30.06.16.
 */
public class XMLManagerTest {
    @Test
    public void testGetAufgabename() throws Exception {
        XMLManager.XMLManager("XMLAufgabe");
        assertEquals("Romische Zahlen", XMLManager.getAufgabename());
    }

    @Test
    public void testGetAufgabenstellung() throws Exception{
        XMLManager.XMLManager("XMLAufgabe");
        assertEquals("Konvertiert arabische in romische Zahlen.", XMLManager.getAufgabenstellung());
    }

    @Test
    public void testGetKlasse() throws Exception {
        XMLManager.XMLManager("XMLAufgabe");
        assertEquals("\n" +
                "                public class RomanNumberConverter {\n" +
                "                }\n" +
                "            ",XMLManager.getKlasse());
    }
}