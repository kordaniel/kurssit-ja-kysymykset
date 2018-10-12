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
    private Kurssi kurssi;
    private Aihe aihe;
    private List<Vastaus> vastaukset;

    public Kysymys(Integer id, String teksti, Kurssi kurssi, Aihe aihe, List<Vastaus> vastaukset) {
        this.id = id;
        this.teksti = teksti;
        this.kurssi = kurssi;
        this.aihe = aihe;
        this.vastaukset = vastaukset;
    }
    
    
}
