package com.cs.coding.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDetail {

    @Id
    private String id; // Event id

    private int duration; // Event duration milliseconds

    private String type;

    private String host;

    private boolean alert;

}