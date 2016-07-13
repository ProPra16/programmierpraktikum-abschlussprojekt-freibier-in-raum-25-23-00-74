import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StateManager {
    CodeManager cm;
    InterfaceManager im;
    List<String> codenames;
    List<String> testnames;
    List<String> akTestnames;
    String currentState;
    boolean attd;

    public StateManager(Collection<Code> codes, InterfaceManager im, boolean attd){
        this.attd=attd;
        codenames = new ArrayList<>();
        testnames = new ArrayList<>();
        akTestnames = new ArrayList<>();
        cm = new CodeManager();
        this.im = im;
        for(Code c : codes){
            cm.addCode(c, c.getDateiname());
            codenames.add(c.getDateiname());
        }
        cm.addTest("import org.junit.Test;\n" +
                "import static org.junit.Assert.*;\n\npublic class Tests{\n" +
                "\n" +
                "@Test\n" +
                "public void firstTest(){\n" +
                "assertEquals(true,true);\n" +
                "}\n}","Test");
        testnames.add("Tests");
        cm.addakTest("import org.junit.Test;\n" +
                "import static org.junit.Assert.*;\n\npublic class AktzeptanzTest{\n\n@Test\npublic void firstTest(){\nassertEquals(true,true);\n}\n}","AktzeptanzTest");
        akTestnames.add("AktzeptanzTest");
        if(attd) {
            printToGUI(codenames, akTestnames);
            currentState = "ATTD";
            im.writeToConsole("Schreiben Sie einen Aktzeptanztest der fehlschlaegt!\n");
        }
        else {
            printToGUI(codenames, testnames);
            currentState = "Red";
            im.writeToConsole("Schreiben Sie einen Test der fehlschlaegt!\n");
        }
    }

    private void printToGUI(List<String> code, List<String> test){
        for(String s : code){
            im.setCode(s,cm.getCode(s).getKlasse());
        }
        for(String s : test){
            im.setTestCode(s,cm.getTest(s));
        }
    }

    private void update(boolean updateATTD){
        //Update Code
        for(String s : codenames){
            Code c = new Code(im.getCode(s),cm.getCode(s).getAufgabenstellung(),s);
            cm.addCode(c,s);
        }
        //Update Tests
        if(updateATTD){
            for(String s : akTestnames){
                cm.addakTest(im.getTestCode(s),s);
            }
        }
        else {
            for(String s : testnames){
                cm.addTest(im.getTestCode(s),s);
            }
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
            case "ATTD": fromATTDtoRed(); break;
            case "Red": fromRedToGreen(); break;
            case "Green": codeAndTestsWorkToNextPhase(); break;
            case "Refactor": codeAndTestsWorkToNextPhase(); break;
        }
    }

    private void fromRedToGreen(){
        update(false);
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
        printToGUI(codenames,testnames);
        currentState = "Red";
        im.writeToConsole("Schreiben Sie einen Test der fehlschlaegt!");
    }

    private void codeAndTestsWorkToNextPhase(){
        //Check if all code compiles
        update(false);
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
            consoleResults = "Alle tests laufen durch und ihr Code kompiliert!";
            replaceBackupCode();
            if(currentState.equalsIgnoreCase("green")){
                currentState = "Refactor";
                consoleResults += "Sie koennen ihren Code jetzt verbessern!";
            }
            else if(currentState.equalsIgnoreCase("refactor")&&attd){
                currentState = "ATTD";
                printToGUI(codenames,akTestnames);
                fromATTDtoRed();
            }
            else{
                consoleResults += "Schreiben Sie einen neuen Test der fehlschlaegt!";
                currentState = "Red";
            }
        }
        im.writeToConsole(consoleResults);
    }

    public void fromATTDtoRed(){
        update(true);
        for(String s : akTestnames){
            CompileManager compiler = new CompileManager(s,cm.getakTest(s),true);
            if(!compiler.IncludeCompileErrors()){
                if(compiler.returnFailedTestsnumber()==0) im.writeToConsole("Der Aktzeptanztest ist erfullt. Schreiben Sie einen neuen!");
                else if(compiler.returnFailedTestsnumber()!=1) im.writeToConsole("Mehr als ein Aktzeptanztest schlagt fehl. Es darf nur ein Aktzeptanztest fehlschlagen!");
                else{
                    im.writeToConsole(compiler.getTestErrors());
                    currentState="Red";
                    im.writeToConsole("Schreiben Sie einen Unittest der fehlschlagt.");
                    printToGUI(codenames,testnames);
                }
            }
        }
    }

    public String getCurrentState(){
        return currentState;
    }
}
