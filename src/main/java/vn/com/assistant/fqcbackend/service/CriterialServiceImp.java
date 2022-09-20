package vn.com.assistant.fqcbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.CriterialRequestDTO;
import vn.com.assistant.fqcbackend.dto.CriterialResponseDTO;
import vn.com.assistant.fqcbackend.entity.Criterial;
import vn.com.assistant.fqcbackend.repository.CriterialRepository;
import vn.com.assistant.fqcbackend.utility.CriterialMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CriterialServiceImp implements CriterialService {
    private final CriterialRepository _repository;
    @Override
    public List<CriterialResponseDTO> fetch() {
        List<Criterial> criterialList = _repository.findAll();
        return CriterialMapper.INSTANCE.listCriterialToCriterialResponseDTO(criterialList);
    }

    @Override
    public void create(CriterialRequestDTO criterialRequestDTO) {
    }

    @Override
    public void update(CriterialRequestDTO criterialRequestDTO, String criterialId) {

    }

    @Override
    public void delete(String criterialId) {

    }
}
