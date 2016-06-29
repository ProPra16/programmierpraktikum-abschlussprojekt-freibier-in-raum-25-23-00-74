package CodeManager;

import java.awt.*;
import java.util.List;

public class Code {
    String code;
    String original;
    public Code(String code){
        this.code = code;
        original = code;
    }

    public void toTextArea(TextArea text){
        text.setText(code);
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
