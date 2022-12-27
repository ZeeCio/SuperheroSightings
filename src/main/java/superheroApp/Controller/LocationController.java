package superheroApp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import superheroApp.DAO.LocationDB;
import superheroApp.DAO.SuperheroDB;
import superheroApp.Entities.Location;
import superheroApp.Entities.Superhero;
import superheroApp.Service.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LocationController {

   private final  LocationDB locationDao;
   private final SuperheroDB superheroDao;

   private final Service service;

    public LocationController(LocationDB locationDao, SuperheroDB superheroDao, Service service) {

        this.locationDao = locationDao;
        this.superheroDao = superheroDao;
        this.service = service;
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

   /* @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }
*/
    @PostMapping("/locations/editLocation")
    public String editLocation(HttpServletRequest request, Model model){

        int id = Integer.parseInt(request.getParameter("locationId"));
        String name = request.getParameter("locationName");
        String stringLatitude = request.getParameter("latitude");
        String stringLongitude = request.getParameter("longitude");
        String description = request.getParameter("description");
        String address = request.getParameter("addressInfo");

        double latitude = 0;
        if(service.isValidLatitude(stringLatitude)){
            latitude = Double.parseDouble(stringLatitude);
        } else {
            System.out.println("Invalid or Empty Latitude");
        }

        double longitude = 0;
        if(service.isValidLongitude(stringLongitude)){
            longitude = Double.parseDouble(stringLongitude);
        } else {
            System.out.println("Invalid or Empty Longitude");
        }

        Location location = service.createLocation(name, latitude, longitude, description, address);
        location.setId(id);

        if(service.isValidLatitude(stringLatitude) && service.isValidLongitude(stringLongitude)){
            locationDao.updateLocation(location);
            return "redirect:/locations";
        } else {
            model.addAttribute("location", locationDao.getLocationById(location.getId()));
            return "editLocation";
        }
    }


    @GetMapping("infoLocation")
    public String displayDetailsLocation(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));

        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);

        List<Superhero> superheros = superheroDao.getSuperheroForLocation(location);
        model.addAttribute("superheros", superheros);

        return "infoLocation";
    }

}
