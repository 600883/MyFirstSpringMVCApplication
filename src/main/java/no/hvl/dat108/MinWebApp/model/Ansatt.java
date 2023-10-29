package no.hvl.dat108.MinWebApp.model;

import jakarta.persistence.*;

@Entity
@Table(schema = "ansatt_avdeling")
public class Ansatt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String navn;
    private String kjonn;
    private int manedslonn;
    private Integer avdeling_id;

    public Ansatt() {}

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }
    public void setNavn(String navn) {
        this.navn = navn;
    }
    public int getLonn() {
        return manedslonn;
    }

    public void setLonn(int lonn) {
        this.manedslonn = lonn;
    }

    public void setKjonn(String kjonn) {
        this.kjonn = kjonn;
    }

    public String getKjonn() {
        return kjonn;
    }
    public Integer getAvdeling_id() {
        return avdeling_id;
    }

    public void setAvdeling_id(Integer id) {
        this.avdeling_id = id;
    }

    public String toString() {
        return String.format("Ansatt: id=%d, navn=%s", id, navn);
    }


}
