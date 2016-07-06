import java.util.Collection;

public class StateManager {
    CodeManager cm;
    InterfaceManager im;

    public StateManager(Collection<Code> codes, InterfaceManager im){
        cm = new CodeManager();
        this.im = im;
        for(Code c : codes){
            cm.addCode(c, c.getDateiname());
        }
        cm.addTest("","first Test");
    }


}
