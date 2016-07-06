import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StateManagerTest {
    StateManager sm;
    static Collection<Code> codes;
    static InterfaceManager im;
    @BeforeClass
    public static void setup(){
        Code test = new Code();
        test.setKlasse("public static void main(String[] args){\n\n}");
        test.setAufgabenstellung("Testaufgabenstellung");
        test.setDateiname("Aufgabe1");
        codes = new ArrayList<>();
        codes.add(test);
        im = mock(InterfaceManager.class);
    }

    @Test
    public void constructor(){
        sm = new StateManager(codes,im);
        verify(im).setCode("Aufgabe1","public static void main(String[] args){\n\n}");
    }

    @Test
    public void redToGreen(){
        sm = new StateManager(codes, im);
        when(im.getCode("Aufgabe1")).thenReturn("public class Aufgabe1{\n public static void main(String[] args){\n\n}\n}");
        when(im.getTestCode("firstTest")).thenReturn("import org.junit.Test;\n import static org.junit.Assert.*;\n public class firstTest{\n @Test\n public void alwaysFalse(){\n assertEquals(true,false);\n} \n}");
        sm.fromRedToGreen();
        assertEquals("Green",sm.getCurrentState());
    }

    @Test
    public void greenToRefactor(){
        sm = new StateManager(codes, im);
        when(im.getCode("Aufgabe1")).thenReturn("public class Aufgabe1{\n public static void main(String[] args){\n\n}\n}");
        when(im.getTestCode("firstTest")).thenReturn("import org.junit.Test;\n import static org.junit.Assert.*;\n public class firstTest{\n @Test\n public void alwaysFalse(){\n assertEquals(true,false);\n} \n}");
        sm.fromRedToGreen();
        when(im.getTestCode("firstTest")).thenReturn("import org.junit.Test;\n import static org.junit.Assert.*;\n public class firstTest{\n @Test\n public void alwaysTrue(){\n assertEquals(true,true);\n} \n}");
        sm.fromGreenToRefactor();
        assertEquals("Refactor",sm.getCurrentState());
    }

    @Test
    public void refactorToRed(){
        sm = new StateManager(codes, im);
        when(im.getCode("Aufgabe1")).thenReturn("public class Aufgabe1{\n public static void main(String[] args){\n\n}\n}");
        when(im.getTestCode("firstTest")).thenReturn("import org.junit.Test;\n import static org.junit.Assert.*;\n public class firstTest{\n @Test\n public void alwaysFalse(){\n assertEquals(true,false);\n} \n}");
        sm.fromRedToGreen();
        when(im.getTestCode("firstTest")).thenReturn("import org.junit.Test;\n import static org.junit.Assert.*;\n public class firstTest{\n @Test\n public void alwaysTrue(){\n assertEquals(true,true);\n} \n}");
        sm.fromGreenToRefactor();
        sm.fromRefactorToRed();
        assertEquals("Red",sm.getCurrentState());
    }
}
