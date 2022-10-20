package com.qterminals.geospatial;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapDeserializer extends JsonDeserializer<Map> {
    public MapDeserializer() {
    }

    public Map deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        Map<String, Object> jsonToMap = new HashMap();

        try {
            JsonNode jsonNode = (JsonNode)jsonParser.getCodec().readTree(jsonParser);
            Iterator<String> fieldIterator = jsonNode.fieldNames();

            while(fieldIterator.hasNext()) {
                String fieldName = (String)fieldIterator.next();
                if ("NUMBER".equalsIgnoreCase(jsonNode.get(fieldName).getNodeType().name())) {
                    jsonToMap.put(fieldName, jsonNode.get(fieldName).asLong());
                    if (jsonNode.get(fieldName).isDouble()) {
                        jsonToMap.put(fieldName, jsonNode.get(fieldName).asDouble());
                    }
                } else if ("null".equalsIgnoreCase(jsonNode.get(fieldName).asText())) {
                    jsonToMap.put(fieldName, (Object)null);
                } else {
                    jsonToMap.put(fieldName, jsonNode.get(fieldName).asText());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return jsonToMap;
    }
}
