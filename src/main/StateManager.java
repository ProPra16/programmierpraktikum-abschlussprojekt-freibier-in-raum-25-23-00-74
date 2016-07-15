import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StateManager {
    CodeManager cm;
    InterfaceManager im;
    String currentState;
    boolean isgoingbackwardallwed = false;
    boolean atdd;

    public StateManager(Code code, Code test, Code akTest, InterfaceManager im){
        this.im=im;
        cm = new CodeManager();
        cm.setCode(code);
        cm.setTest(test);
        cm.setakTest(akTest);
        printToGUI(code,akTest);
        currentState = "ATDD";
        atdd = true;
    }

    public StateManager(Code code, Code test, InterfaceManager im){
        this.im=im;
        cm = new CodeManager();
        cm.setCode(code);
        cm.setTest(test);
        printToGUI(code,test);
        currentState="Red";
    }

    private void printToGUI(Code code, Code tests){
            im.setCode("/* " + code.getAufgabenstellung() +"*/\n\n" +  code.getKlasse());
            im.setTestCode(tests.getKlasse());
    }

    void update(boolean updateATTD){
        //Update Code
        cm.updateCode(im.getCode());
        //Update Tests
        if(updateATTD){
            cm.updateakTest(im.getTestCode());
        }
        else {
            cm.updateTest(im.getTestCode());
        }
    }

    private void replaceBackupCode(){
        //Replace backup code with current code
        cm.CodeToNextStep();
        cm.TestToNextStep();
    }

    public void toNextStep(){
        switch(currentState){
            case "ATDD": fromATTDtoRed(); break;
            case "Red": fromRedToGreen(); break;
            case "Green": codeAndTestsWorkToNextPhase(); break;
            case "Refactor": codeAndTestsWorkToNextPhase(); break;
        }
    }

    private void fromRedToGreen(){
        update(false);
        RealCompileManager compiler = new RealCompileManager(cm.getCode(),cm.getTest().getDateiname(),cm.getTest().getKlasse());
        if(isgoingbackwardallwed) compiler.runCompiler();
        else compiler.compileTest();
        if(compiler.compiles()){
            if(compiler.getNumberOfFailedTests()==1){
                replaceBackupCode();
                currentState = "Green";
            }
        }
        else if(!compiler.compiles()){
            replaceBackupCode();
            currentState = "Green";
        }
        else{
            im.writeToConsole(compiler.getCompileErrors());
            im.writeToConsole(compiler.getTestErrors());
        }
    }

    public void fromGreenToRed(){
        //Reset Code
        cm.resetCode();
        printToGUI(cm.getCode(),cm.getTest());
        currentState = "Red";
    }

    public void fromRedToRefactor(){
        cm.resetTest();
        printToGUI(cm.getCode(),cm.getTest());
        if(isgoingbackwardallwed) currentState = "Refactor";
    }

    public void resetCodetoStart(){
        //Reset Code für babysteps
        if(currentState.equalsIgnoreCase("green"))  fromGreenToRed();
        if(currentState.equalsIgnoreCase("red"))    fromRedToRefactor();
    }


    private void codeAndTestsWorkToNextPhase(){
        //Check if all code compiles
        update(false);
        RealCompileManager compiler = new RealCompileManager(cm.getCode(),cm.getTest().getDateiname(),cm.getTest().getKlasse());
        compiler.runCompiler();
        if(compiler.compiles()&&compiler.getNumberOfFailedTests()==0) {
            replaceBackupCode();
            if(currentState.equalsIgnoreCase("green")){
                currentState = "Refactor";
            }
            else if(currentState.equalsIgnoreCase("refactor")&&atdd){
                currentState = "ATDD";
                printToGUI(cm.getCode(),cm.getakTest());
                fromATTDtoRed();
            }
            else{
                currentState = "Red";
                isgoingbackwardallwed = true;
            }
        }
        else{
            im.writeToConsole(compiler.getCompileErrors());
            if(compiler.compiles())
                im.writeToConsole(compiler.getTestErrors());
        }
    }

    public void fromATTDtoRed(){
        update(true);
            RealCompileManager compiler = new RealCompileManager(cm.getCode(),cm.getakTest().getDateiname(),cm.getakTest().getKlasse());
            compiler.runCompiler();
            boolean tests = false;
            if(compiler.compiles()) tests = compiler.getNumberOfFailedTests()==1;
            if(!compiler.compiles()||tests){
                    currentState="Red";
                    printToGUI(cm.getCode(),cm.getTest());
                }
        }

    public String getCurrentState(){
        return currentState;
    }

    public Code getAkTest()
    {
        return cm.getakTest();
    }
}
