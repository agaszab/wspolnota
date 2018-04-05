package pl.wspolnotamieszkaniowa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wspolnotamieszkaniowa.mod.Osoba;

public interface OsobaRepository extends JpaRepository<Osoba, Long> {
}

