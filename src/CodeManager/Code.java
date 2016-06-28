package CodeManager;

import java.awt.*;
import java.util.List;

public class Code {
    List<String> code;
    public Code(List<String> code){
        this.code = code;
    }

    public void toTextArea(TextArea text){
        String content = "";
        for(String s : code){
            content+=s+"\n";
        }
        text.setText(content);
    }
}
