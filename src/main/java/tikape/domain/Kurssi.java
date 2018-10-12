/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author danielko
 */
public class Kurssi {
    private Integer id;
    private String nimi;

     public Kurssi(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }
    
    public Kurssi(String nimi) {
        this(null, nimi);
    }

    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }
    
    public String toString() {
        return this.id + ": " + this.nimi;
    }
}
