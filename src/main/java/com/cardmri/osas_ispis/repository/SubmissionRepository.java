package com.cardmri.osas_ispis.repository;

import com.cardmri.osas_ispis.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findAllByStudentId(Long studentId);

    long countByStatus(String status);
    long countByRequirementType(String requirementType);

}
