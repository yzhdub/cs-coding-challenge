package com.cs.coding.models;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerLog {

    @NotNull
    private String id;

    @NotNull
    private String state; // (STARTED, FINISHED)

    @NotNull
    private long timestamp;

    private String type;

    private String host;

}
