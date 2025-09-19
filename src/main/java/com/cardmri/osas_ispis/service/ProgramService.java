package com.cardmri.osas_ispis.service;

import com.cardmri.osas_ispis.dto.ProgramRequest;
import com.cardmri.osas_ispis.dto.ProgramResponse;
import com.cardmri.osas_ispis.entity.Program;
import com.cardmri.osas_ispis.repository.ProgramRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramService {

    // 1. Dependency Injection: We inject the repository to talk to the database.
    private final ProgramRepository programRepository;

    // 2. CREATE Operation
    public ProgramResponse createProgram(ProgramRequest request) {
        var program = new Program();
        program.setCode(request.getCode());
        program.setName(request.getName());

        var savedProgram = programRepository.save(program);
        return mapToResponse(savedProgram);
    }

    // 3. READ (All) Operation
    public List<ProgramResponse> getAllPrograms() {
        return programRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 4. READ (by ID) Operation
    public ProgramResponse getProgramById(Long id) {
        var program = programRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Program not found with ID: " + id));
        return mapToResponse(program);
    }

    // 5. UPDATE Operation
    public ProgramResponse updateProgram(Long id, ProgramRequest request) {
        var program = programRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Program not found with ID: " + id));

        program.setCode(request.getCode());
        program.setName(request.getName());

        var updatedProgram = programRepository.save(program);
        return mapToResponse(updatedProgram);
    }

    // 6. DELETE Operation
    public void deleteProgram(Long id) {
        if (!programRepository.existsById(id)) {
            throw new EntityNotFoundException("Program not found with ID: " + id);
        }
        programRepository.deleteById(id);
    }

    // 7. Private Helper Method: This converts a Program entity into a ProgramResponse DTO.
    private ProgramResponse mapToResponse(Program program) {
        return ProgramResponse.builder()
                .id(program.getId())
                .code(program.getCode())
                .name(program.getName())
                .build();
    }
}
