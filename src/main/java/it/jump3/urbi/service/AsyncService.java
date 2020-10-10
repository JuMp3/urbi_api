package it.jump3.urbi.service;

import it.jump3.urbi.service.model.cityscoot.CityScootRoot;
import it.jump3.urbi.service.model.enjoy.EnjoyRoot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AsyncService {

    @Autowired
    private MockService mockService;

    @Async("processAsyncExecutor")
    public CompletableFuture<EnjoyRoot> getEnjoy() {

        EnjoyRoot enjoy = null;
        try {
            log.info("[1/2] Retrieve enjoy data...");
            enjoy = mockService.getEnjoy();
            log.info("[1/2] Retrieve enjoy data done");
        } catch (Exception e) {
            log.error("Error during retrieve enjoy data from provider: ".concat(e.getMessage()));
        }

        return CompletableFuture.completedFuture(enjoy);
    }

    @Async("processAsyncExecutor")
    public CompletableFuture<CityScootRoot> getCityScoot() {

        CityScootRoot cityScoot = null;
        try {
            log.info("[2/2] Retrieve cityScoot data...");
            cityScoot = mockService.getCityScoot();
            log.info("[2/2] Retrieve cityScoot data done");
        } catch (Exception e) {
            log.error("Error during retrieve cityScoot data from provider: ".concat(e.getMessage()));
        }

        return CompletableFuture.completedFuture(cityScoot);
    }
}
