package vn.com.assistant.fqcbackend.service;

import vn.com.assistant.fqcbackend.dto.MeasurementRequestDTO;


public interface MeasurementService {
    void create(MeasurementRequestDTO measurementRequestDTO);

    void delete(String measurementId);
}
