package vn.com.assistant.fqcbackend.service;

import vn.com.assistant.fqcbackend.dto.StaffRequestDTO;

public interface StaffService {
    void create(StaffRequestDTO staffRequestDTO);

    void update(StaffRequestDTO staffRequestDTO, String staffId);

    void resetPassword(String staffId);
}
