/**
 * Created by jan on 30.06.16.
 */
class XMLManagerTest extends GroovyTestCase {
    void testGetAufgabename() {
        XMLManager.XMLManager()
        assertEquals("Römische Zahlen", XMLManager.getAufgabename())

    }

    void testGetAufgabenstellung() {
        XMLManager.XMLManager()
        assertEquals("Konvertiert arabische in römische Zahlen.", XMLManager.getAufgabenstellung())

    }

    void testGetKlasse() {
        XMLManager.XMLManager()
        assertEquals("\n" +
                "                public class RomanNumberConverter {\n" +
                "                }\n" +
                "            ",XMLManager.getKlasse())
    }
}
