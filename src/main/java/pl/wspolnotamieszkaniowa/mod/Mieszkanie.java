package pl.wspolnotamieszkaniowa.mod;

import javax.persistence.*;
import java.util.List;

@Entity
public class Mieszkanie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_mieszkania;

    private String numer_mieszkania;
    private String ulica;
    private int powierzchnia_mieszkania;

    @ManyToOne
    private Wspolnota wspolnota;

    @OneToMany(mappedBy = "mieszkanie")
    private List<Osoba> mieszkancy;

    public Mieszkanie() {
    }

    public Mieszkanie(String numer_mieszkania, String ulica, int powierzchnia_mieszkania, Wspolnota wspolnota, List<Osoba> mieszkancy) {
        this.numer_mieszkania = numer_mieszkania;
        this.ulica = ulica;
        this.powierzchnia_mieszkania = powierzchnia_mieszkania;
        this.wspolnota = wspolnota;
        this.mieszkancy = mieszkancy;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public Long getId_mieszkania() {
        return id_mieszkania;
    }

    public void setId_mieszkania(Long id_mieszkania) {
        this.id_mieszkania = id_mieszkania;
    }

    public String getNumer_mieszkania() {
        return numer_mieszkania;
    }

    public void setNumer_mieszkania(String numer_mieszkania) {
        this.numer_mieszkania = numer_mieszkania;
    }

    public int getPowierzchnia_mieszkania() {
        return powierzchnia_mieszkania;
    }

    public void setPowierzchnia_mieszkania(int powierzchnia_mieszkania) {
        this.powierzchnia_mieszkania = powierzchnia_mieszkania;
    }

    public Wspolnota getWspolnota() {
        return wspolnota;
    }

    public void setWspolnota(Wspolnota wspolnota) {
        this.wspolnota = wspolnota;
    }

    public List<Osoba> getMieszkancy() {
        return mieszkancy;
    }

    public void setMieszkancy(List<Osoba> mieszkancy) {
        this.mieszkancy = mieszkancy;
    }

    @Override
    public String toString() {
        return "Mieszkanie{" +
                "id_mieszkania=" + id_mieszkania +
                ", numer_mieszkania=" + numer_mieszkania +
                ", ulica='" + ulica + '\'' +
                ", powierzchnia_mieszkania=" + powierzchnia_mieszkania +
                ", wspolnota=" + wspolnota +
                ", mieszkancy=" + mieszkancy +
                '}';
    }
}
