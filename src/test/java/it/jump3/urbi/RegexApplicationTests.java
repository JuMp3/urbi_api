package it.jump3.urbi;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.jump3.urbi.service.model.RegexResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class RegexApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper mapper;

    private static final String JSON_1 = "{\n" +
            " \"words\": [\n" +
            "  \"AB123ZZ\",\n" +
            "  \"BB742TG\",\n" +
            "  \"CF678HG\"\n" +
            " ]\n" +
            "}";

    private static final String JSON_2 = "{\n" +
            " \"words\": [\n" +
            "  \"TNTTST80A01F205E\",\n" +
            "  \"PGGGPR90B09A662O\"\n" +
            " ]\n" +
            "}";

    private static final String JSON_3 = "{\n" +
            " \"words\": [\n" +
            "  \"AA123\",\n" +
            "  \"BA1234\",\n" +
            "  \"AB12345\"\n" +
            " ]\n" +
            "}";

    @Test
    void contextLoads() {
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testRegex1() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/regex")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString().replace("\\d", "\\\\d");
        RegexResponse response = mapper.readValue(contentAsString, RegexResponse.class);

        Assert.isTrue(response.getRegex().equals(Pattern.compile("[A-Z]{2}\\d{3}[A-Z]{2}").toString()), "Wrong regex");
    }

    @Test
    public void testRegex2() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/regex")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_2)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString().replace("\\d", "\\\\d");
        RegexResponse response = mapper.readValue(contentAsString, RegexResponse.class);

        Assert.isTrue(response.getRegex().equals(Pattern.compile("[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]").toString()), "Wrong regex");
    }

    @Test
    public void testRegex3() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/regex")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_3)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString().replace("\\d", "\\\\d");
        RegexResponse response = mapper.readValue(contentAsString, RegexResponse.class);

        Assert.isTrue(response.getRegex().equals(Pattern.compile("[A-Z]{2}\\d{3,5}").toString()), "Wrong regex");
    }
}
