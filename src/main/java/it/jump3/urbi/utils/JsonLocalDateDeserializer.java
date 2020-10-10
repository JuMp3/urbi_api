package it.jump3.urbi.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class JsonLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
        return DateUtil.getDateFromStringDefault(jsonparser.getText());
    }
}
