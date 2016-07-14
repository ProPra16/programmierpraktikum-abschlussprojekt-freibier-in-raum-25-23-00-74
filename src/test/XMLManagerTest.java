import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by jan on 30.06.16.
 */
public class XMLManagerTest {
    @Test
    public void testGetAufgabename() throws Exception {
        XMLManager.XMLManager("XMLAufgabe");
        assertEquals("Römische Zahlen", XMLManager.getAufgabename());
    }

    @Test
    public void testGetAufgabenstellung() throws Exception{
        XMLManager.XMLManager("XMLAufgabe");
        assertEquals("Konvertiert arabische in römische Zahlen.", XMLManager.getAufgabenstellung());
    }

    @Test
    public void testgetBabysteps() throws Exception{
        XMLManager.XMLManager("XMLAufgabe");
        assertEquals(true, XMLManager.getBabysteps());
    }

    @Test
    public void testgetAttd() throws Exception{
        XMLManager.XMLManager("XMLAufgabe");
        assertEquals(true, XMLManager.getAttd());
    }

    @Test
    public void testgetBabystepsTime() throws Exception{
        XMLManager.XMLManager("XMLAufgabe");
        assertEquals(100,XMLManager.getBabystepsTime());
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