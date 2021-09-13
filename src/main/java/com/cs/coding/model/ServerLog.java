package com.cs.coding.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerLog {

    private String id;

    private String state; // (STARTED, FINISHED)

    private long timestamp;

    private String type;

    private String host;

}
