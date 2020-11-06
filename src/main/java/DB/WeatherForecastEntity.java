package DB;

import DB.LocationEntity;

import javax.persistence.*;

@Entity
@Table(name = "weather_forecast", schema = "public")
public class WeatherForecastEntity {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  @Column(name = "id_forecast", nullable = false)
  private int idForecast;
  @ManyToOne
  @JoinColumn(name = "id_location", nullable = false)
  private LocationEntity locationEntity;
  @Column(name = "weather_description", nullable = false, length = 300)
  private String weatherDescription;

  public WeatherForecastEntity() {
  }

  public WeatherForecastEntity(LocationEntity locationEntity, String weatherDescription) {
    this.locationEntity = locationEntity;
    this.weatherDescription = weatherDescription;
  }

  public int getIdForecast() {
    return idForecast;
  }

  public void setIdForecast(int idForecast) {
    this.idForecast = idForecast;
  }


  public LocationEntity getIdLocation() {
    return locationEntity;
  }

  public void setIdLocation(LocationEntity idLocation) {
    this.locationEntity = idLocation;
  }


  public String getWeatherDescription() {
    return weatherDescription;
  }

  public void setWeatherDescription(String weatherDescription) {
    this.weatherDescription = weatherDescription;
  }

}
