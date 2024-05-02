package com.profIITsoft.block2.service;

import com.profIITsoft.block2.entity.Service;
import com.profIITsoft.block2.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.Set;

/**
 * ServiceService class to manage flight services in the database
 */
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public Set<Service> findServicesByNames(Set<String> services) {
        return serviceRepository.findByNameIn(services);
    }
}
