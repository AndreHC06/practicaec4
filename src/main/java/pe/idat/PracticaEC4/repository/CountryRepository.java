package pe.idat.PracticaEC4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.idat.PracticaEC4.entity.Country;


public interface CountryRepository extends JpaRepository<Country, Long>{
    Country findByName(String name);
}
