import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;
/**
 * Created by jan on 30.06.16.
 */
public class XMLManagerTest {
    @Test
    public void testGetAufgabename() throws Exception {
        XMLManager.XMLManager(new File(getClass().getResource("/XMLAufgabe.xml").toURI()).getAbsolutePath());
        assertEquals("Romische Zahlen", XMLManager.getAufgabename());
    }

    @Test
    public void testGetAufgabenstellung() throws Exception{
        XMLManager.XMLManager(new File(getClass().getResource("/XMLAufgabe.xml").toURI()).getAbsolutePath());
        assertEquals("Konvertiert arabische in romische Zahlen.", XMLManager.getAufgabenstellung());
    }

    @Test
    public void testgetBabysteps() throws Exception{
        XMLManager.XMLManager(new File(getClass().getResource("/XMLAufgabe.xml").toURI()).getAbsolutePath());
        assertEquals(false, XMLManager.getBabysteps());
    }

    @Test
    public void testgetAttd() throws Exception{
        XMLManager.XMLManager(new File(getClass().getResource("/XMLAufgabe.xml").toURI()).getAbsolutePath());
        assertEquals(true, XMLManager.getAttd());
    }

    @Test
    public void testgetBabystepsTime() throws Exception{
        XMLManager.XMLManager(new File(getClass().getResource("/XMLAufgabe.xml").toURI()).getAbsolutePath());
        assertEquals(26,XMLManager.getBabystepsTime());
    }

    @Test
    public void testGetKlasse() throws Exception {
        XMLManager.XMLManager(new File(getClass().getResource("/XMLAufgabe.xml").toURI()).getAbsolutePath());
        assertEquals("\n" +
                "            public class RomanNumberConverter {\n" +
                "            }\n" +
                "        ",XMLManager.getKlasse());
    }
}