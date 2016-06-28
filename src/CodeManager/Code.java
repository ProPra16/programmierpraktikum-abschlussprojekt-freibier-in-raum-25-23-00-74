package CodeManager;

import java.awt.*;
import java.util.List;

public class Code {
    List<String> code;
    List<String> original;
    public Code(List<String> code){
        this.code = code;
        original = code;
    }

    public void toTextArea(TextArea text){
        String content = "";
        for(String s : code){
            content+=s+"\n";
        }
        text.setText(content);
    }

    public void nextStep(){
        original = code;
    }

    public void reset(){
        code = original;
    }

    public boolean compileable(){
        return false;
    }

    public boolean run(){
        return false;
    }
}
