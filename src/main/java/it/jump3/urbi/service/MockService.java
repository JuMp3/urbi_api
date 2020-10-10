package it.jump3.urbi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.jump3.urbi.service.model.cityscoot.CityScootRoot;
import it.jump3.urbi.service.model.enjoy.EnjoyRoot;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Data
public class MockService {

    @Autowired
    private ResourceLoader resourceLoader;

    private EnjoyRoot enjoy;
    private CityScootRoot cityScoot;

    private ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    private void init() throws IOException {

        try (InputStream is = resourceLoader.getResource("classpath:enjoy.json").getInputStream()) {
            this.enjoy = mapper.readValue(is, EnjoyRoot.class);
        }

        try (InputStream is = resourceLoader.getResource("classpath:cityscoot.json").getInputStream()) {
            this.cityScoot = mapper.readValue(is, CityScootRoot.class);
        }
    }
}