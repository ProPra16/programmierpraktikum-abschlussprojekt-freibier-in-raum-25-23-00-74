import java.util.HashMap;

public class CodeManager {
    private Code code;
    private Code codeOriginal;
    private Code test;
    private Code testOriginal;
    private Code aktest;

    public CodeManager(){
    }

    public void setCode(Code c){
        code= c;
        codeOriginal = c;
    }

    public void setTest(Code test){
        this.test=test;
        testOriginal = test;

    }

    public void updateCode(String content){
        code.setKlasse(content);
    }

    public void updateTest(String content){
        test.setKlasse(content);
    }

    public void updateakTest(String content){
        aktest.setKlasse(content);
    }

    public void setakTest(Code test) {
        aktest=test;
    }

    public Code getakTest() { return aktest;}

    public Code getCode(){
        return code;
    }

    public Code getTest(){
        return test;
    }

    public void CodeToNextStep(){
        codeOriginal=code;
    }

    public void TestToNextStep(){
        testOriginal=test;
    }

    public void resetCode(){
        code=codeOriginal;
    }

    public void resetTest(){
        test=testOriginal;
    }
}
