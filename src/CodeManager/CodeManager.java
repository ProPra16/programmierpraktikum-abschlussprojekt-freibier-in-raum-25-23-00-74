package CodeManager;

import java.awt.*;
import java.util.List;

public class CodeManager {
    private List<String> code;
    private List<String> original;

    public CodeManager(List<String> code){
        this.code = code;
        original = code;
    }

    public void nextStep(){
        original = code;
    }

    public void reset(){
        code = original;
    }

    public void toTextArea(TextArea text){
        String content = "";
        for(String s : code){
            content+=s+"\n";
        }
        text.setText(content);
    }
}
