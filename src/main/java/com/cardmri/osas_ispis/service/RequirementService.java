package com.cardmri.osas_ispis.service;

import com.cardmri.osas_ispis.dto.RequirementRequest;
import com.cardmri.osas_ispis.dto.RequirementResponse;
import com.cardmri.osas_ispis.entity.Requirement;
import com.cardmri.osas_ispis.repository.RequirementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequirementService {

    private final RequirementRepository requirementRepository;

    public RequirementResponse createRequirement(RequirementRequest request) {
        var requirement = new Requirement();
        requirement.setTitle(request.getTitle());
        requirement.setType(request.getType());
        requirement.setAcademicYear(request.getAcademicYear());

        var savedRequirement = requirementRepository.save(requirement);
        return mapToRequirementResponse(savedRequirement);
    }

    public List<RequirementResponse> getAllRequirements() {
        return requirementRepository.findAll()
                .stream()
                .map(this::mapToRequirementResponse)
                .collect(Collectors.toList());
    }

    public RequirementResponse getRequirementById(Long id) {
        var requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Requirement not found with ID: " + id));
        return mapToRequirementResponse(requirement);
    }

    public RequirementResponse updateRequirement(Long id, RequirementRequest request) {
        var requirement = requirementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Requirement not found with ID: " + id));

        requirement.setTitle(request.getTitle());
        requirement.setType(request.getType());
        requirement.setAcademicYear(request.getAcademicYear());

        var updatedRequirement = requirementRepository.save(requirement);
        return mapToRequirementResponse(updatedRequirement);
    }

    public void deleteRequirement(Long id) {
        if (!requirementRepository.existsById(id)) {
            throw new EntityNotFoundException("Requirement not found with ID: " + id);
        }
        requirementRepository.deleteById(id);
    }

    private RequirementResponse mapToRequirementResponse(Requirement requirement) {
        return RequirementResponse.builder()
                .id(requirement.getId())
                .title(requirement.getTitle())
                .type(requirement.getType())
                .academicYear(requirement.getAcademicYear())
                .build();
    }
}
