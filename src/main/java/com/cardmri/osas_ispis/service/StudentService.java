package com.cardmri.osas_ispis.service;

import com.cardmri.osas_ispis.dto.StudentCreateRequest;
import com.cardmri.osas_ispis.dto.StudentResponse;
import com.cardmri.osas_ispis.dto.StudentUpdateRequest;
import com.cardmri.osas_ispis.entity.Program; // Make sure this import is present
import com.cardmri.osas_ispis.entity.Student;
import com.cardmri.osas_ispis.repository.ProgramRepository;
import com.cardmri.osas_ispis.repository.StudentRepository;
import com.cardmri.osas_ispis.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserAccountRepository userAccountRepository;
    private final ProgramRepository programRepository;

    public StudentResponse createStudent(StudentCreateRequest request) {
        var userAccount = userAccountRepository.findById(request.getUserAccountId())
                .orElseThrow(() -> new EntityNotFoundException("UserAccount not found with ID: " + request.getUserAccountId()));

        var student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setYearLevel(request.getYearLevel());
        student.setUserAccount(userAccount);

        // Find and set the Program if programId is provided
        if (request.getProgramId() != null) {
            Program program = programRepository.findById(request.getProgramId())
                    .orElseThrow(() -> new EntityNotFoundException("Program not found with ID: " + request.getProgramId()));
            student.setProgram(program);
        }

        var savedStudent = studentRepository.save(student);

        return mapToResponse(savedStudent); // Corrected method name
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponse) // Corrected method name
                .collect(Collectors.toList());
    }

    public StudentResponse getStudentById(Long id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + id));
        return mapToResponse(student); // Corrected method name
    }

    public StudentResponse updateStudent(Long id, StudentUpdateRequest request) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + id));

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setYearLevel(request.getYearLevel());

        // Find and update the Program if programId is provided
        if (request.getProgramId() != null) {
            Program program = programRepository.findById(request.getProgramId())
                    .orElseThrow(() -> new EntityNotFoundException("Program not found with ID: " + request.getProgramId()));
            student.setProgram(program);
        } else {
            // This allows un-assigning a program by not providing a programId
            student.setProgram(null);
        }

        var updatedStudent = studentRepository.save(student);
        return mapToResponse(updatedStudent); // Corrected method name
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }

    /**
     * Helper method to map a Student entity to a StudentResponse DTO.
     */
    private StudentResponse mapToResponse(Student student) {
        // This is the fully corrected builder logic
        return StudentResponse.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .yearLevel(student.getYearLevel())
                .email(student.getUserAccount().getEmail())
                .phoneNumber(student.getPhoneNumber())
                .programCode(student.getProgram() != null ? student.getProgram().getCode() : null)
                .build();
    }
}
