/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.domain;

import tikape.domain.Kysymys;

/**
 *
 * @author danielko
 */
public class Vastaus {
    private Integer id;
    private Integer kysymysId;
    private String teksti;
    private Boolean oikein;

    public Vastaus(Integer id, Integer kysymysId, String teksti, Boolean oikein) {
        this.id = id;
        this.kysymysId = kysymysId;
        this.teksti = teksti;
        this.oikein = oikein;
    }

    public Integer getId() {
        return id;
    }

    public Integer getKysymysId() {
        return kysymysId;
    }
    
    public String getTeksti() {
        return teksti;
    }

    public Boolean getOikein() {
        return oikein;
    }
    
    public String getOnoikeavastaus() {
        if (getOikein()) return "oikein";
        return "väärin";
    }

    @Override
    public String toString() {
        return this.id + ": (" + this.kysymysId + "): " +  this.teksti + ". onOikea=" + oikein;
    }

    
}
