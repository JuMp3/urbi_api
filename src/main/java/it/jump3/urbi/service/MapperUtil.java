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
import org.springframework.util.StringUtils;

import java.util.*;

@Component
public class MapperUtil {

    public List<Vehicle> getVehiclesFromEnjoy(EnjoyRoot enjoy, String city) {

        Set<Vehicle> vehicles = new HashSet<>();

        if (enjoy != null && enjoy.getReturnValue() != null && !CollectionUtils.isEmpty(enjoy.getReturnValue().getCarsAvailabilityData())) {
            enjoy.getReturnValue().getCarsAvailabilityData()
                    .stream()
                    .filter(carsAvailabilityData -> !CollectionUtils.isEmpty(carsAvailabilityData.getCarData()))
                    .forEach(carsAvailabilityData ->
                            carsAvailabilityData.getCarData().stream()
                                    .filter(carData -> !StringUtils.isEmpty(carData.getCarIdentifier()) &&
                                            org.apache.commons.lang.StringUtils.isNumeric(carData.getCarIdentifier()) &&
                                            (StringUtils.isEmpty(city) || (!StringUtils.isEmpty(carData.getLocationData().getAddress()) &&
                                                    carData.getLocationData().getAddress().toLowerCase().contains(city.toLowerCase()))))
                                    .forEach(carData ->
                                            vehicles.add(getVehicleFromEnjoy(carData))));
        }

        return new ArrayList<>(vehicles);
    }

    private Vehicle getVehicleFromEnjoy(CarData carData) {
        Assert.notNull(carData, "carData must be not null");
        Vehicle vehicle = new Vehicle();

        int idInt = Integer.parseInt(carData.getCarIdentifier());
        vehicle.setId(idInt);

        vehicle.setVehiclesType(VehiclesTypeEnum.CAR);
        vehicle.setDescription(carData.getCarName());
        vehicle.setPlate(carData.getCarPlate());
        vehicle.setAddress(carData.getLocationData().getAddress());
        vehicle.setLatitude(carData.getLocationData().getLat());
        vehicle.setLongitude(carData.getLocationData().getLon());
        return vehicle;
    }

    public List<Vehicle> getVehiclesFromCityScoot(CityScootRoot cityScoot, String city) {

        Set<Vehicle> vehicles = new HashSet<>();

        if (cityScoot != null && cityScoot.getData() != null && !CollectionUtils.isEmpty(cityScoot.getData().getScooters())) {
            cityScoot.getData().getScooters()
                    .stream()
                    .filter(scooter -> scooter != null && scooter.getId() != 0 && (StringUtils.isEmpty(city) ||
                            (!StringUtils.isEmpty(scooter.getGeoCoding()) && scooter.getGeoCoding().toLowerCase().contains(city.toLowerCase()))))
                    .forEach(scooter -> vehicles.add(getVehicleFromEnjoy(scooter)));
        }

        return new ArrayList<>(vehicles);
    }

    private Vehicle getVehicleFromEnjoy(Scooter scooter) {
        Assert.notNull(scooter, "scooter must be not null");
        Vehicle vehicle = new Vehicle();
        vehicle.setId(scooter.getId());
        vehicle.setVehiclesType(VehiclesTypeEnum.SCOOTER);
        vehicle.setPlate(scooter.getPlate());
        vehicle.setAddress(scooter.getGeoCoding());
        vehicle.setLatitude(scooter.getLatitude());
        vehicle.setLongitude(scooter.getLongitude());
        return vehicle;
    }

    private List<Vehicle> getOrderList(Set<Vehicle> vehicles) {
        List vehicleList = new ArrayList<>(vehicles);
        vehicleList.sort(Comparator.comparing(Vehicle::getId));
        return vehicleList;
    }
}
