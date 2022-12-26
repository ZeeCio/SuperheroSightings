package superheroApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import superheroApp.DAO.LocationDB;
import superheroApp.DAO.SuperheroDB;
import superheroApp.DAO.SuperpowerDB;
import superheroApp.Entities.Location;
import superheroApp.Entities.Superhero;

import java.util.List;

@Controller
public class LocationController {

   public final  LocationDB locationDao;

    public LocationController(LocationDB locationDao) {

        this.locationDao = locationDao;
    }

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(String name, String description, String latitude, String longitude, String address) {
        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setLatitude(Double.parseDouble(latitude));
        location.setLongitude(Double.parseDouble(longitude));
        location.setAddressInfo(address);
        locationDao.addLocation(location);

        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        locationDao.deleteLocationById(id);
        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) {
        //int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performLocation(Location location) {
        locationDao.updateLocation(location);
        return "redirect:/locations";
    }

}
