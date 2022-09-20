package vn.com.assistant.fqcbackend.service;

import vn.com.assistant.fqcbackend.dto.CriterialRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriterialResponseDTO;

import java.util.List;

public interface CriterialService {
    List<CriterialResponseDTO> fetch();
    void create(CriterialRequestDTO criterialRequestDTO);
    void update(CriterialRequestDTO criterialRequestDTO, String criterialId);
    void delete(String criterialId);
}
