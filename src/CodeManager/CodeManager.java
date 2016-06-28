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

    public boolean passAllTests(){
        boolean pass = false;
        for(String key : code.keySet()){
            if(key.equalsIgnoreCase("test")) {
                Code value = code.get(key);
                pass = value.run();
            }
        }
        return pass;
    }

}
