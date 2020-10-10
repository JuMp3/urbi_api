package it.jump3.urbi.service;

import it.jump3.urbi.service.model.Vehicle;
import it.jump3.urbi.service.model.VehicleResponse;
import it.jump3.urbi.service.model.cityscoot.CityScootRoot;
import it.jump3.urbi.service.model.enjoy.EnjoyRoot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class VehicleService {

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private MapperUtil mapperUtil;


    /*
        Get vehicles from multiple providers
     */
    public VehicleResponse getVehicles(Locale locale) {

        CompletableFuture<EnjoyRoot> enjoyFuture = asyncService.getEnjoy();
        CompletableFuture<CityScootRoot> cityScootFuture = asyncService.getCityScoot();

        // Wait until they are all done
        log.info("Retrieve data from providers...");
        CompletableFuture.allOf(enjoyFuture, cityScootFuture).join();
        log.info("Retrieve data from providers done");

        VehicleResponse vehicleResponse = new VehicleResponse();

        try {
            EnjoyRoot enjoy = enjoyFuture.get();

            log.info("Process enjoy data...");
            List<Vehicle> cars = mapperUtil.getVehiclesFromEnjoy(enjoy);
            log.info("Process enjoy data done");

            vehicleResponse.getVehicleList().addAll(cars);
            vehicleResponse.increasesCountAll(cars.size());
            vehicleResponse.increasesCountCar(cars.size());
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error during performing retrieve enjoy data: ".concat(e.getMessage()), e);
        }

        try {
            CityScootRoot cityScoot = cityScootFuture.get();

            log.info("Process cityScoot data...");
            List<Vehicle> scooters = mapperUtil.getVehiclesFromCityScoot(cityScoot);
            log.info("Process cityScoot data done");

            vehicleResponse.getVehicleList().addAll(scooters);
            vehicleResponse.increasesCountAll(scooters.size());
            vehicleResponse.increasesCountScooter(scooters.size());
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error during performing retrieve cityScoot data: ".concat(e.getMessage()), e);
        }

        return vehicleResponse;
    }
}
