package pl.wspolnotamieszkaniowa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wspolnotamieszkaniowa.mod.Mieszkanie;

public interface MieszkanieRepository extends JpaRepository<Mieszkanie, Long> {
}
