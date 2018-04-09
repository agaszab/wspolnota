package pl.wspolnotamieszkaniowa.mod;

import javax.persistence.*;

@Entity
public class Osoba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_osoby;

    private String imie;
    private String nazwisko;

    @ManyToOne
    private Mieszkanie mieszkanie;

    @Enumerated(EnumType.STRING)
    private Plec plec;

    public Osoba(String imie, String nazwisko, Mieszkanie mieszkanie, Plec plec) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.mieszkanie = mieszkanie;
        this.plec = plec;
    }

    public Osoba() {
    }

    public Long getId_osoby() {
        return id_osoby;
    }

    public void setId_osoby(Long id_osoby) {
        this.id_osoby = id_osoby;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Mieszkanie getMieszkanie() {
        return mieszkanie;
    }

    public void setMieszkanie(Mieszkanie mieszkanie) {
        this.mieszkanie = mieszkanie;
    }

    public Plec getPlec() {
        return plec;
    }

    public void setPlec(Plec plec) {
        this.plec = plec;
    }

    @Override
    public String toString() {
        return  mieszkanie.getUlica() +" "+mieszkanie.getNumer_mieszkania();
    }
}
