package superheroApp.Entities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class Superhero{
    private int id;

    private String name;

    private String description;

    private List<Superpower> superpowers;
    private List<Sighting> sightings;


    public Superhero() {
    }

    public Superhero(int id, String name, String description, List<Superpower> superpowers, List<Sighting> sightings) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.superpowers = superpowers;
        this.sightings = sightings;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Superhero)) return false;
        Superhero superhero = (Superhero) o;
        return getId() == superhero.getId() &&
                Objects.equals(getName(), superhero.getName()) &&
                Objects.equals(getDescription(),
                        superhero.getDescription()) &&
                Objects.equals(getSuperpowers(),
                        superhero.getSuperpowers()) &&
                Objects.equals(getSightings(),
                        superhero.getSightings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getName(),
                getDescription(),
                getSuperpowers(),
                getSightings());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Superpower> getSuperpowers() {
        return superpowers;
    }

    public void setSuperpowers(List<Superpower> superpowers) {
        this.superpowers = superpowers;
    }

    public List<Sighting> getSightings() {
        return sightings;
    }

    public void setSightings(List<Sighting> sightings) {
        this.sightings = sightings;
    }

    @Override
    public String toString() {
        return "Superhero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", superpowers=" + superpowers +
                ", sightings=" + sightings +
                '}';
    }
}
