package no.hvl.dat108.MinWebApp.repository;

import no.hvl.dat108.MinWebApp.model.Avdeling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvdelingRepository extends JpaRepository<Avdeling, Integer> {
        Avdeling findByNavn(String navn);
}