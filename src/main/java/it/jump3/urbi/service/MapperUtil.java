package it.jump3.urbi.service;

import it.jump3.urbi.enumz.VehiclesTypeEnum;
import it.jump3.urbi.service.model.Vehicle;
import it.jump3.urbi.service.model.cityscoot.CityScootRoot;
import it.jump3.urbi.service.model.cityscoot.Scooter;
import it.jump3.urbi.service.model.enjoy.CarData;
import it.jump3.urbi.service.model.enjoy.EnjoyRoot;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MapperUtil {

    public List<Vehicle> getVehiclesFromEnjoy(EnjoyRoot enjoy) {

        Set<Vehicle> vehicles = new HashSet<>();

        if (enjoy != null && enjoy.getReturnValue() != null && !CollectionUtils.isEmpty(enjoy.getReturnValue().getCarsAvailabilityData())) {
            enjoy.getReturnValue().getCarsAvailabilityData()
                    .stream()
                    .filter(carsAvailabilityData -> !CollectionUtils.isEmpty(carsAvailabilityData.getCarData()))
                    .forEach(carsAvailabilityData ->
                            carsAvailabilityData.getCarData().forEach(carData ->
                                    vehicles.add(getVehicleFromEnjoy(carData))));
        }

        return new ArrayList<>(vehicles);
    }

    private Vehicle getVehicleFromEnjoy(CarData carData) {
        Assert.notNull(carData, "carData must be not null");
        Vehicle vehicle = new Vehicle();
        vehicle.setId(carData.getCarIdentifier());
        vehicle.setVehiclesType(VehiclesTypeEnum.CAR);
        vehicle.setDescription(carData.getCarName());
        vehicle.setPlate(carData.getCarPlate());
        vehicle.setAddress(carData.getLocationData().getAddress());
        vehicle.setLatitude(carData.getLocationData().getLat());
        vehicle.setLongitude(carData.getLocationData().getLon());
        return vehicle;
    }

    public List<Vehicle> getVehiclesFromCityScoot(CityScootRoot cityScoot) {

        Set<Vehicle> vehicles = new HashSet<>();

        if (cityScoot != null && cityScoot.getData() != null && !CollectionUtils.isEmpty(cityScoot.getData().getScooters())) {
            cityScoot.getData().getScooters()
                    .stream()
                    .filter(scooter -> scooter != null && scooter.getId() != 0)
                    .forEach(scooter -> vehicles.add(getVehicleFromEnjoy(scooter)));
        }

        return new ArrayList<>(vehicles);
    }

    private Vehicle getVehicleFromEnjoy(Scooter scooter) {
        Assert.notNull(scooter, "scooter must be not null");
        Vehicle vehicle = new Vehicle();
        vehicle.setId(Integer.toString(scooter.getId()));
        vehicle.setVehiclesType(VehiclesTypeEnum.SCOOTER);
        vehicle.setPlate(scooter.getPlate());
        vehicle.setAddress(scooter.getGeoCoding());
        vehicle.setLatitude(scooter.getLatitude());
        vehicle.setLongitude(scooter.getLongitude());
        return vehicle;
    }
}
