package com.mishal.project.HMS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String city;

    @Embedded
    private HotelContactInfo contactInfo;

    @Column(nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Room> rooms;

    @Column(columnDefinition = "TEXT[]")
    private List<String> photos;

    @Column(columnDefinition = "TEXT[]")
    private List<String> amenities;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;
}
