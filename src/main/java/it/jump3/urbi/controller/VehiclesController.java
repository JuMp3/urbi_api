package it.jump3.urbi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.jump3.urbi.exception.ErrorResponse;
import it.jump3.urbi.service.IVehicle;
import it.jump3.urbi.service.model.VehicleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Api(value = "VehiclesControllerAPI")
@RestController
@RequestMapping("/v1")
@Slf4j
public class VehiclesController {

    @Autowired
    private IVehicle vehicleService;

    @ApiOperation(value = "Get vehicles available", nickname = "vehicles", httpMethod = "GET", response = VehicleResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Server error during data retrieval", response = ErrorResponse.class)})
    @CrossOrigin("*")
    @GetMapping(value = "/vehicles/{page}/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<VehicleResponse> vehicles(@PathVariable(value = "page") Integer page,
                                                    @PathVariable(value = "size") Integer size,
                                                    @RequestParam(required = false) String city,
                                                    Locale locale) {

        log.info("**** START -> vehicles ****");
        VehicleResponse vehicles = vehicleService.getVehicles(city, page, size, locale);
        log.info("**** END -> vehicles ****");

        return ResponseEntity.ok(vehicles);
    }
}
