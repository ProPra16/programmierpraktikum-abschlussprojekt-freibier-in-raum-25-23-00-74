import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Martin on 13.07.2016.
 */
public class TimeManagerTest {
    int a = 10;

    @Test
    public void setStartTimetest(){
        Timemanager manager = new Timemanager();
        manager.setStartTime(a);
        assertEquals(10,manager.getStartTime());
    }

    @Test
    public void runTimetest() throws InterruptedException {
        Timemanager manager = new Timemanager();
        manager.setStartTime(a-6);
        manager.runtime();
        assertEquals(0,manager.getCurrentTime()+1);
    }

}
