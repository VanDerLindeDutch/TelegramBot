package DB;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "location", schema = "public")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_location", nullable = false)
    private int idLocation;
    @Column(name = "longitude", nullable = false, precision = 0)
    private double longitude;
    @Column(name = "latitude", nullable = false, precision = 0)
    private double latitude;
    @OneToOne()
    @JoinColumn(name = "id_subscribe", nullable = false)
    private SubscribeEntity subscribeByIdSubscribe;
    @OneToMany(mappedBy = "locationEntity", cascade = CascadeType.ALL)
    private List<WeatherForecastEntity> weatherForecastEntityList = new ArrayList<>();

    public LocationEntity( double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LocationEntity() {
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }



    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public List<WeatherForecastEntity> getWeatherForecastEntityList() {
        return weatherForecastEntityList;
    }

    public void setWeatherForecastEntityList(List<WeatherForecastEntity> weatherForecastEntityList) {
        this.weatherForecastEntityList = weatherForecastEntityList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationEntity that = (LocationEntity) o;
        return idLocation == that.idLocation &&
                Double.compare(that.longitude, longitude) == 0 &&
                Double.compare(that.latitude, latitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLocation, longitude, latitude);
    }


    public SubscribeEntity getSubscribeByIdSubscribe() {
        return subscribeByIdSubscribe;
    }

    public void setSubscribeByIdSubscribe(SubscribeEntity subscribeByIdSubscribe) {
        this.subscribeByIdSubscribe = subscribeByIdSubscribe;
    }

    @Override
    public String toString() {
        return "LocationEntity{" +
                "idLocation=" + idLocation +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", subscribeByIdSubscribe=" + subscribeByIdSubscribe.getIdSubscribe() +
                ", weatherForecastEntityList=" + weatherForecastEntityList +
                '}';
    }
}
