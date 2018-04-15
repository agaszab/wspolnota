package pl.wspolnotamieszkaniowa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wspolnotamieszkaniowa.mod.Osoba;

import java.util.List;

public interface OsobaRepository extends JpaRepository<Osoba, Long> {
    public List<Osoba> findAllOsobaByMieszkanie_WspolnotaOrderByNazwisko(long id_wspolnoty);
}

