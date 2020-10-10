package it.jump3.urbi.service.model.cityscoot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityScootRoot implements Serializable {

    private boolean success;
    private int reason;
    private String comment;
    private Data data;
    private Meta meta;
}
