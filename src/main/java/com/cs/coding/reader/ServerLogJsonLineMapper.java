package com.cs.coding.reader;

import com.cs.coding.model.ServerLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.file.LineMapper;

public class ServerLogJsonLineMapper implements LineMapper<ServerLog> {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Interpret the line as a Json object and create an Entity from it.
     */
    @Override
    public ServerLog mapLine(String line, int lineNumber) throws Exception {
        return mapper.readValue(line, ServerLog.class);
    }

}