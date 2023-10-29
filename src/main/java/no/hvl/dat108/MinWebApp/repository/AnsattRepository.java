package no.hvl.dat108.MinWebApp.repository;

import no.hvl.dat108.MinWebApp.model.Ansatt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnsattRepository extends JpaRepository<Ansatt, Integer> {
    Ansatt findByNavn(String navn);

}
