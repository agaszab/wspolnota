package pl.wspolnotamieszkaniowa.mod;

import javax.persistence.*;
import java.util.List;


    @Entity
    public class Wspolnota {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id_wspolnoty;

        private String nazwa_wspolnoty;
        private String adres_wspolnoty;
        private String budynek;

        @OneToMany(mappedBy = "wspolnota")
        private List<Mieszkanie> mieszkania;

        public Wspolnota() {
        }


        public Long getId_wspolnoty() {
            return id_wspolnoty;
        }

        public void setId_wspolnoty(Long id_wspolnoty) {
            this.id_wspolnoty = id_wspolnoty;
        }

        public String getNazwa_wspolnoty() {
            return nazwa_wspolnoty;
        }

        public void setNazwa_wspolnoty(String nazwa_wspolnoty) {
            this.nazwa_wspolnoty = nazwa_wspolnoty;
        }

        public String getAdres_wspolnoty() {
            return adres_wspolnoty;
        }

        public void setAdres_wspolnoty(String adres_wspolnoty) {
            this.adres_wspolnoty = adres_wspolnoty;
        }

        public String getBudynek() {
            return budynek;
        }

        public void setBudynek(String budynek) {
            this.budynek = budynek;
        }

        public List<Mieszkanie> getMieszkania() {
            return mieszkania;
        }

        public void setMieszkania(List<Mieszkanie> mieszkania) {
            this.mieszkania = mieszkania;
        }
    }


