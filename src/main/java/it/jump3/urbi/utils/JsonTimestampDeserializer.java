package it.jump3.urbi.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;

@Slf4j
public class JsonTimestampDeserializer extends JsonDeserializer<Timestamp> {

    @Override
    public Timestamp deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
        Timestamp timestamp = null;
        try {
            timestamp = DateUtil.getTimestampFromStringDefault(jsonparser.getText());
        } catch (ParseException e) {
            log.warn(e.getMessage());
        }
        return timestamp;
    }
}
