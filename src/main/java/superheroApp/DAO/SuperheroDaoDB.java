package superheroApp.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import superheroApp.Entities.Location;
import superheroApp.Entities.Sighting;
import superheroApp.Entities.Superhero;
import superheroApp.Entities.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SuperheroDaoDB implements SuperheroDB {
    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    SightingDaoDB sightingDaoDB;

    @Override
    public Superhero getSuperheroById(int id) {
        try {
            final String SELECT_SUPERHERO_BY_ID = "SELECT * FROM Superhero WHERE SuperheroId = ?;";
            Superhero superhero = jdbc.queryForObject(SELECT_SUPERHERO_BY_ID, new SuperheroMapper(), id);
            return superhero;
        } catch (DataAccessException ex) {
            return null;
        }
    }


    private List<Superpower> getSuperpowersForSuperhero(int id) {
        final String SELECT_SUPERPOWERS_FOR_SUPERHERO = "SELECT s.* FROM Superpower s "
                + "JOIN SuperheroSuperpower ss ON ss.SuperpowerId = s.SuperpowerId WHERE ss.SuperheroId = ?;";
        return jdbc.query(SELECT_SUPERPOWERS_FOR_SUPERHERO, new SuperpowerDaoDB.SuperpowerMapper(), id);
    }


    private List<Sighting> getSightingsForSuperhero(int id) {
        final String SELECT_SIGHTINGS_FOR_SUPERHERO = "SELECT * FROM Sighting WHERE SuperheroId = ?;";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_SUPERHERO, new SightingDaoDB.SightingMapper(), id);
        sightingDaoDB.associateLocationsForSightings(sightings);
        return sightings;
    }

    @Override
    public List<Superhero> getAllSuperheros() {
        final String SELECT_ALL_SUPERHEROS = "SELECT * FROM Superhero;";
        List<Superhero> superheros = jdbc.query(SELECT_ALL_SUPERHEROS, new SuperheroMapper());
        associateSuperpowersAndSightings(superheros);
        return superheros;
    }

    public void associateSuperpowersAndSightings(List<Superhero> superheroes) {
        for (Superhero superhero : superheroes) {
            superhero.setSuperpowers(getSuperpowersForSuperhero(superhero.getId()));
            superhero.setSightings(getSightingsForSuperhero(superhero.getId()));
        }
    }

    @Override
    @Transactional
    public Superhero addSuperhero(Superhero superhero) {
        final String INSERT_SUPERHERO = "INSERT INTO Superhero(SuperheroName, SuperheroDescription) "
                + "VALUES(?,?);";
        jdbc.update(INSERT_SUPERHERO,
                superhero.getName(),
                superhero.getDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superhero.setId(newId);
       insertSuperheroSuperpower(superhero);
        insertSighting(superhero);
        return superhero;
    }

  private void insertSuperheroSuperpower(Superhero superhero){
        final String INSERT_SUPERHERO_SUPERPOWER = "INSERT INTO "
                + "SuperheroSuperpower(SuperheroId, SuperpowerId) VALUES(?,?);";
        for(Superpower superpower : superhero.getSuperpowers()) {
            jdbc.update(INSERT_SUPERHERO_SUPERPOWER,
                    superhero.getId(),
                    superpower.getId());
        }
    }

    private void insertSighting(Superhero superhero){
        final String INSERT_SIGHTING = "INSERT INTO "
                + "Sighting(SuperheroId, LocationId, Date) VALUES(?,?,?);";
        for(Sighting sighting : superhero.getSightings()){
            jdbc.update(INSERT_SIGHTING,
                    superhero.getId(),
                    sighting.getLocationId(),
                    sighting.getDate());
            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            sighting.setId(newId);
        }
    }

    public void updateSuperhero(Superhero superhero) {
        final String UPDATE_SUPERHERO = "UPDATE Superhero SET SuperheroName = ?, SuperheroDescription = ?"
                + "WHERE SuperheroId = ?;";
        jdbc.update(UPDATE_SUPERHERO,
                superhero.getName(),
                superhero.getDescription(),
                superhero.getId());
/*
        final String DELETE_SUPERHERO_SUPERPOWER = "DELETE FROM SuperheroSuperpower WHERE SuperheroId = ?;";
        jdbc.update(DELETE_SUPERHERO_SUPERPOWER, superhero.getId());
        //insertSuperheroSuperpower(superhero);

        final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE SuperheroId = ?;";
        jdbc.update(DELETE_SIGHTING, superhero.getId());
        insertSighting(superhero);*/
    }

    @Override
    @Transactional
    public void deleteSuperheroById(int id) {
        final String DELETE_SUPERHERO_SUPERPOWER = "DELETE FROM SuperheroSuperpower WHERE SuperheroId = ?;";
        jdbc.update(DELETE_SUPERHERO_SUPERPOWER, id);

        final String DELETE_SUPERHERO_SIGHTING = "DELETE FROM Sighting WHERE SuperheroId = ?;";
        jdbc.update(DELETE_SUPERHERO_SIGHTING, id);

        final String DELETE_SUPERHERO = "DELETE FROM Superhero WHERE SuperheroId = ?;";
        jdbc.update(DELETE_SUPERHERO, id);
    }

    @Override
    public List<Superhero> getSuperherosForSuperpower(Superpower superpower) {
        final String SELECT_SUPERHEROS_FOR_SUPERPOWER = "SELECT s.* FROM Superhero s JOIN "
                + "SuperheroSuperpower ss ON ss.SuperheroId = s.SuperheroId WHERE ss.SuperpowerId = ?;";
        List<Superhero> superheroes = jdbc.query(SELECT_SUPERHEROS_FOR_SUPERPOWER,
                new SuperheroMapper(), superpower.getId());
        associateSuperpowersAndSightings(superheroes);
        return superheroes;
    }

    @Override
    public Superhero getSuperheroForSighting(Sighting sighting) {
        final String SELECT_SUPERHEROS_FOR_SIGHTING = "SELECT s.* FROM Superhero s JOIN "
                + "Sighting si ON si.SuperheroId = s.SuperheroId WHERE si.SightingId = ?;";
        Superhero superhero = jdbc.queryForObject(SELECT_SUPERHEROS_FOR_SIGHTING,
                new SuperheroMapper(), sighting.getId());
        superhero.setSuperpowers(getSuperpowersForSuperhero(superhero.getId()));
        superhero.setSightings(getSightingsForSuperhero(superhero.getId()));
        return superhero;
    }

    @Override
    public List<Superhero> getSuperheroForLocation(Location location) {
        final String SELECT_SUPERHEROS_FOR_LOCATION = "SELECT si.* FROM Superhero s "
                + "JOIN Sighting si ON si.SuperheroId = s.SuperheroId "
                + "WHERE si.LocationId = ?;";
        List<Superhero> superheroes = jdbc.query(SELECT_SUPERHEROS_FOR_LOCATION,
                new SuperheroMapper(), location.getId());
        associateSuperpowersAndSightings(superheroes);
        return superheroes;
    }

    public static class SuperheroMapper implements RowMapper<Superhero> {

        @Override
        public Superhero mapRow(ResultSet rs, int index) throws SQLException {
            Superhero superhero = new Superhero();
            superhero.setId(rs.getInt("SuperheroId"));
            superhero.setName(rs.getString("SuperheroName"));
            superhero.setDescription(rs.getString("SuperheroDescription"));
            return superhero;
        }
    }
}
