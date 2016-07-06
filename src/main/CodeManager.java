import java.util.HashMap;

public class CodeManager {
    private HashMap<String, Code> code;
    private HashMap<String, Code> codeOriginal;
    private HashMap<String, String> test;
    private HashMap<String, String> testOriginal;

    public CodeManager(){
        code = new HashMap<>();
        codeOriginal = new HashMap<>();
        test = new HashMap<>();
        testOriginal = new HashMap<>();
    }

    public void addCode(Code c, String name){
        code.put(name,c);
    }

    public void addTest(String test, String name){
        this.test.put(name,test);
    }

    public Code getCode(String name){
        return code.get(name);
    }

    public String getTest(String name){
        return test.get(name);
    }

    public void CodeToNextStep(String name){
        codeOriginal.put(name,code.get(name));
    }

    public void TestToNextStep(String name){
        testOriginal.put(name,test.get(name));
    }

    public void resetCode(String name){
        code.put(name,codeOriginal.get(name));
    }

    public void resetTest(String name){
        test.put(name,testOriginal.get(name));
    }
}
