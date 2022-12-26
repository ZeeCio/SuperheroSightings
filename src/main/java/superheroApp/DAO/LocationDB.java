package superheroApp.DAO;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import superheroApp.Entities.Location;
import superheroApp.Entities.Superhero;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface LocationDB {
    Location getLocationById(int id);

    List<Location> getAllLocations();

    @Transactional
    Location addLocation(Location location);

    void updateLocation(Location location);

    @Transactional
    void deleteLocationById(int id);

    List<Location> getLocationsForSuperhero(Superhero superhero);


}
