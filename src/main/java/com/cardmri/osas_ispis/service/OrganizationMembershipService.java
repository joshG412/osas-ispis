package com.cardmri.osas_ispis.service;

import com.cardmri.osas_ispis.dto.MembershipRequest;
import com.cardmri.osas_ispis.dto.MembershipResponse;
import com.cardmri.osas_ispis.entity.OrganizationMembership;
import com.cardmri.osas_ispis.repository.OrganizationMembershipRepository;
import com.cardmri.osas_ispis.repository.OrganizationRepository;
import com.cardmri.osas_ispis.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrganizationMembershipService {

    private final OrganizationMembershipRepository membershipRepository;
    private final StudentRepository studentRepository;
    private final OrganizationRepository organizationRepository;

    public MembershipResponse createMembership(MembershipRequest request) {
        var student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + request.getStudentId()));
        var organization = organizationRepository.findById(request.getOrganizationId())
                .orElseThrow(() -> new EntityNotFoundException("Organization not found with ID: " + request.getOrganizationId()));

        var membership = new OrganizationMembership();
        membership.setStudent(student);
        membership.setOrganization(organization);
        membership.setPosition(request.getPosition());

        var savedMembership = membershipRepository.save(membership);
        return mapToResponse(savedMembership);
    }
    
    public List<MembershipResponse> getAllMemberships() {
        return membershipRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteMembership(Long id) {
        if (!membershipRepository.existsById(id)) {
            throw new EntityNotFoundException("Membership not found with ID: " + id);
        }
        membershipRepository.deleteById(id);
    }

    private MembershipResponse mapToResponse(OrganizationMembership membership) {
        return MembershipResponse.builder()
                .id(membership.getId())
                .position(membership.getPosition())
                .studentId(membership.getStudent().getId())
                .studentName(membership.getStudent().getFirstName() + " " + membership.getStudent().getLastName())
                .organizationId(membership.getOrganization().getId())
                .organizationName(membership.getOrganization().getName())
                .build();
    }
}
