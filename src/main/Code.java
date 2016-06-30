import java.awt.*;
import java.util.List;

public class Code {
    String klasse;
    String aufgabenstellung;

    public Code(String klasse, String ufgabenstellung){
        this.klasse = klasse;
        this.aufgabenstellung = aufgabenstellung;
    }

    public String getKlasse(){
        return this.klasse;
    }

    public String getAufgabenstellung(){
        return this.aufgabenstellung;
    }
}
