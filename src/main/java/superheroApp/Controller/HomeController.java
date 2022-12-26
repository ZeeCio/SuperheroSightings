package superheroApp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import superheroApp.DAO.LocationDB;
import superheroApp.DAO.SightingDB;
import superheroApp.DAO.SuperheroDB;
import superheroApp.DAO.SuperpowerDB;
import superheroApp.Entities.Location;
import superheroApp.Entities.Sighting;
import superheroApp.Entities.Superhero;
import superheroApp.Entities.Superpower;
import superheroApp.service.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
public class HomeController {

   SuperheroDB superheroDao;

    SuperpowerDB superpowerDao;

    SightingDB sightingDao;

    LocationDB locationDao;
    Service service;

    private ArrayList<String> displayForHome;
    private HashMap<String,Integer> displays;

    public HomeController(Service service, SuperheroDB superheroDao, SuperpowerDB superpowerDao, SightingDB sightingDao, LocationDB locationDao) {
        this.service = service;
        this.superheroDao = superheroDao;
        this.superpowerDao = superpowerDao;
        this.sightingDao = sightingDao;
        this.locationDao = locationDao;
    }

    @GetMapping("/index")
    public String displayAll(Model model) {
        List<Superhero> superheros = superheroDao.getAllSuperheros();
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("superheros", superheros);
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        return "index";
    }


    @GetMapping("/")
    public String displayIndex(Model model) {
        final int SIGHTINGS_PER_PAGE = 10;
        List<Sighting> sightings = service.getLastSightings(SIGHTINGS_PER_PAGE);
        HashMap<Sighting,Superhero> superheroSightings = service.mapSuperheroSightings(sightings);
        model.addAttribute("sightings", sightings);
        model.addAttribute("SuperheroSightings", superheroSightings);

        return "index";
    }


}
