package it.jump3.urbi.service.model.enjoy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarData implements Serializable {

    private int carCategoryID;
    private int carCategoryTypeID;
    private String carIdentifier;
    private String carManufacturer;
    private String carModel;
    private String carName;
    private String carPlate;
    private List<Integer> dynamicAttrs;
    private int fuelLevel;
    private boolean isOwner;
    private LocationData locationData;
    private int priceRangeID;
    private int virtualRentalID;
    private int virtualRentalTypeID;
}
