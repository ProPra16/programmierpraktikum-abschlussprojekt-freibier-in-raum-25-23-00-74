
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
    static Collection<Code> codes;
    static InterfaceManager im;
    int a = 10;

    @BeforeClass //Fake StateManager
    public static void setup(){
        Code test = new Code("public static void main(String[] args){\n\n}","Testaufgabenstellung","Aufgabe1");
        codes = new ArrayList<>();
        codes.add(test);
        im = mock(InterfaceManager.class);
    }

    @Test
    public void setStartTimetest(){
        statem = new StateManager(codes,im);
        TimeManager2 manager = new TimeManager2(statem);
        manager.setStartTime(a);
        assertEquals(10,manager.getStartTime());
    }

    @Test
    public void runTimetest() throws InterruptedException {
        state2 = new StateManager(codes,im);
        TimeManager2 manager = new TimeManager2(state2);
        manager.setStartTime(4);
        manager.runtime();
        assertEquals(0,manager.getCurrentTime());
    }

    @Test
    public void zeroruntest() {
        statem = new StateManager(codes,im);
        TimeManager2 manager = new TimeManager2(statem);
        manager.setStartTime(0);
        assertEquals(true,manager.zeroValueCheck());
    }

    @Test
    public void runonesteptest() {
        statem = new StateManager(codes,im);
        TimeManager2 manager = new TimeManager2(statem);
        manager.setStartTime(3);
        manager.runOneStep();
        assertEquals(2,manager.getCurrentTime());
    }

}
