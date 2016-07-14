import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Jan on 14.07.2016.
 */
public class RealCompileManagerTest {
    static RealCompileManager foobar;
    @BeforeClass
    public static void setup(){
        foobar = new RealCompileManager(new Code("" +
                "public class Fooclass" +
                "{" +
                "public static int bar(){return 5;}}","blabla","Fooclass" ),"myTest", "import org.junit.Test;\n" +
                "import static org.junit.Assert.*;\n\npublic class myTest{\n" +
                "\n" +
                "@Test\n" +
                "public void firstTest(){\n" +
                "assertEquals(5,Fooclass.bar());\n" +
                "}\n}");
    }

    @Test
    public void testCompiles() throws Exception {
        foobar.runCompiler();
        assertEquals(true,foobar.compiles());
    }

    @Test
    public void hasTestErrors(){
        foobar = new RealCompileManager(new Code("" +
                "public class Fooclass" +
                "{" +
                "public static int bar(){return 5;}}","blabla","Fooclass" ),"myTest", "import org.junit.Test;\n" +
                "import static org.junit.Assert.*;\n\npublic class myTest{\n" +
                "\n" +
                "@Test\n" +
                "public void firstTest(){\n" +
                "assertEquals(6,Fooclass.bar());\n" +
                "}\n}");
        foobar.runCompiler();
        assertFalse("".equals(foobar.getTestErrors()));
    }

}