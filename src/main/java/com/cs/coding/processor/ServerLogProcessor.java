package com.cs.coding.processor;

import com.cs.coding.models.EventDetail;
import com.cs.coding.models.ServerLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashMap;
import java.util.Map;

public class ServerLogProcessor implements ItemProcessor<ServerLog, EventDetail> {

    private static final Logger log = LoggerFactory.getLogger(ServerLogProcessor.class);

    private Map<String, ServerLog> map = new HashMap<>();

    @Override
    public EventDetail process(ServerLog serverLog) throws Exception {
        String id = serverLog.getId();

        EventDetail eventDetail = null;

        if (map.containsKey(id)) {
            Long newTimeStamp = serverLog.getTimestamp();
            String host = serverLog.getHost();
            String type = serverLog.getType();

            ServerLog oldLog = map.get(id);
            int duration = (int) Math.abs(newTimeStamp - oldLog.getTimestamp());
            boolean alert = duration > 4;
            eventDetail = new EventDetail(id, duration, host, type, alert);
        } else {
            map.put(id, serverLog);
        }

        return eventDetail;
    }
}