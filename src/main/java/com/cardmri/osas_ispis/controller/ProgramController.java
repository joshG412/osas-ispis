package com.cardmri.osas_ispis.controller;

import com.cardmri.osas_ispis.dto.ProgramRequest;
import com.cardmri.osas_ispis.dto.ProgramResponse;
import com.cardmri.osas_ispis.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/programs") // 1. Base URL for all endpoints in this controller
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    // 2. Create Endpoint: POST /api/v1/programs
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // 3. Security: Only Admins can create programs.
    public ResponseEntity<ProgramResponse> createProgram(@RequestBody ProgramRequest request) {
        var createdProgram = programService.createProgram(request);
        return new ResponseEntity<>(createdProgram, HttpStatus.CREATED);
    }

    // 4. Get All Endpoint: GET /api/v1/programs
    @GetMapping
    public ResponseEntity<List<ProgramResponse>> getAllPrograms() {
        return ResponseEntity.ok(programService.getAllPrograms());
    }

    // 5. Get by ID Endpoint: GET /api/v1/programs/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProgramResponse> getProgramById(@PathVariable Long id) {
        return ResponseEntity.ok(programService.getProgramById(id));
    }

    // 6. Update Endpoint: PUT /api/v1/programs/{id}
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Security: Only Admins can update.
    public ResponseEntity<ProgramResponse> updateProgram(@PathVariable Long id, @RequestBody ProgramRequest request) {
        return ResponseEntity.ok(programService.updateProgram(id, request));
    }

    // 7. Delete Endpoint: DELETE /api/v1/programs/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Returns a 204 No Content on success
    @PreAuthorize("hasRole('ADMIN')") // Security: Only Admins can delete.
    public void deleteProgram(@PathVariable Long id) {
        programService.deleteProgram(id);
    }
}
