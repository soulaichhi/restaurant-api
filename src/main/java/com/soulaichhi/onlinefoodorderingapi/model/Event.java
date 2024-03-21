package com.soulaichhi.onlinefoodorderingapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String image;
    private Date startedAt;
    private Date endsAt;
    private String name;
    @ManyToOne
    private Restaurant restaurant;
    private String location;
}
