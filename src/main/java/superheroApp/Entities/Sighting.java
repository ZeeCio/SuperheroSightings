package superheroApp.Entities;

import java.sql.Date;
import java.util.Objects;

public class Sighting {

    private int id;
    private int superheroId;
    private int locationId;
    private Date date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sighting)) return false;
        Sighting sighting = (Sighting) o;
        return getId() == sighting.getId() &&
                getSuperheroId() == sighting.getSuperheroId()
                && getLocationId() == sighting.getLocationId()
                && getDate().equals(sighting.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getSuperheroId(),
                getLocationId(),
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

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Sighting{" +
                "id=" + id +
                ", superheroId=" + superheroId +
                ", location=" + locationId +
                ", date=" + date +
                '}';
    }
}
