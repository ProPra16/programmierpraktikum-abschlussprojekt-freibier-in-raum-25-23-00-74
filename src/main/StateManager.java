import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StateManager {
    CodeManager cm;
    InterfaceManager im;
    String currentState;
    Boolean isgoingbackwardallwed = false;
    boolean attd;

    public StateManager(Code code, Code test, Code akTest, InterfaceManager im){
        cm = new CodeManager();
        cm.setCode(code);
        cm.setTest(test);
        cm.setakTest(akTest);
        printToGUI(code,akTest);
        currentState = "ATDD";
    }

    public StateManager(Code code, Code test, InterfaceManager im){
        cm = new CodeManager();
        cm.setCode(code);
        cm.setTest(test);
        printToGUI(code,test);
        currentState="Red";
    }

    private void printToGUI(Code code, Code tests){
            im.setCode(code.getKlasse());
            im.setTestCode(tests.getKlasse());
    }

    private void update(boolean updateATTD){
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
            case "ATTD": fromATTDtoRed(); break;
            case "Red": fromRedToGreen(); break;
            case "Green": codeAndTestsWorkToNextPhase(); break;
            case "Refactor": codeAndTestsWorkToNextPhase(); break;
        }
    }

    private void fromRedToGreen(){
        update(false);
        RealCompileManager compiler = new RealCompileManager(cm.getCode(),cm.getTest().getDateiname(),cm.getTest().getKlasse());
        compiler.runCompiler();
        if(!compiler.compiles()||compiler.getNumberOfFailedTests()==1){
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
        currentState = "Refactor";
    }

    public void resetCodetoStart(){
        //Reset Code für babysteps
        if(currentState.equals("green"))  fromGreenToRed();
        if(currentState.equals("red"))    fromRedToRefactor();
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
            else if(currentState.equalsIgnoreCase("refactor")&&attd){
                currentState = "ATTD";
                printToGUI(cm.getCode(),cm.getakTest());
                fromATTDtoRed();
            }
            else{
                currentState = "Red";
            }
        }
        else{
            im.writeToConsole(compiler.getCompileErrors());
            im.writeToConsole(compiler.getTestErrors());
        }
    }

    public void fromATTDtoRed(){
        update(true);
            RealCompileManager compiler = new RealCompileManager(cm.getCode(),cm.getakTest().getDateiname(),cm.getakTest().getKlasse());
            if(compiler.compiles()){
                if(compiler.getNumberOfFailedTests()==1){
                    currentState="Red";
                    printToGUI(cm.getCode(),cm.getTest());
                }
                else{
                    im.writeToConsole(compiler.getCompileErrors());
                    im.writeToConsole(compiler.getTestErrors());
            }
        }
    }

    public String getCurrentState(){
        return currentState;
    }
}
