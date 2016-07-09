import org.junit.Test;
import static org.junit.Assert.*;

public class CodeManagerTest {
    @Test
    public void addGetCode(){
        Code c = new Code("Klasse","Aufgabenstellung","Dateiname");
        c.setAufgabenstellung("Aufgabenstellung");
        c.setDateiname("Dateiname");
        c.setKlasse("Klasse");
        CodeManager cm = new CodeManager();
        cm.addCode(c,"Dateiname");
        Code d = cm.getCode("Dateiname");
        assertEquals(c,d);
    }

    @Test
    public void addGetTest(){
        String test = "Test";
        CodeManager cm = new CodeManager();
        cm.addTest(test,"first Test");
        assertEquals(test,cm.getTest("first Test"));
    }

    @Test
    public void resetCode(){
        Code c = new Code("Klasse","Aufgabenstellung","Dateiname");
        c.setAufgabenstellung("Aufgabenstellung");
        c.setDateiname("Dateiname");
        c.setKlasse("Klasse");
        CodeManager cm = new CodeManager();
        cm.addCode(c,"Dateiname");
        c.setKlasse("NotKlasse");
        cm.CodeToNextStep("Dateiname");
        cm.addCode(c,"Dateiname");
        cm.resetCode("Dateiname");
        c.setKlasse("Klasse");
        assertEquals(c,cm.getCode("Dateiname"));
    }

    @Test
    public void resetTest(){
        CodeManager cm = new CodeManager();
        cm.addTest("Test", "Testname");
        cm.TestToNextStep("Testname");
        cm.addTest("Not Test", "Testname");
        cm.resetTest("Testname");
        assertEquals("Test", cm.getTest("Testname"));
    }
}

