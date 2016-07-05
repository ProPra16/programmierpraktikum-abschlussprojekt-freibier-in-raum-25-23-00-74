import java.util.HashMap;

public class CodeManager {
    private HashMap<String, Code> code;
    private HashMap<String, Code> codeOriginal;
    private HashMap<String, Code> test;
    private HashMap<String, Code> testOriginal;

    public CodeManager(){
        code = new HashMap<>();
        codeOriginal = new HashMap<>();
        test = new HashMap<>();
        testOriginal = new HashMap<>();
    }

    public void addCode(Code c, String name){
        code.put(name,c);
    }

    public void addTest(Code c, String name){
        test.put(name,c);
    }

    public Code getCode(String name){
        return code.get(name);
    }

    public Code getTest(String name){
        return test.get(name);
    }

    public void updateCode(String name){
        codeOriginal.put(name,code.get(name));
    }

    public void updateTest(String name){
        testOriginal.put(name,test.get(name));
    }

    public void resetCode(String name){
        code.put(name,codeOriginal.get(name));
    }

    public void resetTest(String name){
        test.put(name,testOriginal.get(name));
    }
}
