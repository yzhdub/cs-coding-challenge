package com.cs.coding.reader;

import com.cs.coding.models.ServerLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.file.LineMapper;

public class ServerLogJsonLineMapper implements LineMapper<ServerLog> {

    private ObjectMapper mapper = new ObjectMapper();

//    private JsonLineMapper delegate;
//
//    public void setDelegate(JsonLineMapper delegate) {
//        this.delegate = delegate;
//    }

    /**
     * Interpret the line as a Json object and create an Entity from it.
     */
    @Override
    public ServerLog mapLine(String line, int lineNumber) throws Exception {
        return mapper.readValue(line, ServerLog.class);
    }

//    public ServerLog mapLine(String line, int lineNumber) throws Exception {
//        Map<String,Object> eventAsMap = delegate.mapLine(line, lineNumber);
//
//        ServerLog serverLog = new ServerLog();
////        serverLog.setId((String)eventAsMap.get("id"));
////        serverLog.setState((String)eventAsMap.get("state"));
////        serverLog.setType((String)eventAsMap.get("type"));
////        serverLog.setHost ((String)eventAsMap.get("host"));
////        serverLog.setTimestamp(0L);
////        serverLog.setTimestamp(new Long((String) eventAsMap.get("timestamp")));
//        return serverLog;
//    }

}