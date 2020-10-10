package it.jump3.urbi.service.model.cityscoot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Scooter implements Serializable {

    private int id;

    @JsonProperty("id_availability")
    private int idAvailability;

    private int number;

    @JsonProperty("id_fleet")
    private int idFleet;

    private String name;

    @JsonProperty("citybox")
    private int cityBox;

    private double latitude;
    private double longitude;

    @JsonProperty("geohash")
    private String geoHash;

    private int battery;

    @JsonProperty("ep_soc")
    private int epSoc;

    private int autonomy;
    private String plate;

    @JsonProperty("geocoding")
    private String geoCoding;

    private int version;
    private int status;

    @JsonProperty("has_cityboost")
    private boolean hasCityBoost;

    @JsonProperty("marker_graphic_id")
    private int markerGraphicId;

    @JsonProperty("id_scooter_artwork")
    private int idScooterArtwork;

    @JsonProperty("helmet_type")
    private int helmetType;

    @JsonProperty("helmet_size")
    private String helmetSize;
}
