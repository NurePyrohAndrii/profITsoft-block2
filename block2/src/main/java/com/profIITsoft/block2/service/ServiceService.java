package com.profIITsoft.block2.service;

import com.profIITsoft.block2.dto.ServiceDto;
import com.profIITsoft.block2.entity.Service;
import com.profIITsoft.block2.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
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

    public List<ServiceDto> getServices() {
        return serviceRepository.findAll().stream()
                .map(service -> ServiceDto.builder()
                        .name(service.getName())
                        .build())
                .toList();
    }
}
