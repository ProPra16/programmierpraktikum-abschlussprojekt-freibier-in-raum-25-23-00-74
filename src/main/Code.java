public class Code {
    String klasse;
    String aufgabenstellung;
    String dateiname;


    public void setKlasse(String klasse){
        this.klasse = klasse;
    }

    public void setDateiname(String dateiname){
        this.dateiname = dateiname;
    }

    public void setAufgabenstellung(String aufgabenstellung){
        this.aufgabenstellung = aufgabenstellung;
    }

    public String getKlasse(){
        return this.klasse;
    }

    public String getAufgabenstellung(){
        return this.aufgabenstellung;
    }

    public String getDateiname(){
        return this.dateiname;
    }
}
