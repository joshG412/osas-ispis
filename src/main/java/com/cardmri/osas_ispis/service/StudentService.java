package com.cardmri.osas_ispis.service;

import com.cardmri.osas_ispis.dto.StudentCreateRequest;
import com.cardmri.osas_ispis.dto.StudentResponse;
import com.cardmri.osas_ispis.dto.StudentUpdateRequest;
import com.cardmri.osas_ispis.entity.Student;
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

    public StudentResponse createStudent(StudentCreateRequest request) {
        var userAccount = userAccountRepository.findById(request.getUserAccountId())
                .orElseThrow(() -> new EntityNotFoundException("UserAccount not found with ID: " + request.getUserAccountId()));

        var student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setYearLevel(request.getYearLevel());
        student.setUserAccount(userAccount);

        var savedStudent = studentRepository.save(student);

        return mapToStudentResponse(savedStudent); // We pass the saved entity to the helper
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToStudentResponse)
                .collect(Collectors.toList());
    }

    public StudentResponse getStudentById(Long id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + id));
        return mapToStudentResponse(student);
    }

    public StudentResponse updateStudent(Long id, StudentUpdateRequest request) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + id));

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setYearLevel(request.getYearLevel());

        var updatedStudent = studentRepository.save(student);
        return mapToStudentResponse(updatedStudent);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }

    // --- THIS IS THE CORRECTED HELPER METHOD ---
    /**
     * Helper method to map a Student entity to a StudentResponse DTO.
     */
    private StudentResponse mapToStudentResponse(Student student) { // It receives a parameter named 'student'
        return StudentResponse.builder()
                .id(student.getId()) // It must use 'student' here
                .firstName(student.getFirstName()) // and here
                .lastName(student.getLastName()) // and here
                .yearLevel(student.getYearLevel()) // and here
                .email(student.getUserAccount().getEmail()) // and here
                .build();
    }
}
