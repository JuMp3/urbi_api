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
public class Artwork implements Serializable {

    @JsonProperty("id_scooter_artwork")
    private int idScooterArtwork;

    private String name;

    @JsonProperty("image_url")
    private String imageUrl;
}
