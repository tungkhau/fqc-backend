package vn.com.assistant.fqcbackend.service;

import vn.com.assistant.fqcbackend.dto.CriterionRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriterionResponseDTO;

import java.util.List;

public interface CriterionService {
    List<CriterionResponseDTO> fetch();
    void create(CriterionRequestDTO criterionRequestDTO);
    void delete(String criteriaId);
}
