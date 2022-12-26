package superheroApp.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import superheroApp.Entities.Location;
import superheroApp.Entities.Sighting;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SightingDaoDB implements SightingDB {
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM Sighting WHERE SightingId = ?;";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            sighting.setLocationId(getLocationForSighting(id).getId());
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM Sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        //associateLocationsForSightings(sightings);
        return sightings;
    }

    @Override
    public void associateLocationsForSightings(List<Sighting> sightings){
        for (Sighting sighting : sightings) {
            sighting.setLocationId(getLocationForSighting(sighting.getId()).getId());
        }
    }

    private Location getLocationForSighting(int id){
        final String SELECT_LOCATION_FOR_SIGHTING = "SELECT l.* FROM Location l "
                + "JOIN Sighting s ON s.LocationId = l.LocationId WHERE s.SightingId = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationDaoDB.LocationMapper(), id);
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO Sighting(SuperheroId, LocationId, Date) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getSuperheroId(),
                sighting.getLocationId(),
                sighting.getDate());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE Sighting SET SuperheroId = ?, LocationId = ?, Date = ?"
                + "WHERE SightingId = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getSuperheroId(),
                sighting.getLocationId(),
                sighting.getDate(),
                sighting.getId());
    }

    @Override
    @Transactional
    public void deleteSightingById(int id) {
        final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE SightingId = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<Sighting> getSightingsForLocation(Location location) {
        final String SELECT_SIGHTINGS_FOR_LOCATION = "SELECT * FROM Sighting WHERE LocationId = ?;";
        List<Sighting> sighting = jdbc.query(SELECT_SIGHTINGS_FOR_LOCATION,
                new SightingMapper(), location.getId());
        associateLocationsForSightings(sighting);
        return sighting;
    }



    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("SightingId"));
            sighting.setSuperheroId(rs.getInt("SuperheroId"));
            sighting.setLocationId(rs.getInt("LocationId"));
            sighting.setDate(Date.valueOf(rs.getDate("Date").toString()));
            return sighting;
        }
    }

}
