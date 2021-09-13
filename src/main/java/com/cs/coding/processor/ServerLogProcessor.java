package com.cs.coding.processor;

import com.cs.coding.model.EventDetail;
import com.cs.coding.model.ServerLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ServerLogProcessor implements ItemProcessor<ServerLog, EventDetail> {

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
            log.info("EventDetail = " + eventDetail.toString());
        } else {
            map.put(id, serverLog);
        }

        return eventDetail;
    }
}