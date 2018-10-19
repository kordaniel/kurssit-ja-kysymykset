package tikape.domain;

public class Kysymys {
    private Integer id;
    private String teksti;
    private Integer kurssi_id;
    private Integer aihe_id;

    public Kysymys(Integer id, String teksti, Integer kurssi_id, Integer aihe_id) {
        this.id = id;
        this.teksti = teksti;
        this.kurssi_id = kurssi_id;
        this.aihe_id = aihe_id;
    }

    public Integer getId() {
        return id;
    }

    public String getTeksti() {
        return teksti;
    }

    public Integer getKurssi_id() {
        return kurssi_id;
    }

    public Integer getAihe_id() {
        return aihe_id;
    }

    @Override
    public String toString() {
        return id + ": k:" + kurssi_id + ", a:" + aihe_id + ": " + teksti;
    }
    
}
