package it.jump3.urbi.service;

import it.jump3.urbi.service.model.VehicleResponse;

import java.util.Locale;

public interface IVehicle {

    VehicleResponse getVehicles(String city, Integer page, Integer size, Locale locale);
}
