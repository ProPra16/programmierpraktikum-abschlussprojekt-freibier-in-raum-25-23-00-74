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
    static Code code;
    static InterfaceManager im;
    @BeforeClass
    public static void setup(){
        code = new Code("public static void main(String[] args){\n\n}","Testaufgabenstellung","Aufgabe1");
        im = mock(InterfaceManager.class);
    }

    @Test
    public void constructor(){
        sm = new StateManager(code,new Code("","","Tests"),im);
        verify(im).setCode("public static void main(String[] args){\n\n}");
    }

    @Test
    public void redToGreen(){
        sm = new StateManager(code,new Code("","","Tests"),im);
        when(im.getCode()).thenReturn("public class Aufgabe1{\n public static void main(String[] args){\n\n}\n}");
        when(im.getTestCode()).thenReturn("import org.junit.Test;\n import static org.junit.Assert.*;\n public class Tests{\n @Test\n public void alwaysFalse(){\n assertEquals(true,false);\n} \n}");
        sm.toNextStep();
        assertEquals("Green",sm.getCurrentState());
    }

    @Test
    public void greenToRefactor(){
        sm = new StateManager(code,new Code("","","Tests"),im);
        when(im.getCode()).thenReturn("public class Aufgabe1{\n public static void main(String[] args){\n\n}\n}");
        when(im.getTestCode()).thenReturn("import org.junit.Test;\n import static org.junit.Assert.*;\n public class Tests{\n @Test\n public void alwaysFalse(){\n assertEquals(true,false);\n} \n}");
        sm.toNextStep();
        when(im.getTestCode()).thenReturn("import org.junit.Test;\n import static org.junit.Assert.*;\n public class Tests{\n @Test\n public void alwaysTrue(){\n assertEquals(true,true);\n} \n}");
        sm.toNextStep();
        assertEquals("Refactor",sm.getCurrentState());
    }

    @Test
    public void refactorToRed(){
        sm = new StateManager(code,new Code("","","Tests"),im);
        when(im.getCode()).thenReturn("public class Aufgabe1{\n public static void main(String[] args){\n\n}\n}");
        when(im.getTestCode()).thenReturn("import org.junit.Test;\n import static org.junit.Assert.*;\n public class Tests{\n @Test\n public void alwaysFalse(){\n assertEquals(true,false);\n} \n}");
        sm.toNextStep();
        when(im.getTestCode()).thenReturn("import org.junit.Test;\n import static org.junit.Assert.*;\n public class Tests{\n @Test\n public void alwaysTrue(){\n assertEquals(true,true);\n} \n}");
        sm.toNextStep();
        sm.toNextStep();
        assertEquals("Red",sm.getCurrentState());
    }

    @Test
    public void GreenToRed(){
        sm = new StateManager(code,new Code("","","Tests"),im);
        when(im.getCode()).thenReturn("public class Aufgabe1{\n public static void main(String[] args){\n\n}\n}");
        when(im.getTestCode()).thenReturn("import org.junit.Test;\n import static org.junit.Assert.*;\n public class Tests{\n @Test\n public void alwaysFalse(){\n assertEquals(true,false);\n} \n}");
        sm.toNextStep();
        sm.fromGreenToRed();
        assertEquals("Red",sm.getCurrentState());
    }

    @Test
    public void attdToRed(){
        sm = new StateManager(code,new Code("","",""),new Code("","","Aktzeptanztest"),im);
        when(im.getCode()).thenReturn("public class Aufgabe1{\n public static void main(String[] args){\n\n}\n}");
        when(im.getTestCode()).thenReturn("import org.junit.Test;\n" +
                "import static org.junit.Assert.*;\n\npublic class AktzeptanzTest{\n\n@Test\npublic void firstTest(){\nassertEquals(true,false);\n}\n}");
        sm.toNextStep();
        assertEquals("Red",sm.getCurrentState());
    }

    @Test
    public void attdTestcompleted(){
        sm = new StateManager(code,new Code("","",""),new Code("","","Aktzeptanztest"),im);
        when(im.getCode()).thenReturn("public class Aufgabe1{\n public static void main(String[] args){\n\n}\n}");
        when(im.getTestCode()).thenReturn("import org.junit.Test;\n" +
                "import static org.junit.Assert.*;\n\npublic class AktzeptanzTest{\n\n@Test\npublic void firstTest(){\nassertEquals(true,true);\n}\n}");
        sm.toNextStep();
        assertEquals("ATTD",sm.getCurrentState());
    }
}
