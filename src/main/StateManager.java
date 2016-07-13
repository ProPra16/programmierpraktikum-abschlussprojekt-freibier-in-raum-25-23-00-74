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
        cm.addTest("import org.junit.Test;\n" +
                "import static org.junit.Assert.*;public class firstTest{\n@Test\n public void falset(){assertEquals(true,false);}}","firstTest");
        testnames.add("firstTest");
        printToGUI();
        currentState = "Red";
        im.writeToConsole("Schreiben Sie einen Test der fehlschlaegt!\n");
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
            Code c = new Code(im.getCode(s),cm.getCode(s).getAufgabenstellung(),s);
            cm.addCode(c,s);
        }
        //Update Tests
        for(String s : testnames){
            cm.addTest(im.getTestCode(s),s);
        }
    }

    private void replaceBackupCode(){
        //Replace backup code with current code
        for(String s : codenames){
            cm.CodeToNextStep(s);
        }
        for(String s : testnames){
            cm.TestToNextStep(s);
        }
    }

    public void toNextStep(){
        switch(currentState){
            case "Red": fromRedToGreen(); break;
            case "Green": fromGreenToRefactor(); break;
            case "Refactor": fromRefactorToRed(); break;
        }
    }

    private void fromRedToGreen(){
        update();
        boolean codeCompiles=true;
        String consoleResult = "";
        //Check if any code has compileErrors
        for(String s : codenames){
            CompileManager compiler = new CompileManager(s,cm.getCode(s).getKlasse(),false);
            codeCompiles=compiler.IncludeCompileErrors();
            if(!codeCompiles) consoleResult += compiler.getCompilingErrors();
        }
        //Check if exactly 1 test fails
        int count = 0;
        for(String s : testnames){
                CompileManager compiler = new CompileManager(s, cm.getTest(s), true);
                if(compiler.IncludeCompileErrors()){
                    consoleResult+=compiler.getCompilingErrors();
                    continue;
                }
                count += compiler.returnFailedTestsnumber();
                consoleResult += compiler.getTestErrors();
        }
        boolean oneFailedTest = count == 1;
        if(consoleResult.equals("")) consoleResult = "Alle Tests funktionieren. Schreiben Sie einen Test der nicht funktioniert!";
        if(!codeCompiles||oneFailedTest){
            replaceBackupCode();
            currentState = "Green";
        }
        im.writeToConsole(consoleResult);
    }

    public void fromGreenToRed(){
        //Reset Code
        for(String s : codenames){
            cm.resetCode(s);
        }
        printToGUI();
        currentState = "Red";
        im.writeToConsole("Schreiben Sie einen Test der fehlschlaegt!");
    }

    public void resetCodetoStart(){
        //Reset Code
        for(String s : codenames){
            cm.resetCode(s);
        }
        printToGUI();
    }

    private void fromGreenToRefactor(){
        //Check if all code compiles
        update();
        int countCompileFailures = 0;
        String consoleResults = "";
        for(String s : codenames){
            CompileManager compiler = new CompileManager(s,cm.getCode(s).getKlasse(),false);
            if(compiler.IncludeCompileErrors()){
                countCompileFailures++;
                consoleResults+=compiler.getCompilingErrors();
            }
        }
        boolean codeCompiles = countCompileFailures==0;
        //Check if all tests work
        String testErrors = "";
        for(String s : testnames){
            CompileManager compiler = new CompileManager(s,cm.getTest(s),true);
            testErrors+=compiler.getTestErrors();
        }
        boolean testsRun = testErrors.equals("");
        consoleResults += testErrors;
        if(codeCompiles&&testsRun) {
            consoleResults = "Alle tests laufen durch!";
            replaceBackupCode();
            if(currentState.equalsIgnoreCase("green")){
                currentState = "Refactor";
                consoleResults += "Sie koennen ihren Code jetzt verbessern!";
            }
            else{
                consoleResults += "Schreiben Sie einen neuen Test der fehlschlaegt!";
                currentState = "Red";
            }
        }
        im.writeToConsole(consoleResults);
    }

    private void fromRefactorToRed(){
        fromGreenToRefactor();
    }

    public String getCurrentState(){
        return currentState;
    }
}
