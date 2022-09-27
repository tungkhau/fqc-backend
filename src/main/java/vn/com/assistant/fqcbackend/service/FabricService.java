package vn.com.assistant.fqcbackend.service;

import vn.com.assistant.fqcbackend.dto.FabricRequestDTO;
import vn.com.assistant.fqcbackend.dto.FabricResponseDTO;

import java.util.List;

public interface FabricService {
    List<FabricResponseDTO> fetch();

    void create(FabricRequestDTO fabricRequestDTO);

    void delete(String fabricId);
}
