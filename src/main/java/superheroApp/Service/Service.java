package superheroApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import superheroApp.DAO.SightingDB;
import superheroApp.DAO.SuperheroDB;
import superheroApp.Entities.Location;
import superheroApp.Entities.Sighting;
import superheroApp.Entities.Superhero;
import superheroApp.Entities.Superpower;

import java.sql.Date;
import java.util.*;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    SuperheroDB superheroDao;

    @Autowired
    SightingDB sightingDao;

    public Location createLocation(String name, double latitude, double longitude, String description, String address){
        Location location = new Location();
        location.setName(name);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setDescription(description);
        location.setAddressInfo(address);

        return location;
    }

    public Sighting createSighting(Superhero superhero, Location location, Date date){
        Sighting sighting = new Sighting();
        sighting.setSuperheroId(superhero.getId());
        //sighting.setLocation(location);
        sighting.setDate(date);

        return sighting;
    }


    public Superhero createSuperhero(String name, String description, List<Superpower> superpowers){
        Superhero superhero = new Superhero();
        superhero.setName(name);
        superhero.setDescription(description);
        superhero.setSuperpowers(superpowers);
        superhero.setSightings(new ArrayList<Sighting>());
        return superhero;
    }

    public Superpower createSuperpower(String name, String description){
        Superpower superpower = new Superpower();
        superpower.setName(name);
        superpower.setDescription(description);

        return superpower;
    }


    //functions to get last sightings and the superheroes
    public List<Sighting> getLastSightings(int number){
        List<Sighting> sightings = sightingDao.getAllSightings();
        Collections.sort(sightings, new SortByDateDesc());
        if(number >= sightings.size()){
            return sightings;
        } else {
            List<Sighting> result = new ArrayList<>();
            for(int i=0;i<sightings.size();i++){
                result.add(sightings.get(i));
            }
            return result;
        }
    }
    public HashMap<Sighting, Superhero> mapSuperheroSightings(List<Sighting> sightings){
        HashMap<Sighting, Superhero> heroSightings = new HashMap<>();
        for(int i=0;i<sightings.size();i++){
            heroSightings.put(sightings.get(i), superheroDao.getSuperheroForSighting(sightings.get(i)));
        }
        return heroSightings;
    }

    public static final class SortByDateDesc implements Comparator<Sighting> {

        @Override
        public int compare(Sighting s1, Sighting s2) {
            return s2.getDate().compareTo(s1.getDate());
        }

    }

    public boolean isValidLatitude(String latitude){
        try{
            double value = Double.parseDouble(latitude);
            if(value<-90 || value>90){
                return false;
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean isValidLongitude(String longitude){
        try{
            double value = Double.parseDouble(longitude);
            if(value<-180 || value>180){
                return false;
            }
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean isValidDate(String date){
        try{
            Date.valueOf(date);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


}
