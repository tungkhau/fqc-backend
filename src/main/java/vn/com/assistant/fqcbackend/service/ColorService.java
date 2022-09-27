package vn.com.assistant.fqcbackend.service;

import vn.com.assistant.fqcbackend.dto.ColorRequestDTO;
import vn.com.assistant.fqcbackend.dto.ColorResponseDTO;
import vn.com.assistant.fqcbackend.dto.CriterionRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriterionResponseDTO;

import java.util.List;

public interface ColorService {
    List<ColorResponseDTO> fetch();

    void create(ColorRequestDTO colorRequestDTO);

    void delete(String colorId);
}
