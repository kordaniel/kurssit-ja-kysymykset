package tikape.domain;

public class Aihe {
    private Integer id;
    private String teksti;

    public Aihe(Integer id, String teksti) {
        this.id = id;
        this.teksti = teksti;
    }

    public Integer getId() {
        return id;
    }

    public String getTeksti() {
        return teksti;
    }
    
    public String toString() {
        return this.id + ": " + this.teksti;
    }
}
