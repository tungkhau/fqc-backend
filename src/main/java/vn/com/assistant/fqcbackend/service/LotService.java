package vn.com.assistant.fqcbackend.service;

import vn.com.assistant.fqcbackend.dto.LotRequestDTO;
import vn.com.assistant.fqcbackend.dto.LotResponseDTO;

import java.util.List;

public interface LotService {
    List<LotResponseDTO> fetch();
    void create(LotRequestDTO lotRequestDTO);
    void update(LotRequestDTO lotRequestDTO, String lotId);
    void delete(String lotId);
}
