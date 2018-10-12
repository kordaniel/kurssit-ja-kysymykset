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
    private List<Kysymys> kysymykset;

    public Kurssi(Integer id, String nimi, List<Kysymys> kysymykset) {
        this.id = id;
        this.nimi = nimi;
        this.kysymykset = kysymykset;
    }
    
    public Kurssi(Integer id, String nimi) {
        this(id, nimi, new ArrayList<>());
    }
    
    public Kurssi(String nimi) {
        this(-1, nimi);
    }

    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }
    
    public String toString() {
        return this.id + ": " + this.nimi + "\n " + this.kysymykset.toString();
    }
}
