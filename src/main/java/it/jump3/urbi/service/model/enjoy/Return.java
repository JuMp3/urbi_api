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
public class Return implements Serializable {

    private List<CarsAvailabilityData> carsAvailabilityData;
    private boolean isCanReserve;
    private String statusCode;
    private String statusMessage;
}
