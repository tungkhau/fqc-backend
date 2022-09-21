package vn.com.assistant.fqcbackend.service;

import vn.com.assistant.fqcbackend.dto.CriteriaRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriteriaResponseDTO;

import java.util.List;

public interface CriteriaService {
    List<CriteriaResponseDTO> fetch();
    void create(CriteriaRequestDTO criteriaRequestDTO);
    void delete(String criteriaId);
}
