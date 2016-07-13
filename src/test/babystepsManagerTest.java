/**
 * Created by Martin on 13.07.2016.
 */

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class BabystepsManagerTest {

    @Test
    public void timeisoverTest(){
        Timemanager t = new Timemanager();
        t.setStartTime(0);
        BabystepsManager babe = new BabystepsManager(t);
        assertEquals(true,babe.timeisover());
    }
}
