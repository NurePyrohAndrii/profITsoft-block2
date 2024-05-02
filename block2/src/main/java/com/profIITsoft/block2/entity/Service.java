package com.profIITsoft.block2.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Service entity class to represent the services provided within airlines.
 */
@Entity
@Table(name = "service")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @Column(name = "service_name", nullable = false, unique = true)
    private String serviceName;
}