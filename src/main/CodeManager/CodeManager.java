package CodeManager;
import java.util.HashMap;

public class CodeManager {
    private HashMap<String, Code> code;

    public CodeManager(){
        code = new HashMap<>();
    }

    public void addCode(Code c){
        code.put("Code",c);
    }

    public void addTest(Code c){
        code.put("Test",c);
    }

    private boolean passAllTests(){
        boolean pass = false;
        for(String key : code.keySet()){
            if(key.equalsIgnoreCase("test")) {
                Code value = code.get(key);
                pass = value.run();
            }
        }
        return pass;
    }

    private boolean oneTestfails(){
        int count = 0;
        for(String key : code.keySet()){
            if(key.equalsIgnoreCase("test")) {
                Code value = code.get(key);
                if(!value.run()) count++;
            }
        }
        return count==1;
    }

    private boolean codeCompiles(){
        boolean compiles = false;
        for(String key : code.keySet()){
            if(key.equalsIgnoreCase("code")) {
                Code value = code.get(key);
                compiles = value.compileable();
            }
        }
        return compiles;
    }

    public void fromRedToGreen(){
        if(!oneTestfails()||!codeCompiles()){
            for(String key : code.keySet()){
                if(key.equalsIgnoreCase("test")) {
                    Code value = code.get(key);
                    value.nextStep();
                }
            }
        }
    }

    public void backToRed(){
        for(String key : code.keySet()){
            if(key.equalsIgnoreCase("code")) {
                Code value = code.get(key);
                value.reset();
            }
        }
    }

    public void fromGreenToRefactor(){
        if(passAllTests()&&codeCompiles()){
            for(String key : code.keySet()){
                if(key.equalsIgnoreCase("code")) {
                    Code value = code.get(key);
                    value.nextStep();
                }
            }
        }
    }

    public void fromRefactorToRed(){
        if(passAllTests()&&codeCompiles()){
            for(String key : code.keySet()){
                    Code value = code.get(key);
                    value.nextStep();
            }
        }
    }

}
