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
    private Kysymys kysymys;
    private Boolean oikein;

    public Vastaus(Integer id, Kysymys kysymys, Boolean oikein) {
        this.id = id;
        this.kysymys = kysymys;
        this.oikein = oikein;
    }

    
}
