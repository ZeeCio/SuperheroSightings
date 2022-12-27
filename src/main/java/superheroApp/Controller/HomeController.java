package superheroApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import superheroApp.DAO.*;
import superheroApp.Entities.Location;
import superheroApp.Entities.Sighting;
import superheroApp.Entities.Superhero;
import superheroApp.Entities.Superpower;
import superheroApp.Service.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private final SuperheroDB superheroDao;
    @Autowired
    private final SuperpowerDB superpowerDao;
    @Autowired
    private final SightingDB sightingDao;
    @Autowired
    private final LocationDB locationDao;
    @Autowired
    private final Service service;


    public HomeController(Service service, SuperheroDB superheroDao, SuperpowerDB superpowerDao, SightingDB sightingDao, LocationDB locationDao) {
        this.service = service;
        this.superheroDao = superheroDao;
        this.superpowerDao = superpowerDao;
        this.sightingDao = sightingDao;
        this.locationDao = locationDao;
    }

    @GetMapping("index")
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

    @GetMapping("addSighting")
    public String displayAddSightings(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));

        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);

        List<Superhero> superheros = superheroDao.getAllSuperheros();
        model.addAttribute("superheros", superheros);

        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);

        return "addSighting";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("sightingId"));
        int superheroId = Integer.parseInt(request.getParameter("superheroId"));
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        String dateString = request.getParameter("Date");

        String date = null;
        if (service.isValidDate(dateString)) {
            date = dateString;
        } else {
            System.out.println("Empty or Invalid date.");
        }

        Superhero superhero = superheroDao.getSuperheroById(superheroId);
        Location location = locationDao.getLocationById(locationId);

        Sighting sighting = service.createSighting(superhero, location, date);
        sighting.setId(id);

        if (date != null) {
            sightingDao.addSighting(sighting);
            return "redirect:/sightings";
        } else {
            sighting = sightingDao.getSightingById(sighting.getId());
            model.addAttribute("sightings", sighting);

            List<Superhero> superheros = superheroDao.getAllSuperheros();
            model.addAttribute("superheros", superheros);

            List<Location> locations = locationDao.getAllLocations();
            model.addAttribute("locations", locations);

            return "redirect:/addSighting";
        }


    }
  /*  @GetMapping("/")
    public String displayIndex(Model model) {
        final int SIGHTINGS_PER_PAGE = 10;
        List<Sighting> sightings = service.getLastSightings(SIGHTINGS_PER_PAGE);
        HashMap<Sighting,Superhero> superheroSightings = service.mapSuperheroSightings(sightings);
        model.addAttribute("sightings", sightings);
        model.addAttribute("superheroSightings", superheroSightings);

        return "index";
    }*/

}
