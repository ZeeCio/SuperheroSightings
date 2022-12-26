package superheroApp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import superheroApp.DAO.SuperheroDB;
import superheroApp.DAO.SuperpowerDB;
import superheroApp.Entities.Superhero;
import java.util.List;

@Controller
public class SuperheroController {

    private final SuperheroDB superheroDao;


    public SuperheroController(SuperheroDB superheroDao) {
        this.superheroDao = superheroDao;
    }

    @GetMapping("/superheros")
    public String displaySuperheros(Model model) {
        List<Superhero> superheros = superheroDao.getAllSuperheros();
        model.addAttribute("superheros", superheros);
        return "superheros";
    }

    @PostMapping("addSuperhero")
    public String addStudent(String name, String description) {
        Superhero superhero = new Superhero();
        superhero.setName(name);
        superhero.setDescription(description);
        superheroDao.addSuperhero(superhero);

        return "redirect:/superheros";
    }

    @GetMapping("deleteSuperhero")
    public String deleteSuperhero(Integer id) {
        superheroDao.deleteSuperheroById(id);
        return "redirect:/superheros";
    }

    @GetMapping("editSuperhero")
    public String editSuperhero(Integer id, Model model) {
        //int id = Integer.parseInt(request.getParameter("id"));
        Superhero superhero = superheroDao.getSuperheroById(id);
        model.addAttribute("superhero", superhero);
        return "editSuperhero";
    }

    @PostMapping("editSuperhero")
    public String performSuperhero(Superhero superhero) {
        superheroDao.updateSuperhero(superhero);
        return "redirect:/superheros";
    }


    }

