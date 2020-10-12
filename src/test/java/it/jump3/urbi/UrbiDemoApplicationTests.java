package it.jump3.urbi;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.jump3.urbi.service.MockService;
import it.jump3.urbi.service.model.VehicleResponse;
import it.jump3.urbi.service.model.cityscoot.CityScootRoot;
import it.jump3.urbi.service.model.enjoy.EnjoyRoot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UrbiDemoApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ResourceLoader resourceLoader;

    @MockBean
    private MockService mockService;

    @Autowired
    private ObjectMapper mapper;

    private EnjoyRoot enjoy;
    private CityScootRoot cityScoot;

    @Test
    void contextLoads() {
    }

    @BeforeEach
    public void setup() throws IOException {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        try (InputStream is = resourceLoader.getResource("classpath:enjoy.json").getInputStream()) {
            enjoy = mapper.readValue(is, EnjoyRoot.class);
        }

        try (InputStream is = resourceLoader.getResource("classpath:cityscoot.json").getInputStream()) {
            cityScoot = mapper.readValue(is, CityScootRoot.class);
        }
    }

    @Test
    public void checkVehicles200Ok() throws Exception {

        doReturn(enjoy).when(mockService).getEnjoy();
        doReturn(cityScoot).when(mockService).getCityScoot();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/vehicles/0/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        VehicleResponse vehicleResponse = mapper.readValue(contentAsString, VehicleResponse.class);

        Assert.notNull(vehicleResponse, "Response must be not null");
        Assert.notEmpty(vehicleResponse.getVehicleList(), "Vehicles not found");
        Assert.isTrue(vehicleResponse.getVehicleList().size() == 1000, "Wrong pagination");
        Assert.isTrue(vehicleResponse.getPages() == 3, "Wrong pagination");
    }

    @Test
    public void checkVehiclesWithFilter200Ok() throws Exception {

        doReturn(enjoy).when(mockService).getEnjoy();
        doReturn(cityScoot).when(mockService).getCityScoot();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/vehicles/0/1000")
                .param("city", "san donato")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        VehicleResponse vehicleResponse = mapper.readValue(contentAsString, VehicleResponse.class);

        Assert.notNull(vehicleResponse, "Response must be not null");
        Assert.notEmpty(vehicleResponse.getVehicleList(), "Vehicles not found");
        Assert.isTrue(vehicleResponse.getVehicleList().size() == 18, "Wrong pagination");
        Assert.isTrue(vehicleResponse.getPages() == 1, "Wrong pagination");
    }

    @Test
    public void checkVehiclesNotFound200Ok() throws Exception {

        doReturn(enjoy).when(mockService).getEnjoy();
        doReturn(cityScoot).when(mockService).getCityScoot();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/vehicles/0/1000")
                .param("city", "vanzago")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        VehicleResponse vehicleResponse = mapper.readValue(contentAsString, VehicleResponse.class);

        Assert.notNull(vehicleResponse, "Response must be not null");
        Assert.isTrue(CollectionUtils.isEmpty(vehicleResponse.getVehicleList()), "Vehicles found");
    }

    @Test
    public void checkApiNotFound404Ok() throws Exception {

        doReturn(enjoy).when(mockService).getEnjoy();
        doReturn(cityScoot).when(mockService).getCityScoot();

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }
}
