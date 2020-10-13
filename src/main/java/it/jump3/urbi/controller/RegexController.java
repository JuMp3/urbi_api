package it.jump3.urbi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.jump3.urbi.exception.ErrorResponse;
import it.jump3.urbi.service.IRegex;
import it.jump3.urbi.service.model.RegexResponse;
import it.jump3.urbi.service.model.RegexRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Api(value = "VehiclesControllerAPI")
@RestController
@RequestMapping("/v1")
@Slf4j
public class RegexController {

    @Autowired
    private IRegex regexService;

    @ApiOperation(value = "Generate regex from list of strings", nickname = "generateRegex", httpMethod = "POST", response = RegexResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Server error during data retrieval", response = ErrorResponse.class)})
    @CrossOrigin("*")
    @PostMapping(value = "/regex", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<RegexResponse> generateRegex(@Valid @RequestBody RegexRequest request) {

        log.info("**** START -> generateRegex ****");
        String regex = regexService.getRegexFromString(request.getWords()
                .stream()
                .filter(s -> !StringUtils.isEmpty(s))
                .collect(Collectors.toList()));
        log.info("**** END -> generateRegex ****");

        return ResponseEntity.ok(new RegexResponse(regex));
    }
}
