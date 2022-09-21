package vn.com.assistant.fqcbackend.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import vn.com.assistant.fqcbackend.dto.LotRequestDTO;
import vn.com.assistant.fqcbackend.dto.LotResponseDTO;
import vn.com.assistant.fqcbackend.entity.Lot;
import vn.com.assistant.fqcbackend.exception.InvalidException;
import vn.com.assistant.fqcbackend.repository.LotRepository;
import vn.com.assistant.fqcbackend.utility.LotMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
public class LotServiceImp implements LotService{
    private final LotRepository _repository;
    private Environment env;
    @Override
    public List<LotResponseDTO> fetch() {
        List<Lot> lots = _repository.findAll();
        return LotMapper.INSTANCE.listLotToLotResponseDTO(lots);
    }

    @Override
    public void create(LotRequestDTO lotRequestDTO) {
        Lot lot = LotMapper.INSTANCE.lotRequestDTOtoLot(lotRequestDTO);
        _repository.save(lot);
    }

    @Override
    public void update(LotRequestDTO lotRequestDTO, String lotId) {
        Lot lot = _repository.findById(lotId).orElseThrow(() -> new InvalidException(env.getProperty("lot.notExisted")));
        LotMapper.INSTANCE.updateLotFromLotRequestDTO(lotRequestDTO, lot);
        _repository.save(lot);
    }

    @Override
    public void delete(String lotId) {
        Lot lot = _repository.findById(lotId).orElseThrow(() -> new InvalidException(env.getProperty("lot.notExisted")));
        _repository.delete(lot);
    }
}
