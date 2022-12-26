package superheroApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import superheroApp.DAO.LocationDB;
import superheroApp.DAO.SightingDB;
import superheroApp.DAO.SuperheroDB;
import superheroApp.DAO.SuperpowerDB;
import superheroApp.Entities.Superhero;
import superheroApp.Entities.Superpower;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SuperpowerController {


    SuperpowerDB superpowerDao;


    public SuperpowerController(SuperpowerDB superpowerDao) {
        this.superpowerDao = superpowerDao;
    }

    @GetMapping("/superpowers")
    public String displaySuperpowers(Model model) {
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        return "superpowers";
    }

    @PostMapping("addSuperpower")
    public String addSuperpowers(String name, String description) {
        Superpower superpower = new Superpower();
        superpower.setName(name);
        superpower.setDescription(description);
        superpowerDao.addSuperpower(superpower);

        return "redirect:/superpowers";
    }

    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(Integer id) {
        superpowerDao.deleteSuperpowerById(id);
        return "redirect:/superpowers";
    }

    @GetMapping("editSuperpower")
    public String editSuperpower(Integer id, Model model) {
        //int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        return "editSuperpower";
    }

    @PostMapping("editSuperpower")
    public String performSuperpower(Superpower superpower) {
        superpowerDao.updateSuperpower(superpower);
        return "redirect:/superpowers";
    }


  /*  @GetMapping("/superpowers")
    public String displaySuperpowers(Model model) {
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        return "/superpowers";
    }

    @PostMapping("addSuperpower")
    public String addSuperpower(HttpServletRequest request, Model model){

        String name = request.getParameter("SuperpowerName");
        String description = request.getParameter("SuperpowerDescription");

        Superpower superpower = superpowerDao.createSuperpower(name, description);

        return "redirect:/addSuperpower";
    }

    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(Integer id) {
        superpowerDao.deleteSuperpowerById(id);
        return "redirect:/superpowers";
    }

    @GetMapping("editSuperpower")
    public String displayEditSuperpower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDao.getSuperpowerById(id);

        model.addAttribute("superpower", superpower);

        return "editSuperpower";
    }

    @PostMapping("editSuperpower")
    public String editSuperpower(HttpServletRequest request, Model model){

        int id = Integer.parseInt(request.getParameter("SuperpowerId"));
        String name = request.getParameter("SuperpowerName");
        String description = request.getParameter("SuperpowerDescription");

        Superpower superpower = superpowerDao.createSuperpower(name, description);
        superpower.setId(id);

            superpower = superpowerDao.getSuperpowerById(superpower.getId());

            model.addAttribute("superpower", superpower);


            return "editSuperpower";

    }

    @GetMapping("detailsSuperpower")
    public String displayDetailsSuperpower(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));

        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);

       // List<Superhero> superheros = superpowerDao.getSuperheroForSuperpower(superpower);
       // model.addAttribute("superheros", superheros);

        return "superpowers/detailsSuperpower";
    }*/
}
