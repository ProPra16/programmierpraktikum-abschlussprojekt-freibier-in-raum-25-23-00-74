package CodeManager;
import java.util.List;

public class CodeManager {
    private Code code;
    private Code original;

    public CodeManager(List<String> code){
        this.code = new Code(code);
        original = new Code(code);
    }

    public void nextStep(){
        original = code;
    }

    public void reset(){
        code = original;
    }

    public void compile(){

    }
}
