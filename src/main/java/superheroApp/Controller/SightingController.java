package superheroApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import superheroApp.DAO.LocationDB;
import superheroApp.DAO.SightingDB;
import superheroApp.DAO.SuperheroDB;
import superheroApp.Entities.Location;
import superheroApp.Entities.Sighting;
import superheroApp.Entities.Superhero;
import superheroApp.Service.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

@Controller
public class SightingController {

    @Autowired
    private final SightingDB sightingDao;
    @Autowired
    private final SuperheroDB superheroDao;
    @Autowired
    private final LocationDB locationDao;
    @Autowired
    private final Service service;

    public SightingController(SightingDB sightingDao, SuperheroDB superheroDao, LocationDB locationDao, Service service) {
        this.sightingDao = sightingDao;
        this.superheroDao = superheroDao;
        this.locationDao = locationDao;
        this.service = service;
    }

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        HashMap<Sighting, Superhero> superheroSightings = service.mapSuperheroSightings(sightings);
        model.addAttribute("sightings", sightings);
        model.addAttribute("superheroSightings", superheroSightings);
        return "sightings";
    }


    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }

/*    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        //int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performSighting(Sighting sighting) {
        sightingDao.updateSighting(sighting);
        return "redirect:/sightings";
    }*/

    @GetMapping("editSighting")
    public String displayEditSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));

        Sighting sightings = sightingDao.getSightingById(id);
        model.addAttribute("sightings", sightings);

        List<Superhero> superheros = superheroDao.getAllSuperheros();
        model.addAttribute("superheros", superheros);

        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {


        int id = Integer.parseInt(request.getParameter("sightingId"));
        int superheroId = Integer.parseInt(request.getParameter("superheroId"));
        int locationId = Integer.parseInt(request.getParameter("locationId"));
        String dateString = request.getParameter("sightingDate");

        String date = null;
        if (service.isValidDate(dateString)) {
            date = dateString;
        } else {
            System.out.println("Empty or Invalid date.");
        }

        Superhero superhero = superheroDao.getSuperheroById(superheroId);
        Location location = locationDao.getLocationById(locationId);

        Sighting sightings = service.createSighting(superhero, location, date);
        sightings.setId(id);

        if (date != null) {
            sightingDao.updateSighting(sightings);
            return "redirect:sightings";
        } else {
            sightings = sightingDao.getSightingById(sightings.getId());
            model.addAttribute("sightings", sightings);

            List<Superhero> superheros = superheroDao.getAllSuperheros();
            model.addAttribute("superheros", superheros);

            List<Location> locations = locationDao.getAllLocations();
            model.addAttribute("locations", locations);

            return "editSighting";
        }
    }



    }
