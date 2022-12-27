package superheroApp.DAO;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import superheroApp.Entities.Location;
import superheroApp.Entities.Sighting;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface SightingDB {
    Sighting getSightingById(int id);



    List<Sighting> getAllSightings();

    void associateLocationsForSightings(List<Sighting> sightings);

    @Transactional
    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting);

    @Transactional
    void deleteSightingById(int id);

    List<Sighting> getSightingsForLocation(Location location);


}
