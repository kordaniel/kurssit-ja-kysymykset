/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.domain;

import tikape.domain.Kurssi;
import tikape.domain.Aihe;
import java.util.List;

/**
 *
 * @author danielko
 */
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
