package it.jump3.urbi.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.jump3.urbi.enumz.VehiclesTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {

    private Integer id;
    private VehiclesTypeEnum vehiclesType;
    private String description;
    private String plate;
    private String address;
    private double latitude;
    private double longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id) &&
                vehiclesType == vehicle.vehiclesType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehiclesType);
    }
}
