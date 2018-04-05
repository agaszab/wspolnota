package pl.wspolnotamieszkaniowa.mod;


    public enum Plec {
        KOBIETA ("Kobieta"),
        MEZCZYZNA ("Mężczyzna");

        private String name;

        Plec(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }
