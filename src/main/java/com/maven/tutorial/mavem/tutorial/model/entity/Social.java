package com.maven.tutorial.mavem.tutorial.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "social")
@Data
public class Social {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer Id;

    private String facebook;
    private String twitter;
    private String instagram;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
