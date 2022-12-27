package superheroApp.Entities;

import java.sql.Date;
import java.util.Objects;

public class Sighting {

    private int id;
    private int superheroId;
    private Location location;
    private String date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sighting)) return false;
        Sighting sighting = (Sighting) o;
        return getId() == sighting.getId() &&
                getSuperheroId() == sighting.getSuperheroId()
                && getLocation() == sighting.getLocation()
                && getDate().equals(sighting.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getSuperheroId(),
                getLocation(),
                getDate());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSuperheroId() {
        return superheroId;
    }

    public void setSuperheroId(int superheroId) {
        this.superheroId = superheroId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Sighting{" +
                "id=" + id +
                ", superheroId=" + superheroId +
                ", location=" + location +
                ", date=" + date +
                '}';
    }
}
