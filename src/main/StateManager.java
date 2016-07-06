import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StateManager {
    CodeManager cm;
    InterfaceManager im;
    List<String> codenames;
    List<String> testnames;
    String currentState;

    public StateManager(Collection<Code> codes, InterfaceManager im){
        codenames = new ArrayList<>();
        testnames = new ArrayList<>();
        cm = new CodeManager();
        this.im = im;
        for(Code c : codes){
            cm.addCode(c, c.getDateiname());
            codenames.add(c.getDateiname());
        }
        cm.addTest("","first Test");
        testnames.add("first Test");
        printToGUI();
        currentState = "Red";
    }

    private void printToGUI(){
        for(String s : codenames){
            im.setCode(s,cm.getCode(s).getKlasse());
        }
        for(String s : testnames){
            im.setTestCode(s,cm.getTest(s));
        }
    }

    private void update(){
        //Update Code
        for(String s : codenames){
            Code c = new Code();
            c.setDateiname(s);
            c.setAufgabenstellung(cm.getCode(s).getAufgabenstellung());
            c.setKlasse(im.getCode(s));
            cm.addCode(c,s);
        }
        //Update Tests
        for(String s : testnames){
            cm.addTest(im.getTestCode(s),s);
        }
    }

    private void toNextStep(){
        //Replace backup code with current code
        for(String s : codenames){
            cm.CodeToNextStep(s);
        }
        for(String s : testnames){
            cm.TestToNextStep(s);
        }
    }

    public void fromRedToGreen(){
        update();
        boolean codeCompiles=true;
        //Check if any code has compileErrors
        for(String s : codenames){
            CompileManager compiler = new CompileManager(s,cm.getCode(s).getKlasse(),false);
            codeCompiles=compiler.IncludeCompileErrors();
        }
        //Check if exactly 1 test fails
        int count = 0;
        for(String s : testnames){
            CompileManager compiler = new CompileManager(s,cm.getTest(s),true);
            count+=compiler.returnFailedTestsnumber();
        }
        boolean oneFailedTest = count == 1;
        if(!codeCompiles||oneFailedTest){
            toNextStep();
            currentState = "Green";
        }
    }

    public void fromGreenToRed(){
        //Reset Code
        for(String s : codenames){
            cm.resetCode(s);
        }
    }

    public void fromGreenToRefactor(){
        //Check if all code compiles
        update();
        int countCompileFailures = 0;
        for(String s : codenames){
            CompileManager compiler = new CompileManager(s,cm.getCode(s).getKlasse(),false);
            if(compiler.IncludeCompileErrors()) countCompileFailures++;
        }
        boolean codeCompiles = countCompileFailures==0;
        //Check if all tests work
        String testErrors = "";
        for(String s : testnames){
            CompileManager compiler = new CompileManager(s,cm.getTest(s),true);
            testErrors+=compiler.getTestErrors();
        }
        boolean testsRun = testErrors.equals("");
        if(codeCompiles&&testsRun) {
            toNextStep();
            if(currentState.equalsIgnoreCase("green")) currentState = "Refactor";
            else currentState = "Red";
        }
    }

    public void fromRefactorToRed(){
        fromGreenToRefactor();
    }

    public String getCurrentState(){
        return currentState;
    }
}
