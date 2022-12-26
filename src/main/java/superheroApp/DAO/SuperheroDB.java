package superheroApp.DAO;

import org.springframework.transaction.annotation.Transactional;
import superheroApp.Entities.Location;
import superheroApp.Entities.Sighting;
import superheroApp.Entities.Superhero;
import superheroApp.Entities.Superpower;

import java.util.List;

public interface SuperheroDB {
    Superhero getSuperheroById(int id);


    List<Superhero> getAllSuperheros();

    void associateSuperpowersAndSightings(List<Superhero> superheroes);

    @Transactional
    Superhero addSuperhero(Superhero superhero);

    void updateSuperhero(Superhero superhero);

    @Transactional
    void deleteSuperheroById(int id);

    List<Superhero> getSuperherosForSuperpower(Superpower superpower);

    Superhero getSuperheroForSighting(Sighting sighting);

    List<Superhero> getSuperheroForLocation(Location location);
}
