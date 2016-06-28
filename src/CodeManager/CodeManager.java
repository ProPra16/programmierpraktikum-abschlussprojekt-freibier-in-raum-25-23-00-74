package CodeManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

}
