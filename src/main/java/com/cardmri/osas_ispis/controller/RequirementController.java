package com.cardmri.osas_ispis.controller;

import com.cardmri.osas_ispis.dto.RequirementRequest;
import com.cardmri.osas_ispis.dto.RequirementResponse;
import com.cardmri.osas_ispis.service.RequirementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/requirements")
@RequiredArgsConstructor
public class RequirementController {

    private final RequirementService requirementService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RequirementResponse> createRequirement(@RequestBody RequirementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(requirementService.createRequirement(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')") // Allow staff to view requirements as well
    public ResponseEntity<List<RequirementResponse>> getAllRequirements() {
        return ResponseEntity.ok(requirementService.getAllRequirements());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<RequirementResponse> getRequirementById(@PathVariable Long id) {
        return ResponseEntity.ok(requirementService.getRequirementById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RequirementResponse> updateRequirement(@PathVariable Long id, @RequestBody RequirementRequest request) {
        return ResponseEntity.ok(requirementService.updateRequirement(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRequirement(@PathVariable Long id) {
        requirementService.deleteRequirement(id);
    }
}
