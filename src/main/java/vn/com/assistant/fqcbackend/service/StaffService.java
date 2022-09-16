package vn.com.assistant.fqcbackend.service;

import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.StaffRequestDTO;
import vn.com.assistant.fqcbackend.dto.StaffResponseDTO;

import java.util.List;

public interface StaffService {
    List<StaffResponseDTO> fetch();

    void create(StaffRequestDTO staffRequestDTO);

    void update(StaffRequestDTO staffRequestDTO, String staffId);

    void resetPassword(String staffId);
}
