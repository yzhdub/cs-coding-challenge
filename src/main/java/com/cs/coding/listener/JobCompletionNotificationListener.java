package com.cs.coding.listener;

import com.cs.coding.models.EventDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("JOB Completed! Verify the results from the DB");

            jdbcTemplate.query("SELECT id, duration, type, host, alert FROM EVENTDETAIL",
                    (rs, row) -> new EventDetail(
                            rs.getString(1),
                            rs.getInt(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getBoolean(5))
            ).forEach(serverlog -> log.info("Found <" + serverlog + "> in the database."));
        }
    }
}