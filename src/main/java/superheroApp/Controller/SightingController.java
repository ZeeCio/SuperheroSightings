package superheroApp.Controller;

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

import java.util.List;

@Controller
public class SightingController {


    public final SightingDB sightingDao;

    public final SuperheroDB superheroDao;

    public final LocationDB locationDao;
    public SightingController(SightingDB sightingDao, SuperheroDB superheroDao, LocationDB locationDao) {

        this.sightingDao = sightingDao;
        this.superheroDao = superheroDao;
        this.locationDao = locationDao;
    }

    @GetMapping("/sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        model.addAttribute("sightings", sightings);
        return "sightings";
    }


    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
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
    }

    @GetMapping("addSighting")
    public String displayAddSightings(Model model) {
        List<Superhero> superheros = superheroDao.getAllSuperheros();
        model.addAttribute("superheros", superheros);

        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);

        return "addSighting";
    }

}
