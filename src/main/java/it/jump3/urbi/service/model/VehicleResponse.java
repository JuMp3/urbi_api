package it.jump3.urbi.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleResponse {

    private List<Vehicle> vehicleList = new ArrayList<>();
    private Long countAll = 0L;
    private Long countCar = 0L;
    private Long countScooter = 0L;
    private Integer pages = 0;

    public void increasesCountAll(Integer count) {
        this.countAll += count;
    }

    public void increasesCountCar(Integer count) {
        this.countCar += count;
    }

    public void increasesCountScooter(Integer count) {
        this.countScooter += count;
    }
}
