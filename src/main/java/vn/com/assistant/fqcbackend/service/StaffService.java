package vn.com.assistant.fqcbackend.service;

import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.StaffRequestDTO;

@Service
public interface StaffService {
    void create(StaffRequestDTO staffRequestDTO);

    void update(StaffRequestDTO staffRequestDTO);

    void resetPassword(String id);
}