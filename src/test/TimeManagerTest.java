
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;

/**
 * Created by Martin on 13.07.2016.
 */
public class TimeManagerTest {
    StateManager statem;
    StateManager state2;
    static Code code;
    static InterfaceManager im;
    static Code testcode;
    int a = 10;

    @BeforeClass //Fake StateManager
    public static void setup(){
        code = new Code("public static void main(String[] args){\n\n}","Testaufgabenstellung","Aufgabe1");
        testcode = new Code("","","");
        im = mock(InterfaceManager.class);
    }

    @Test
    public void setStartTimetest(){
        statem = new StateManager(code,testcode,im);
        TimeManager manager = new TimeManager(statem);
        manager.setStartTime(a);
        assertEquals(10,manager.getStartTime());
    }

    @Test
    public void runTimetest() throws InterruptedException {
        state2 = new StateManager(code,testcode,im);
        TimeManager manager = new TimeManager(state2);
        manager.setStartTime(4);
        manager.runtime();
        assertEquals(0,manager.getCurrentTime());
    }

    @Test
    public void zeroruntest() {
        statem = new StateManager(code,testcode,im);
        TimeManager manager = new TimeManager(statem);
        manager.setStartTime(0);
        assertEquals(true,manager.zeroValueCheck());
    }

    @Test
    public void runonesteptest() {
        statem = new StateManager(code,testcode,im);
        TimeManager manager = new TimeManager(statem);
        manager.setStartTime(3);
        manager.runOneStep();
        assertEquals(2,manager.getCurrentTime());
    }

}
