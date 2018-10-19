package tikape.domain;

public class Kurssi {
    private Integer id;
    private Integer aihe_id;
    private String nimi;
    
     public Kurssi(Integer id, Integer aihe_id, String nimi) {
        this.id = id;
        this.aihe_id = aihe_id;
        this.nimi = nimi;
    }
    
    public Kurssi(Integer aihe_id, String nimi) {
        this(null, aihe_id, nimi);
    }

    public Integer getId() {
        return id;
    }
    
    public Integer getAihe_id() {
        return aihe_id;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public String toString() {
        return this.id + ": " + this.nimi;
    }
}
