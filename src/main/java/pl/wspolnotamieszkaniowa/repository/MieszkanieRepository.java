package pl.wspolnotamieszkaniowa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wspolnotamieszkaniowa.mod.Mieszkanie;

import java.util.List;

public interface MieszkanieRepository extends JpaRepository<Mieszkanie, Long> {

public List<Mieszkanie> findAllByPrzypisaneFalse();

}
