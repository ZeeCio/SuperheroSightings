package superheroApp.Entities;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Location {
    private int id;

    private String name;


    private double latitude;


    private double longitude;


    private String description;


    private String addressInfo;


    public Location() {
    }

    public Location(int id, String name, double latitude, double longitude, String description, String addressInfo) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.addressInfo = addressInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return getId() == location.getId() &&
                Double.compare(location.getLatitude(),
                        getLatitude()) == 0 && Double.compare(location.getLongitude(),
                getLongitude()) == 0 && Objects.equals(getName(),
                location.getName()) && Objects.equals(getDescription(),
                location.getDescription()) && Objects.equals(getAddressInfo(),
                location.getAddressInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getName(),
                getLatitude(),
                getLongitude(),
                getDescription(),
                getAddressInfo());
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", description='" + description + '\'' +
                ", addressInfo='" + addressInfo + '\'' +
                '}';
    }
}
