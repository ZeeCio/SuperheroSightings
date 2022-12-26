package superheroApp.DAO;

import org.springframework.transaction.annotation.Transactional;
import superheroApp.Entities.Superpower;

import java.util.List;

public interface SuperpowerDB {
    Superpower getSuperpowerById(int id);

    List<Superpower> getAllSuperpowers();

    @Transactional
    Superpower addSuperpower(Superpower superpower);

    void updateSuperpower(Superpower superpower);

    @Transactional
    void deleteSuperpowerById(int id);
}
