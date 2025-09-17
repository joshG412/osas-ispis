package com.cardmri.osas_ispis.service;

import com.cardmri.osas_ispis.dto.OrganizationRequest;
import com.cardmri.osas_ispis.dto.OrganizationResponse;
import com.cardmri.osas_ispis.entity.Organization;
import com.cardmri.osas_ispis.repository.OrganizationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationResponse createOrganization(OrganizationRequest request) {
        var organization = new Organization();
        organization.setName(request.getName());
        organization.setDescription(request.getDescription());
        var savedOrg = organizationRepository.save(organization);
        return mapToResponse(savedOrg);
    }

    public List<OrganizationResponse> getAllOrganizations() {
        return organizationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public OrganizationResponse getOrganizationById(Long id) {
        var organization = organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with ID: " + id));
        return mapToResponse(organization);
    }

    public OrganizationResponse updateOrganization(Long id, OrganizationRequest request) {
        var organization = organizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with ID: " + id));
        organization.setName(request.getName());
        organization.setDescription(request.getDescription());
        var updatedOrg = organizationRepository.save(organization);
        return mapToResponse(updatedOrg);
    }

    public void deleteOrganization(Long id) {
        if (!organizationRepository.existsById(id)) {
            throw new EntityNotFoundException("Organization not found with ID: " + id);
        }
        organizationRepository.deleteById(id);
    }

    private OrganizationResponse mapToResponse(Organization organization) {
        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .build();
    }
}
