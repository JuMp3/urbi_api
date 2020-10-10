package it.jump3.urbi.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.sql.Timestamp;

public class JsonTimestampSerializer extends JsonSerializer<Timestamp> {

    @Override
    public void serialize(Timestamp date, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(DateUtil.getDateTimeFromTimestampDefault(date));
    }
}
