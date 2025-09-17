package com.cardmri.osas_ispis.controller;

import com.cardmri.osas_ispis.dto.MembershipRequest;
import com.cardmri.osas_ispis.dto.MembershipResponse;
import com.cardmri.osas_ispis.service.OrganizationMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/memberships")
@RequiredArgsConstructor
public class OrganizationMembershipController {

    private final OrganizationMembershipService membershipService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MembershipResponse> createMembership(@RequestBody MembershipRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(membershipService.createMembership(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<List<MembershipResponse>> getAllMemberships() {
        return ResponseEntity.ok(membershipService.getAllMemberships());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMembership(@PathVariable Long id) {
        membershipService.deleteMembership(id);
    }
}
