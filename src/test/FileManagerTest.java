import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jan on 08.07.16.
 */
public class FileManagerTest {
    @Test
    public void setNodeValue() throws Exception {
        FileManager.ändereNodeValue("aufgabename","Testname","FileManagerTest");
    }

}