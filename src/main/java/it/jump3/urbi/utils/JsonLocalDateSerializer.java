package it.jump3.urbi.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;

public class JsonLocalDateSerializer extends JsonSerializer<LocalDate> {

    public void serialize(LocalDate localDateTime, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(DateUtil.getFromLocalDateDefault(localDateTime));
    }
}
